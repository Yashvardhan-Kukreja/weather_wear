package com.example.yash1300.socialnetwork;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yash 1300 on 29-06-2017.
 */

public class PutDataTask extends AsyncTask<String, Void, String> {

    String email, password;

    public PutDataTask(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        BufferedWriter bufferedWriter = null;
        try {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newpassword", password);
        jsonObject.put("email", email);

        URL url = new URL(params[0]);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(10000);
        urlConnection.setRequestMethod("PUT");
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-type", "application/json");
        urlConnection.connect();

        OutputStream outputStream = urlConnection.getOutputStream();
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(jsonObject.toString());
        bufferedWriter.flush();

        if (bufferedWriter != null){
            bufferedWriter.close();
        }

        if (urlConnection.getResponseCode() == 200){
            return "Updated successfully";
        } else {
            return "Update failed";
        }
    } catch (JSONException e){
        e.printStackTrace();
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
