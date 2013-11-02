package com.challenge.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.challenge.schema.Challenge;
import com.challenge.GlobalDataStore;
import com.challenge.R;
import com.challenge.schema.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
public class TakeChallengeActivity extends Activity {
    public final static int TAKE_TASK_REQUEST = 1;
    private Challenge challenge;
    private List<Task> tasks = new ArrayList<Task>();
    ArrayList<String> taskListItems = new ArrayList<String>();
    ArrayAdapter<String> taskListItemsAdapter;
    ListView taskListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_challenge);
        Intent intent = getIntent();
        int challengeID = intent.getIntExtra(ChallengeActivity.CHALLENGE_ID, -1);
        challenge = GlobalDataStore.getChallenge(challengeID);
        setUpTaskListView();
    }

    private void setUpTaskListView() {
        taskListView = (ListView)findViewById(R.id.challenge_task_list);
        // Show all tasks that should be visible.
        for (int ii = 0; ii < challenge.numTasksVisible; ii++) {
            taskListItems.add(Task.stateToString.get(challenge.tasks.get(ii).taskState) + " " + challenge.tasks.get(ii).description);
        }
        taskListItemsAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                taskListItems);
        taskListView.setAdapter(taskListItemsAdapter);
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_TASK_REQUEST:
                if (resultCode == TakeTaskActivity.TASK_SUBMITTED) {
                    int taskId = data.getIntExtra(TakeTaskActivity.TASK_TO_TAKE, -1);
                    taskListItems.set(taskId, Task.stateToString.get(challenge.tasks.get(taskId).taskState) + " " + tasks.get(taskId).description);
                    taskListItemsAdapter.notifyDataSetChanged();
                }
                break;
            default:
                // Don't expect to hit this
        }
    }

}