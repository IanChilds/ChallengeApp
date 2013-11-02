package com.challenge.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.challenge.schema.Challenge;
import com.challenge.GlobalDataStore;
import com.challenge.R;
import com.challenge.schema.Task;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public class ChallengeActivity extends Activity {
    public final static String CHALLENGE_ID = "com.challenge.challenge_ID";
    private Challenge challenge;
    private TextView title;
    private TextView description;
    private ImageView photoImageView;
    ArrayList<String> taskListItems = new ArrayList<String>();
    ArrayAdapter<String> taskListItemsAdapter;
    ListView taskListView;

    // Fill in all bits of the challenge activity based on the challenge received.

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge);
        Intent intent = getIntent();
        int challengeID = intent.getIntExtra(CHALLENGE_ID, -1);
        challenge = GlobalDataStore.getChallenge(challengeID);
        setChallengeView();
    }

    private void setChallengeView() {
        title = (TextView)findViewById(R.id.challenge_title);
        title.setText(challenge.name);
        description = (TextView)findViewById(R.id.challenge_description);
        description.setText(challenge.description);
        photoImageView = (ImageView)findViewById(R.id.challenge_photo);
        if (challenge.photo != null) photoImageView.setImageBitmap(challenge.photo);

        for (Task task : challenge.tasks) {
            taskListItems.add(task.description);
        }
        taskListView = (ListView)findViewById(R.id.challenge_task_list);
        taskListItemsAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                taskListItems);
        taskListView.setAdapter(taskListItemsAdapter);
    }

    public void takeChallenge(View view) {
        Intent intent = new Intent(this, TakeChallengeActivity.class);
        intent.putExtra(CHALLENGE_ID, challenge.dataStorePosition);
        startActivity(intent);
    }
}