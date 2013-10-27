package com.challenge;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class FindChallengeActivity extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        SupportMapFragment fragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        final GoogleMap map = fragment.getMap();
        Location myLoc = GlobalDataStore.locationHelper.getLatestLocation();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLoc.getLatitude(),myLoc.getLongitude()), 15.0f));
        final Random r = new Random();
        for(int i = 0; i < 10; i++){
            MarkerOptions m = new MarkerOptions();
            double lat = 51.49 + r.nextDouble() / 100;
            double lng = -0.044 + r.nextDouble() / 100;
            m.position(new LatLng(lat, lng));
            m.title("Challenge " + i);
            map.addMarker(m);
        }

        map.setMyLocationEnabled(true);

        map.setOnMarkerClickListener(new ChallengeClickListener());

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                MarkerOptions m = new MarkerOptions();
                m.position(latLng);
                m.title("Challenge " + r.nextInt());
                m.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                map.addMarker(m);
            }
        });
    }

    public class ChallengeClickListener implements GoogleMap.OnMarkerClickListener {
        public boolean onMarkerClick(Marker marker) {

            return false;
        }
    }

}