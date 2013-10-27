package com.challenge;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class Task {
    public String description;
    public TaskType type;
    public boolean useGPSConstraint;
    public GPSConstraint gpsConstraint;
    public boolean useTimeConstraint;
    public TimeConstraint timeConstraint;
    public Bitmap photo;
    public String submissionText;
    public int dataStorePosition = -1;

    public Task() {

    }

    public enum TaskType {
        GPS, PHOTO, TEXT
    }

    public class GPSConstraint {
        public float lat;
        public float lon;
        public float range;

        @Override
        public String toString(){
            return "" + lat + "," + lon + "," + range;
        }

    }

    public class TimeConstraint {
        public Date startTime;
        public Date endTime;
    }
}

