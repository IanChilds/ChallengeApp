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
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class EditTaskActivity extends Activity { // This can easily be made into an "Edit Task Activity"
    public final static int TASK_TAKE_PHOTO_REQUEST = 1;
    public final static String TASK_ID = "com.challenge.createtask.taskid";
    public final static int TASK_ADDED = 6;
    public final static int TASK_CHANGED = 7;
    public final static String TASK_OPENED_FOR_EDITING = "com.challenge.task_opened_for_editing";
    public final static String TASK_OPENED = "com.challenge.task_opened";
    private boolean openedForEditing = false;
    private EditText instructions;
    private EditText text;
    private CheckBox photoCheckBox;
    private ImageView photoImageView;
    private Bitmap photo = null;
    private CheckBox gpsCheckBox;
    private ImageView gpsMap = null;
    private GPSConstraint gps = null;
    private CheckBox timeCheckBox;
    private Spinner spinner;
    private int spinnerSelectedPosition;
    private int taskIdReceived;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        text = (EditText)findViewById(R.id.correct_text_answer);
        photoCheckBox = (CheckBox)findViewById(R.id.want_photo_checkbox);
        photoImageView = (ImageView)findViewById(R.id.create_task_photo);
        gpsCheckBox = (CheckBox)findViewById(R.id.want_gps_checkbox);
        gpsMap = (ImageView)findViewById(R.id.choose_gps_coordinates);
        timeCheckBox = (CheckBox)findViewById(R.id.want_time_checkbox);
        instructions = (EditText)findViewById(R.id.task_instructions);
        setSpinner();
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            openedForEditing = receivedIntent.getBooleanExtra(TASK_OPENED_FOR_EDITING, false);
            if (openedForEditing) {
                taskIdReceived = receivedIntent.getIntExtra(TASK_OPENED, -1);
                setOldData(GlobalDataStore.getTask(taskIdReceived));
            }
        }
    }

    private void setOldData(Task task) {
        instructions.setText(task.description);
        switch (task.type) {
            case GPS:
                // Not implemented
                break;
            case PHOTO:
                spinnerSelectedPosition = 2;
                photo = task.examplePhoto;  // Currently you have to have a examplePhoto
                photoImageView.setImageBitmap(photo);
                photoCheckBox.setChecked(task.provideExamplePhoto);
                gpsCheckBox.setChecked(task.useGPSConstraint);
                if (task.useGPSConstraint) gps = task.gpsConstraint;
                timeCheckBox.setChecked(task.useTimeConstraint);
                // haven't implemented time constraints yet.
                setUpForPhotoSelected();
                break;
            case TEXT:
                spinnerSelectedPosition = 3;
                text.setText(task.text);
                gpsCheckBox.setChecked(task.useGPSConstraint);
                if (task.useGPSConstraint) gps = task.gpsConstraint;
                timeCheckBox.setChecked(task.useTimeConstraint);
                setUpForTextSelected();
                break;
        }
        spinner.setSelection(spinnerSelectedPosition);
    }

    private void setSpinner() {
        spinner = (Spinner)findViewById(R.id.task_type_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_type_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinnerSelectedPosition = 0;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                spinnerSelectedPosition = pos;
                switch (pos) {
                    // Items are Select, GPS, Photo, Text
                    case 0:
                        setUpForNoneSelected();
                        break;
                    case 1:
                        setUpForGPSSelected();
                        break;
                    case 2:
                        setUpForPhotoSelected();
                        break;
                    case 3:
                        setUpForTextSelected();
                        break;
                    default:
                        //Not expected, but oh well
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    private void setUpForNoneSelected() {
        text.setVisibility(View.GONE);
        photoCheckBox.setVisibility(View.GONE);
        photoImageView.setVisibility(View.GONE);
        gpsCheckBox.setVisibility(View.GONE);
        gpsMap.setVisibility(View.GONE);
        timeCheckBox.setVisibility(View.GONE);
    }

    private void setUpForGPSSelected() {
        text.setVisibility(View.GONE);
        photoCheckBox.setVisibility(View.GONE);
        photoImageView.setVisibility(View.GONE);
        gpsCheckBox.setVisibility(View.GONE);
        gpsMap.setVisibility(View.VISIBLE);
        timeCheckBox.setVisibility(View.VISIBLE);
    }

    private void setUpForPhotoSelected() {
        text.setVisibility(View.GONE);
        photoCheckBox.setVisibility(View.VISIBLE);
        photoImageView.setVisibility((photoCheckBox.isChecked()) ? View.VISIBLE : View.GONE);
        gpsCheckBox.setVisibility(View.VISIBLE);
        gpsMap.setVisibility((gpsCheckBox.isChecked()) ? View.VISIBLE : View.GONE);
        timeCheckBox.setVisibility(View.VISIBLE);
    }

    private void setUpForTextSelected() {
        text.setVisibility(View.VISIBLE);
        photoCheckBox.setVisibility(View.GONE);
        photoImageView.setVisibility(View.GONE);
        gpsCheckBox.setVisibility(View.VISIBLE);
        gpsMap.setVisibility((gpsCheckBox.isChecked()) ? View.VISIBLE : View.GONE);
        timeCheckBox.setVisibility(View.VISIBLE);
    }

    public void wantPhotoClicked(View view) {
        if (((CheckBox)view).isChecked()) photoImageView.setVisibility(View.VISIBLE);
        else photoImageView.setVisibility(View.GONE);
    }

    public void wantGPSClicked(View view) {
        if (((CheckBox)view).isChecked()) gpsMap.setVisibility(View.VISIBLE);
        else gpsMap.setVisibility(View.GONE);
    }

    public void wantTimeClicked(View view) {
        // Still to implement this bit.
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
                    photo = GlobalDataStore.getLastPhotoTaken();
                    photoImageView.setImageBitmap(photo);
                }
                break;
            default:
                // Don't expect to hit this
        }
    }

    public void addThisTask(View view) {
        // Only do something if the data is prepared.
        // TODO: we should put a dialog up if the data is not prepared.
        if (allDataIsReady()) {
            if (!openedForEditing) {
                Task task = buildTask();
                int taskID = GlobalDataStore.addTask(task);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(TASK_ID, taskID);
                setResult(TASK_ADDED, resultIntent);
                finish();
            }
            else {
                Task task = buildTask();
                GlobalDataStore.setTask(taskIdReceived, task);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(TASK_ID, taskIdReceived);
                setResult(TASK_CHANGED, resultIntent);
                finish();
            }
        }
    }

    private Task buildTask() {
        Task task = new Task();
        task.description = instructions.getText().toString();
        switch(spinnerSelectedPosition) {
            // Items are Select, GPS, Photo, Text
            case 1:
                // TODO Can't implement this yet.
                task.type = Task.TaskType.GPS;
            case 2:
                task.type = Task.TaskType.PHOTO;
                task.examplePhoto = photo;
                task.provideExamplePhoto = photoCheckBox.isChecked();
                task.useGPSConstraint = false;
                task.useTimeConstraint = false;
                break;

            case 3:
                task.type = Task.TaskType.TEXT;
                task.text = text.getText().toString();
                task.useGPSConstraint = false;
                task.useTimeConstraint = false;
                break;

            default:
                // Something has gone wrong.
                break;
        }
        return task;
    }

    private boolean allDataIsReady() {
        if (instructions.getText().toString().length() == 0) return false;
        switch(spinnerSelectedPosition) {
            // Items are Select, GPS, Photo, Text
            case 0:
                return false;
            case 1:
                return (gps != null);
            case 2:
                return (photo != null);
            case 3:
                return (text.getText().toString().length() > 0);
            default:
                // Something has gone wrong.
                return false;
        }
    }
}