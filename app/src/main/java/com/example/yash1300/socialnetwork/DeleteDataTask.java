package com.example.yash1300.socialnetwork;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yash 1300 on 29-06-2017.
 */

public class DeleteDataTask extends AsyncTask<String, Void, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try{
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 204){
                return "Deleted successfully";
            } else {
                return "Deletion failed";
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
