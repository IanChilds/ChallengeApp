package com.challenge;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class Challenge {
    public String creatorName;
    public String name;
    public String description;
    public int estimatedTime;
    public float rating;
    public int dataStorePosition;
    public Bitmap photo;
    public List<Task> tasks = new ArrayList<Task>();
    public int numTasksVisible;
    public GPSConstraint gpsConstraint;
}
