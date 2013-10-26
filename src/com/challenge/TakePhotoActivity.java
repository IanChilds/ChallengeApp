package com.challenge;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 26/10/13
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public class TakePhotoActivity extends Activity implements Camera.PictureCallback {
    public static final int PHOTO_TAKEN = 5;
    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;
    private Camera          camera = null;
    SurfaceHolder.Callback  surfaceCallback = new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {}
        public void surfaceDestroyed(SurfaceHolder holder) {}

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            try {
                camera.setPreviewDisplay(previewHolder);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            camera.startPreview();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        camera = Camera.open();
        Camera.Parameters cameraParameters = camera.getParameters();
        int thinnest = Integer.MAX_VALUE;
        for (Camera.Size s : cameraParameters.getSupportedPictureSizes()) {
            if (s.width < thinnest) {
                cameraParameters.setPictureSize(s.width, s.height);
                thinnest = s.width;
            }
        }

        camera.setParameters(cameraParameters);
        camera.setDisplayOrientation(90);
        camera.startPreview();
    }

    @Override
    public void onPause() {
        camera.stopPreview();
        camera.release();
        super.onPause();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_photo);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        preview = (SurfaceView)findViewById(R.id.camera_surface);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ViewGroup.LayoutParams params = preview.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = (int)(params.width * (4.0 / 3.0));
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        preview.setLayoutParams(params);
    }

    public void takePhoto(View view){
        System.out.println("");
        camera.takePicture(null, null, this);
    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
 //       int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
 //       bitmap = Bitmap.createBitmap(bitmap, 0, 0, size, size);
        bitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, false);
        GlobalDataStore.storeLastPhotoTaken(bitmap);
        setResult(PHOTO_TAKEN, null);
        finish();
    }
}