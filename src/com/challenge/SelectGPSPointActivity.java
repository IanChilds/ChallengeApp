package com.challenge;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

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
    private GoogleMap map;
    private SeekBar seekBar;
    private MarkerOptions markerOptions = new MarkerOptions();
    private boolean markerSelected;
    private Circle circle;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_gps_point);
        markerSelected = false;
        seekBar = (SeekBar)findViewById(R.id.gps_range);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (markerSelected) {
                    circle.setRadius((double)seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if ((1 + (double)seekBar.getProgress()) / (seekBar.getMax() + 1) < 0.15) {
                    seekBar.setMax(seekBar.getMax() / 2 + 1);
                }
                else if ((1 + (double)seekBar.getProgress()) / (seekBar.getMax() + 1) > 0.85) {
                    seekBar.setMax(seekBar.getMax() * 2 + 1);
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Range is " + (seekBar.getProgress() + 1) + "m", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        SupportMapFragment fragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map = fragment.getMap();
        Intent intent = getIntent();
        double range = intent.getDoubleExtra(RANGE, -1);
        if (range > 0) {
            seekBar.setProgress((int)range);
            LatLng latLng = new LatLng(intent.getDoubleExtra(LATITUDE, 0), intent.getDoubleExtra(LONGITUDE, 0));
            addMarker(latLng, "Marker");
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
                addMarker(latLng, "Marker");
            }
        });
    }

    public void addMarker(LatLng latLng, String title) {
        markerOptions.position(latLng);
        markerOptions.title(title);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        map.addMarker(markerOptions);
        markerSelected = true;
        circle = map.addCircle(new CircleOptions().center(latLng)
                                                  .radius(seekBar.getProgress())
                                                  .strokeWidth(3));
        circle.setRadius(circle.getRadius() * 2);
    }

    public void removeMarker() {
        if (markerSelected) {
            map.clear();
            markerSelected = false;
        }
    }

    public void removeMarker(View view) {
        removeMarker();
    }

    public void submitMarker(View view) {
        if (markerSelected) {
            Intent intent = new Intent();
            intent.putExtra(LATITUDE, markerOptions.getPosition().latitude);
            intent.putExtra(LONGITUDE, markerOptions.getPosition().longitude);
            intent.putExtra(RANGE, (double)seekBar.getProgress());
            setResult(POINT_ADDED, intent);
            finish();
        }
    }

}