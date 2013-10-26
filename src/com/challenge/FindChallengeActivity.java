package com.challenge;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FindChallengeActivity extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        SupportMapFragment fragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        GoogleMap map = fragment.getMap();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.497149, -0.044589), 15.0f));
        MarkerOptions m = new MarkerOptions();



        map.addMarker(new MarkerOptions().position(new LatLng(51.497149, -0.044589)).title("Hello world"));
    }
}