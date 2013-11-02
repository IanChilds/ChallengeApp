package com.challenge;

import android.graphics.Bitmap;
import com.challenge.schema.Challenge;
import com.challenge.schema.Profile;
import com.challenge.schema.Task;

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
    public static LocationHelper locationHelper;

    public static List<Task> taskList = new ArrayList<Task>();
    public static List<Challenge> challengeList = new ArrayList<Challenge>();

    public static int addTask(Task task) {
        task.dataStorePosition = taskList.size();
        taskList.add(task);
        return taskList.size() - 1;
    }

    public static void setTask(int pos, Task task) {
        task.dataStorePosition = pos;
        taskList.set(pos, task);
    }

    public static Task getTask (int taskID) {
        return taskList.get(taskID);
    }

    public static int addChallenge(Challenge challenge) {
        challenge.dataStorePosition = challengeList.size();
        challengeList.add(challenge);
        return challenge.dataStorePosition;
    }

    public static Challenge getChallenge (int challengeID) {
        return challengeList.get(challengeID);
    }

    public static Bitmap lastPhotoTaken;

    public static void storeLastPhotoTaken(Bitmap photo) {
        lastPhotoTaken = photo;
    }

    public static Bitmap getLastPhotoTaken() {
        return lastPhotoTaken;
    }


}
