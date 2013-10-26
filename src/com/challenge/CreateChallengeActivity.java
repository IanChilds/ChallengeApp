package com.challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class CreateChallengeActivity extends Activity {
    public final static int TAKE_PHOTO_REQUEST = 1;
    private ImageView photo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_challenge);
        photo = (ImageView)findViewById(R.id.create_challenge_photo);
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(this, TakePhotoActivity.class);
        startActivityForResult(intent, TAKE_PHOTO_REQUEST);
        // TODO: need to verify that the TakePhotoActivity only returns RESULT_OK once it has taken a photo.
    }

    public void createNewTask(View view) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST:
                if (resultCode == TakePhotoActivity.PHOTO_TAKEN) {
                    photo.setImageBitmap(GlobalDataStore.getLastPhotoTaken());
                }

            default:
                // Don't expect to hit this
        }
    }
}