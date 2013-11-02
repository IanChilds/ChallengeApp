package com.challenge.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.challenge.schema.Challenge;
import com.challenge.schema.Task;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Chris
 * Date: 27/10/13
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
public class GetPhotoTask extends AsyncTask<Void, Void, Bitmap> {
    private Task task;
    private Challenge challenge;
    private String id;

    public GetPhotoTask(Task task, String id){
        this.task = task;
        this.id = id;
    }

    public GetPhotoTask(Challenge challenge, String id){
        this.challenge = challenge;
        this.id = id;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try{
            HttpClient httpclient = new DefaultHttpClient();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair("id", id));
            String target = task != null ? "/taskPhoto" : "/challengePhoto";
            URI uri = URIUtils.createURI("http", "norse-feat-380.appspot.com", -1, target, URLEncodedUtils.format(httpParams, "UTF-8"), null);

            HttpGet getRequest = new HttpGet(uri);
            HttpResponse response = httpclient.execute(getRequest); // Execute request against server, get response
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){ // If everything went ok, read off response
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                return BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.size());
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e){
            // Probably should do something here
        }
        return null;
    }

    @Override
    public void onPostExecute(Bitmap b){
        if(challenge != null)
            challenge.photo = b;

        if(task != null)
            task.examplePhoto = b;
    }
}
