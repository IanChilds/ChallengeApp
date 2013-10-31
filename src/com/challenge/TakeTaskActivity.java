package com.challenge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public class TakeTaskActivity extends Activity {
    public final static int TASK_TAKE_PHOTO_REQUEST = 1;
    public final static int TASK_SUBMITTED = 8;
    public final static String TASK_TO_TAKE = "com.challenge.task_to_take";
    private TextView instructions;
    private EditText textAnswer;
    private ImageView examplePhotoImageView;
    private ImageView actualPhotoImageView;
    private Bitmap examplePhoto = null;
    private Bitmap actualPhoto = null;
    private ImageView exampleGpsMap = null;
    private ImageView actualGpsSubmissionMap = null;
    private GPSConstraint actualGpsSubmission = null;
    private Button submitButton;
    private Task task;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_task);
        instructions = (EditText)findViewById(R.id.task_instructions);
        textAnswer = (EditText)findViewById(R.id.text_answer);
        examplePhotoImageView = (ImageView)findViewById(R.id.take_task_example_photo);
        actualPhotoImageView = (ImageView)findViewById(R.id.take_task_actual_photo);
        exampleGpsMap = (ImageView)findViewById(R.id.take_task_required_gps);
        actualGpsSubmissionMap = (ImageView)findViewById(R.id.take_task_actual_gps);
        submitButton = (Button)findViewById(R.id.take_task_submit_answer);
        Intent receivedIntent = getIntent();
        task = GlobalDataStore.getTask(receivedIntent.getIntExtra(TASK_TO_TAKE, -1));
        setView();
    }

    private void setView() {
        if (task.taskState == Task.TaskState.CORRECT) {
            submitButton.setVisibility(View.GONE);
        }
        switch(task.type) {
            case GPS:
                // Not yet implemented
                break;
            case PHOTO:
                examplePhotoImageView.setVisibility(View.VISIBLE);
                actualPhotoImageView.setVisibility(View.VISIBLE);
                examplePhotoImageView.setImageBitmap(task.examplePhoto);
                if (!task.useGPSConstraint) {
                    exampleGpsMap.setVisibility(View.GONE);
                    actualGpsSubmissionMap.setVisibility(View.GONE);
                }
                textAnswer.setVisibility(View.GONE);
                break;
            case TEXT:
                textAnswer.setVisibility(View.VISIBLE);
                examplePhotoImageView.setVisibility(View.GONE);
                actualPhotoImageView.setVisibility(View.GONE);
                if (!task.useGPSConstraint) {
                    exampleGpsMap.setVisibility(View.GONE);
                    actualGpsSubmissionMap.setVisibility(View.GONE);
                }
                break;
        }
    }

    // Take an example photoImageView for the task.
    public void takePhoto(View view) {
        Intent intent = new Intent(this, TakePhotoActivity.class);
        startActivityForResult(intent, TASK_TAKE_PHOTO_REQUEST);
    }

    // Get the photoImageView that's just been taken for this task.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TASK_TAKE_PHOTO_REQUEST:
                if (resultCode == TakePhotoActivity.PHOTO_TAKEN) {
                    actualPhoto = GlobalDataStore.getLastPhotoTaken();
                    actualPhotoImageView.setImageBitmap(actualPhoto);
                }
                break;
            default:
                // Don't expect to hit this
        }
    }

    public void submitAnswer(View view) {
        // Only do something if the data is prepared.
        // TODO: we should put a dialog up if the data is not prepared.
        if (allDataIsReady()) {
            // Change task details in our data store, and then send to the back end if it's a photo.
            updateTask();
            Intent intent = new Intent();
            intent.putExtra(TASK_TO_TAKE, intent);
            setResult(TASK_SUBMITTED, intent);
        }
    }

    private void updateTask() {
        switch(task.type) {
            // Items are Select, GPS, Photo, Text
            case GPS:
                // TODO Can't implement this yet.
            case PHOTO:
                task.photoSubmitted = actualPhoto;
                if (task.useGPSConstraint) {
                    task.gpsConstraintSubmitted = actualGpsSubmission;
                }
                break;

            case TEXT:
                task.textSubmitted = textAnswer.getText().toString();
                if (task.useGPSConstraint) {
                    task.gpsConstraintSubmitted = actualGpsSubmission;
                }
                if (task.textSubmitted.equals(task.text)) {
                    task.taskState = Task.TaskState.CORRECT;
                }
                else {
                    task.taskState = Task.TaskState.INCORRECT;
                }
                break;

            default:
                // Something has gone wrong.
                break;
        }
    }

    private boolean allDataIsReady() {
        if (instructions.getText().toString().length() == 0) return false;
        switch(task.type) {
            // Items are Select, GPS, Photo, Text
            case GPS:
                return (actualGpsSubmission != null);
            case PHOTO:
                return (actualPhoto != null);
            case TEXT:
                return (textAnswer.getText().toString().length() > 0);
            default:
                // Something has gone wrong.
                return false;
        }
    }
}