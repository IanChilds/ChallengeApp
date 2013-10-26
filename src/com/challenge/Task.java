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
    private String description;
    private TaskType type;
    private boolean useGPSConstraint;
    private GPSConstraint gpsConstraint;
    private boolean useTimeConstraint;
    private TimeConstraint timeConstraint;
    private Bitmap photo;

    public enum TaskType {
        GPS, PHOTO, TEXT
    }

    public class GPSConstraint {
        private float lat;
        private float lon;
        private float range;
    }

    public class TimeConstraint {
        private Date startTime;
        private Date endTime;
    }
}

