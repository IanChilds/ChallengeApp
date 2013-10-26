package com.challenge;

import android.graphics.Bitmap;

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

    public static Bitmap lastPhotoTaken;

    public static void storeLastPhotoTaken(Bitmap photo) {
        lastPhotoTaken = photo;
    }

    public static Bitmap getLastPhotoTaken() {
        return lastPhotoTaken;
    }
}
