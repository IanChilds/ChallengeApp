package com.challenge.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.*;
import com.challenge.*;
import com.challenge.async.UploadChallenge;
import com.challenge.schema.Challenge;
import com.challenge.schema.GPSConstraint;
import com.challenge.schema.Task;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class CreateChallengeActivity extends FragmentActivity {
    public final static int CHALLENGE_TAKE_PHOTO_REQUEST = 1;
    public final static int CREATE_TASK_REQUEST = 2;
    public final static int CHALLENGE_GET_GPS = 3;
    private ImageView photoImageView;
    private Bitmap photo;
    private EditText title;
    private EditText description;
    private GPSConstraint gpsConstraint = new GPSConstraint();
    private boolean gpsConstraintSet = false;
    private List<Task> tasks = new ArrayList<Task>();
    ArrayList<String> taskListItems = new ArrayList<String>();
    ArrayAdapter<String> taskListItemsAdapter;
    ListView taskListView;
    private GoogleMap map;
    private SupportMapFragment fragment;
    private MarkerOptions m;
    private boolean markerSelected = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_challenge);
        photoImageView = (ImageView)findViewById(R.id.create_challenge_photo);
        title = (EditText)findViewById(R.id.challenge_title);
        description = (EditText)findViewById(R.id.challenge_description);
        taskListView = (ListView)findViewById(R.id.task_list);
        taskListItemsAdapter=new ArrayAdapter<String>(this,
                                                      android.R.layout.simple_list_item_1,
                                                      taskListItems);
        taskListView.setAdapter(taskListItemsAdapter);
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
                intent.putExtra(EditTaskActivity.TASK_OPENED_FOR_EDITING, true);
                intent.putExtra(EditTaskActivity.TASK_OPENED, tasks.get(i).dataStorePosition);
                startActivityForResult(intent, CREATE_TASK_REQUEST);
            }
        });
        setUpMap();
    }

    public void setUpMap() {
        fragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map = fragment.getMap();
        Location myLoc = GlobalDataStore.locationHelper.getLatestLocation();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLoc.getLatitude(), myLoc.getLongitude()), 15.0f));
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setAllGesturesEnabled(false);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                setGPSMarker(fragment.getView());
            }
        });
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(this, TakePhotoActivity.class);
        startActivityForResult(intent, CHALLENGE_TAKE_PHOTO_REQUEST);
        // TODO: need to verify that the TakePhotoActivity only returns RESULT_OK once it has taken a examplePhoto.
    }

    public void createNewTask(View view) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        startActivityForResult(intent, CREATE_TASK_REQUEST);
    }

    public void setGPSMarker(View view) {
        Intent intent = new Intent(view.getContext(), SelectGPSPointActivity.class);
        if (gpsConstraintSet) {
            intent.putExtra(SelectGPSPointActivity.LATITUDE, gpsConstraint.lat);
            intent.putExtra(SelectGPSPointActivity.LONGITUDE, gpsConstraint.lon);
            intent.putExtra(SelectGPSPointActivity.RANGE, gpsConstraint.range);
        }
        startActivityForResult(intent, CHALLENGE_GET_GPS);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHALLENGE_TAKE_PHOTO_REQUEST:
                if (resultCode == TakePhotoActivity.PHOTO_TAKEN) {
                    photo = GlobalDataStore.getLastPhotoTaken();
                    photoImageView.setImageBitmap(photo);
                }

            case CREATE_TASK_REQUEST:
                if (resultCode == EditTaskActivity.TASK_ADDED) {
                    int taskId = data.getIntExtra(EditTaskActivity.TASK_ID, -1);
                    if (taskId >= 0) {
                        Task task = GlobalDataStore.getTask(taskId);
                        tasks.add(task);
                        taskListItems.add(task.description);
                        taskListItemsAdapter.notifyDataSetChanged();
                    }
                }
                else if (resultCode == EditTaskActivity.TASK_CHANGED) {
                    int taskId = data.getIntExtra(EditTaskActivity.TASK_ID, -1);
                    for (int ii = 0; ii < tasks.size(); ii++) {
                        if (tasks.get(ii).dataStorePosition == taskId) {
                            Task task = GlobalDataStore.getTask(taskId);
                            tasks.set(ii, task);
                            taskListItems.set(ii, task.description);
                            taskListItemsAdapter.notifyDataSetChanged();
                        }
                    }
                }

            case CHALLENGE_GET_GPS:
                if (resultCode == SelectGPSPointActivity.POINT_ADDED) {
                    gpsConstraint.lat = data.getDoubleExtra(SelectGPSPointActivity.LATITUDE, 0);
                    gpsConstraint.lon = data.getDoubleExtra(SelectGPSPointActivity.LONGITUDE, 0);
                    gpsConstraint.range = data.getDoubleExtra(SelectGPSPointActivity.RANGE, 100);
                    gpsConstraintSet = true;
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsConstraint.lat, gpsConstraint.lon), 15.0f));
                    if (markerSelected) {
                        map.clear();
                    }
                    m = new MarkerOptions();
                    m.position(new LatLng(gpsConstraint.lat, gpsConstraint.lon));
                    m.title("Marker");
                    m.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    map.addMarker(m);
                    markerSelected = true;
                }

            default:
                // Don't expect to hit this
        }
    }

    public void submitChallenge(View view) {
        if (uploadPermitted()) {
            Challenge challenge = new Challenge();
            challenge.creatorName = GlobalDataStore.currUser;
            challenge.name = title.getText().toString();
            challenge.description = description.getText().toString();
            challenge.photo = photo;
            for (Task task : tasks) challenge.tasks.add(task);

            UploadChallenge uploadChallenge = new UploadChallenge(challenge);
            uploadChallenge.execute();
        }
    }

    private boolean uploadPermitted() {
        if (photo == null) return false;
        if (title.getText().toString().length() == 0) return false;
        if (description.getText().toString().length() == 0) return false;
        return true;
    }
}