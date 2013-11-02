package com.challenge.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.challenge.schema.Challenge;
import com.challenge.GlobalDataStore;
import com.challenge.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FindChallengeActivity extends FragmentActivity {
    private Map<Marker, Integer> markerDataStorePos = new HashMap<Marker, Integer>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        SupportMapFragment fragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        final GoogleMap map = fragment.getMap();
        Location myLoc = GlobalDataStore.locationHelper.getLatestLocation();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLoc.getLatitude(),myLoc.getLongitude()), 15.0f));
        final Random r = new Random();
/*        for(int i = 0; i < 10; i++){
            MarkerOptions m = new MarkerOptions();
            double lat = 51.49 + r.nextDouble() / 100;
            double lng = -0.044 + r.nextDouble() / 100;
            m.position(new LatLng(lat, lng));
            m.title("Challenge " + i);
            map.addMarker(m);
        }                    */

        for (Challenge challenge : GlobalDataStore.challengeList) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(challenge.gpsConstraint.lat, challenge.gpsConstraint.lon));
            //double lat = 51.49 + r.nextDouble() / 100;
            //double lng = -0.044 + r.nextDouble() / 100;
            //markerOptions.position(new LatLng(lat,lng));
            markerOptions.title(challenge.name);
            Marker marker = map.addMarker(markerOptions);
            markerDataStorePos.put(marker, challenge.dataStorePosition);
        }

        map.setMyLocationEnabled(true);

        map.setOnMarkerClickListener(new ChallengeClickListener());
    }

    public class ChallengeClickListener implements GoogleMap.OnMarkerClickListener {
        public boolean onMarkerClick(Marker marker) {
            Intent intent = new Intent(getApplicationContext(), ChallengeActivity.class);
            intent.putExtra(ChallengeActivity.CHALLENGE_ID, markerDataStorePos.get(marker));
            startActivity(intent);
            return true;
        }
    }

}