package com.challenge.async;

import android.os.AsyncTask;
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

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Binnie
 * Date: 27/10/13
 * Time: 21:02
 * To change this template use File | Settings | File Templates.
 */
public class AddUser extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {

        try{
            HttpClient httpclient = new DefaultHttpClient();

            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair("user", params[0]));
            URI uri = URIUtils.createURI("http", "alpine-avatar-381.appspot.com", -1, "/addUser",
                    URLEncodedUtils.format(httpParams, "UTF-8"), null);

            HttpGet getRequest = new HttpGet(uri);
            HttpResponse response = httpclient.execute(getRequest); // Execute request against server, get response
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){ // If everything went ok, read off response
                return true;
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e){
            // Probably should do something here
            String string = e.toString();
        }

        return false;
    }
}
