package com.challenge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

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
    private ImageView photoImageView;
    private Bitmap photo;
    private EditText title;
    private EditText description;
    private List<Task> tasks = new ArrayList<Task>();
    ArrayList<String> taskListItems = new ArrayList<String>();
    ArrayAdapter<String> taskListItemsAdapter;
    ListView taskListView;

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

            default:
                // Don't expect to hit this
        }
    }

    public void submitChallenge(View view) {
        Challenge challenge = new Challenge();
        challenge.creatorName = GlobalDataStore.currUser;
        challenge.name = title.getText().toString();
        challenge.description = description.getText().toString();
        challenge.photo = photo;
        for (Task task : tasks) challenge.tasks.add(task);

    }
}