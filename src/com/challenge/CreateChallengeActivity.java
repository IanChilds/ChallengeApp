package com.challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class CreateChallengeActivity extends Activity {
    public final static int CHALLENGE_TAKE_PHOTO_REQUEST = 1;
    public final static int CREATE_TASK_REQUEST = 2;
    private ImageView photo;
    private EditText title;
    private List<Task> tasks = new ArrayList<Task>();
    ArrayList<String> taskListItems = new ArrayList<String>();
    ArrayAdapter<String> taskListItemsAdapter;
    ListView taskListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_challenge);
        photo = (ImageView)findViewById(R.id.create_challenge_photo);
        title = (EditText)findViewById(R.id.challenge_title);
        taskListView = (ListView)findViewById(R.id.task_list);
        taskListItemsAdapter=new ArrayAdapter<String>(this,
                                                      android.R.layout.simple_list_item_1,
                                                      taskListItems);
        taskListView.setAdapter(taskListItemsAdapter);
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(this, TakePhotoActivity.class);
        startActivityForResult(intent, CHALLENGE_TAKE_PHOTO_REQUEST);
        // TODO: need to verify that the TakePhotoActivity only returns RESULT_OK once it has taken a photo.
    }

    public void createNewTask(View view) {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivityForResult(intent, CREATE_TASK_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHALLENGE_TAKE_PHOTO_REQUEST:
                if (resultCode == TakePhotoActivity.PHOTO_TAKEN) {
                    photo.setImageBitmap(GlobalDataStore.getLastPhotoTaken());
                }

            case CREATE_TASK_REQUEST:
                if (resultCode == CreateTaskActivity.TASK_ADDED) {
                    int taskId = data.getIntExtra(CreateTaskActivity.TASK_ID, -1);
                    if (taskId >= 0) {
                        Task task = GlobalDataStore.getTask(taskId);
                        tasks.add(task);
                        taskListItems.add(task.description);
                        taskListItemsAdapter.notifyDataSetChanged();
                    }
                }

            default:
                // Don't expect to hit this
        }
    }

    public void submitChallenge(View view) {
        Challenge challenge = new Challenge();
        // Send a load of data to the back end.
    }
}