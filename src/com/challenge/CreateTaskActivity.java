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
public class CreateTaskActivity extends Activity {
    public final static int TASK_TAKE_PHOTO_REQUEST = 1;
    private TextView instructions;
    private TextView text;
    private CheckBox photoCheckBox;
    private ImageView photoImageView;
    private Bitmap photo = null;
    private CheckBox gpsCheckBox;
    private ImageView gpsMap = null;
    private Task.GPSConstraint gps = null;
    private CheckBox timeCheckBox;
    private Spinner spinner;
    private int spinnerSelectedPosition;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);
        text = (TextView)findViewById(R.id.correct_text_answer);
        photoCheckBox = (CheckBox)findViewById(R.id.want_photo_checkbox);
        photoImageView = (ImageView)findViewById(R.id.create_task_photo);
        gpsCheckBox = (CheckBox)findViewById(R.id.want_gps_checkbox);
        gpsMap = (ImageView)findViewById(R.id.choose_gps_coordinates);
        timeCheckBox = (CheckBox)findViewById(R.id.want_time_checkbox);
        setSpinner();
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
        gpsMap.setVisibility((photoCheckBox.isChecked()) ? View.VISIBLE : View.GONE);
        timeCheckBox.setVisibility(View.VISIBLE);
    }

    private void setUpForTextSelected() {
        text.setVisibility(View.VISIBLE);
        photoCheckBox.setVisibility(View.GONE);
        photoImageView.setVisibility(View.GONE);
        gpsCheckBox.setVisibility(View.VISIBLE);
        gpsMap.setVisibility((photoCheckBox.isChecked()) ? View.VISIBLE : View.GONE);
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

            default:
                // Don't expect to hit this
        }
    }

    public void addThisTask(View view) {
        // Only do something if the data is prepared.
        // TODO: we should put a dialog up if the data is not prepared.
        if (allDataIsReady()) {
            Task task = buildTask();


            }
    }

    private Task buildTask() {
        Task task = new Task();
        task.description = instructions.toString();
        switch(spinnerSelectedPosition) {
            // Items are Select, GPS, Photo, Text
            case 1:
                // TODO Can't implement this yet.
                task.type = Task.TaskType.GPS;
            case 2:
                task.type = Task.TaskType.PHOTO;
                task.photo = photo;
                task.useGPSConstraint = false;
                task.useTimeConstraint = false;
                break;

            case 3:
                task.type = Task.TaskType.TEXT;
                task.text = text.toString();
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
        if (text.toString().length() == 0) return false;
        switch(spinnerSelectedPosition) {
            // Items are Select, GPS, Photo, Text
            case 0:
                return false;
            case 1:
                return (gps != null);
            case 2:
                return (photo != null);
            case 3:
                return (text.toString().length() > 0);
            default:
                // Something has gone wrong.
                return false;
        }

    }


}