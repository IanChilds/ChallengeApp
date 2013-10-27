package com.challenge;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public class VerifyPhotoActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get data from server to populate the view.
    }

    public void replyNotSure(View view) {

        finish();
    }

    public void replyAnswerIsWrong(View view) {

        finish();
    }

    public void replyAnswerIsCorrect(View view) {

        finish();
    }
}