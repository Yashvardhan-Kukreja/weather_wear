package com.example.yash1300.socialnetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yash 1300 on 28-06-2017.
 */

public class GetDataTask extends AsyncTask<String, Void, String> {
    List<String> emails = new ArrayList<>();
    List<String> passwords = new ArrayList<>();
    List<String> names = new ArrayList<>();
    Context context;
    String email;
    ProgressDialog progressDialog;

    public GetDataTask(List<String> emails, List<String> passwords, List<String> names, Context context) {
        this.emails = emails;
        this.passwords = passwords;
        this.names = names;
        this.context = context;
    }

    public GetDataTask(List<String> emails, List<String> passwords, List<String> names, Context context, String email) {
        this.emails = emails;
        this.passwords = passwords;
        this.names = names;
        this.context = context;
        this.email = email;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();
        try{
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine())!=null){
                result.append(line).append("\n");
            }
            if (bufferedReader!=null){
                bufferedReader.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                emails.add(jsonObject.getString("email"));
                passwords.add(jsonObject.getString("password"));
                names.add(jsonObject.getString("firstName")+" "+jsonObject.getString("lastName"));

            }
            if (progressDialog!=null){
                progressDialog.dismiss();
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        super.onPostExecute(s);
    }


}
