package com.challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    // Fill in all bits of the challenge activity based on the challenge received.

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int challengeID = intent.getIntExtra(CHALLENGE_ID, -1);
        challenge = GlobalDataStore.getChallenge(challengeID);
    }

    public void takeChallenge(View view) {
        Intent intent = new Intent(this, TakeChallengeActivity.class);
        intent.putExtra(CHALLENGE_ID, challenge.dataStorePosition);
        startActivity(intent);
    }
}