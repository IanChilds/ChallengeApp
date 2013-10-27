package com.challenge;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.logging.Logger;

public class LocationHelper {
    private LocListener listener;
    private Location latestLocation;

    public LocationHelper(LocationManager locationManager){
        listener = new LocListener();
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
    }

    public Location getLatestLocation(){
        return latestLocation;
    }

    public class LocListener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            latestLocation = location;

            Log.i("GPSTAG", location.getLatitude() + ", " + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}
