package com.challenge;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class FetchChallenges extends AsyncTask<Void, Void, List<Challenge>> {
    @Override
    protected List<Challenge> doInBackground(Void... params) {
        List<Challenge> challenges = new ArrayList<Challenge>();

        try{
            HttpClient httpclient = new DefaultHttpClient();

            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            URI uri = URIUtils.createURI("http", "norse-feat-380.appspot.com", -1, "/getChallenges",
                    URLEncodedUtils.format(httpParams, "UTF-8"), null);

            HttpGet getRequest = new HttpGet(uri);
            HttpResponse response = httpclient.execute(getRequest); // Execute request against server, get response
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){ // If everything went ok, read off response
                String result = EntityUtils.toString(response.getEntity());

                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                StringReader sr = new StringReader(result);
                InputSource is = new InputSource(sr);
                Document d = documentBuilder.parse(is);
                NodeList challengeList = d.getElementsByTagName("challenge");
                for(int i = 0; i < challengeList.getLength(); i++){
                    Challenge challenge = new Challenge();
                    Element challengeTag = (Element)challengeList.item(i);

                    String challengeID = challengeTag.getElementsByTagName("id").item(0).getTextContent();
                    challenge.name = challengeTag.getElementsByTagName("name").item(0).getTextContent();
                    challenge.description = challengeTag.getElementsByTagName("description").item(0).getTextContent();
                    challenge.creatorName = challengeTag.getElementsByTagName("creator").item(0).getTextContent();
                    //challenge.startLocation = challengeTag.getElementsByTagName("name").item(0).getTextContent();
                    NodeList taskList = challengeTag.getElementsByTagName("task");
                    for(int j = 0; j < taskList.getLength(); j++){
                        Task task = new Task();
                        Element taskTag = (Element)taskList.item(j);
                        task.description = taskTag.getElementsByTagName("description").item(0).getTextContent();
                        String taskID = taskTag.getElementsByTagName("id").item(0).getTextContent();
                        challenge.tasks.add(task);
                    }

                    //TODO: startAsync to set photos
                }
            } else{
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e){
            // Probably should do something here
        }

        return challenges;
    }

    @Override
    protected void onPostExecute(List<Challenge> challenges){
        //TODO: You now have the challenges
    }
}
