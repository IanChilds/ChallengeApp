package com.challenge;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadChallenge extends AsyncTask<Void, Void, Void> {
    private Challenge challenge;
    private Map<String, Bitmap> images = new HashMap<String, Bitmap>();
    private String xmlString;

    public UploadChallenge(Challenge challenge){
        this.challenge = challenge;

        StringBuilder xmlDoc = new StringBuilder();
        xmlDoc.append("<challenge>\n");
        xmlDoc.append("<name>" + challenge.name + "</name>\n");
        xmlDoc.append("<description>" + challenge.description + "</description>\n");
        xmlDoc.append("<creator>" + challenge.creatorName + "</creator>\n");
        xmlDoc.append("<startLocation>51.708100,0.187800</startLocation>\n");

        images.put("image", challenge.photo);

        int imageNum = 1;
        for(Task task : challenge.tasks){
            xmlDoc.append("<task>\n");
            xmlDoc.append("<description>" + task.description + "</description>\n");

            if(task.type == Task.TaskType.PHOTO){
                String taskImage = "taskImage" + (imageNum++);
                images.put(taskImage, task.examplePhoto);
                xmlDoc.append("<photoEvidence>" + taskImage + "</photoEvidence>\n");
            } else if(task.type == Task.TaskType.TEXT) {
                xmlDoc.append("<textEvidence>" + task.text + "</textEvidence>\n");
            }

            if(task.useTimeConstraint)
                xmlDoc.append("<timeEvidence>" + "30.0" + "</timeEvidence>\n");

            if(task.useGPSConstraint)
                xmlDoc.append("<gpsEvidence>" + task.gpsConstraint.toString() + "</gpsEvidence>\n");

            xmlDoc.append("</task>\n");
        }
        xmlDoc.append("</challenge>\n");

        xmlString = xmlDoc.toString();
    }

    public Void doInBackground(Void... input) {
        String responseString;
        try{
            String postURL = "http://norse-feat-380.appspot.com/uploadChallenge"; // URL on server for echo test
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(postURL);

            MultipartEntity fileEntity = new MultipartEntity(HttpMultipartMode.STRICT);

            for(String key : images.keySet()){
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                images.get(key).compress(Bitmap.CompressFormat.PNG, 50, byteStream);
                fileEntity.addPart(key, new ByteArrayBody(byteStream.toByteArray(), "image/png", key + ".png"));
            }

            fileEntity.addPart("challenge", new StringBody(xmlString));

            postRequest.setEntity(fileEntity);
            HttpResponse response = httpclient.execute(postRequest); // Execute request against server, get response
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){ // If everything went ok, read off response
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e){
            responseString = e.toString();
        }

        return null;
    }

    public void onPostExecute(String response){ }
}