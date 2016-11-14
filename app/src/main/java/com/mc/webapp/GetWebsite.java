package com.mc.webapp;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by Himanshu Sagar on 12-11-2016.
 */
public class GetWebsite extends AsyncTask<String , Object ,String>
{
    private String server_response;
    private WebView webView;
    private OnTaskCompleted listener;
    private String mimeType;
    private String encoding;

    public GetWebsite(WebView webView , OnTaskCompleted listener)
    {
        this.listener = listener;
        this.webView = webView;
        this.server_response ="NOT FOUND";

    }

    @Override
    protected String doInBackground(String... strings) {

        URL url;
        HttpURLConnection urlConnection = null;


        try
        {
            url = new URL(strings[0]);
            Log.d(TAG , url.toString() );

            urlConnection = (HttpURLConnection) url.openConnection();

            int responseCode = urlConnection.getResponseCode();

            mimeType = urlConnection.getHeaderField("Content-Type");
            encoding = urlConnection.getContentEncoding();

            if(responseCode == HttpURLConnection.HTTP_OK)
            {

                server_response = readStream(urlConnection.getInputStream());

            }

        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        listener.onTaskCompleted(server_response , mimeType , encoding);

    }

// Converting InputStream to String

    private String readStream(InputStream in)
    {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }



}
