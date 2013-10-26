package com.challenge;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public class GlobalDataStore {
    public static String currUser;
    public static Profile currProfile;

    public static List<Task> taskList = new ArrayList<Task>();
    public static int addTask(Task task) {
        taskList.add(task);
        return taskList.size() - 1;
    }

    public static Task getTask (int taskID) {
        return taskList.get(taskID);
    }

    public static Bitmap lastPhotoTaken;

    public static void storeLastPhotoTaken(Bitmap photo) {
        lastPhotoTaken = photo;
    }

    public static Bitmap getLastPhotoTaken() {
        return lastPhotoTaken;
    }


}
