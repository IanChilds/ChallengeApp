package com.challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 30/10/13
 * Time: 22:16
 * To change this template use File | Settings | File Templates.
 */
public class FetchChallengesActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch);
        FetchChallenges fetchChallenges = new FetchChallenges(this);
        fetchChallenges.execute();
    }

    public void challengesLoaded() {
        Intent intent = new Intent(this, FindChallengeActivity.class);
        startActivity(intent);
    }


}