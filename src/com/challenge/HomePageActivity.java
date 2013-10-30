package com.challenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class HomePageActivity extends Activity {
    private ArrayList<Class> activities = new ArrayList<Class>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        GlobalDataStore.locationHelper = new LocationHelper((LocationManager)getSystemService(Context.LOCATION_SERVICE));
    }

    public void loadProfileActivity(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void loadFindChallengeActivity(View view) {
    //    Intent intent = new Intent(this, FindChallengeActivity.class);
    //    startActivity(intent);
        Intent intent = new Intent(this, FetchChallengesActivity.class);
        startActivity(intent);
    }

    public void loadResumeActivity(View view) {
        Intent intent = new Intent(this, ResumeChallengeActivity.class);
        startActivity(intent);
    }

    public void loadCreateActivity(View view) {
        Intent intent = new Intent(this, CreateChallengeActivity.class);
        startActivity(intent);
    }

    public void loadVerifyActivity(View view) {
        Intent intent = new Intent(this, VerifyPhotoActivity.class);
        startActivity(intent);
    }
}