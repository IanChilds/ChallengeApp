package com.challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
public class TakeChallengeActivity extends Activity {
    private Challenge challenge;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int challengeID = intent.getIntExtra(ChallengeActivity.CHALLENGE_ID, -1);
        challenge = GlobalDataStore.getChallenge(challengeID);
        // Just set up the list of challenges that the task requires, and then
    }
}