package com.challenge;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 27/10/13
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class SelectGPSPointActivity extends FragmentActivity {
    public final static String LATITUDE = "com.challenge.latitude";
    public final static String LONGITUDE = "com.challenge.longitude";
    public final static String RANGE = "com.challenge.range";
    public final static int POINT_ADDED = 10;
    private MarkerOptions m;
    private boolean markerSelected;
    private GoogleMap map;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_gps_point);
        markerSelected = false;
        SupportMapFragment fragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map = fragment.getMap();
        Intent intent = getIntent();
        double range = intent.getDoubleExtra(RANGE, -1);
        if (range > 0) {
            m = new MarkerOptions();
            LatLng latLng = new LatLng((double)intent.getDoubleExtra(LATITUDE, 0), (double)intent.getDoubleExtra(LONGITUDE, 0));
            m.position(latLng);
            m.title("Marker");
            m.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            map.addMarker(m);
            markerSelected = true;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        }
        else {
            markerSelected = false;
            Location myLoc = GlobalDataStore.locationHelper.getLatestLocation();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLoc.getLatitude(), myLoc.getLongitude()), 15.0f));
        }

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (markerSelected) {
                    removeMarker();
                }
                m = new MarkerOptions();
                m.position(latLng);
                m.title("Marker");
                m.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                map.addMarker(m);
                markerSelected = true;
            }
        });
    }

    public void removeMarker() {
        map.clear();
        markerSelected = false;
    }

    public void removeMarker(View view) {
        if (markerSelected) removeMarker();
    }

    public void submitMarker(View view) {
        if (markerSelected) {
            Intent intent = new Intent();
            intent.putExtra(LATITUDE, m.getPosition().latitude);
            intent.putExtra(LONGITUDE, m.getPosition().longitude);
            intent.putExtra(RANGE, 100.0);
            setResult(POINT_ADDED, intent);
            finish();
        }
    }

}