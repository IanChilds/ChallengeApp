package com.challenge;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.maps.MapActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */
public class FindChallengeActivity extends MapActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}