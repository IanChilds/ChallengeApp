package com.challenge;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class Task {
    public String description;
    public TaskState taskState;
    public TaskType type;
    public boolean useGPSConstraint;
    public GPSConstraint gpsConstraint;
    public GPSConstraint gpsConstraintSubmitted;
    public boolean useTimeConstraint;
    public TimeConstraint timeConstraint;
    public TimeConstraint timeConstraintSubmitted;
    public Bitmap examplePhoto;
    public Bitmap photoSubmitted;
    public boolean provideExamplePhoto;
    public String text;
    public String textSubmitted;
    public int dataStorePosition = -1;
    public static Map<TaskState, String> stateToString = new HashMap<TaskState, String>(3);
    public static final String INCOMPLETE = "?";
    public static final String CORRECT = "\u2713";
    public static final String INCORRECT = "X";

    static {
        stateToString.put(TaskState.CORRECT, CORRECT);
        stateToString.put(TaskState.INCOMPLETE, INCOMPLETE);
        stateToString.put(TaskState.INCORRECT, INCORRECT);
    }

    public enum TaskType {
        GPS, PHOTO, TEXT
    }

    public enum TaskState {
        CORRECT, INCOMPLETE, INCORRECT;
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

