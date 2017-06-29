package com.example.yash1300.socialnetwork;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yash 1300 on 28-06-2017.
 */
public class PostDataTask extends AsyncTask<String, Void, String> {
    String FirstName, LastName, Emailid, Pass;
    Integer AgeOfUser;
    Context context;

    public PostDataTask(String FirstName, String LastName, String Emailid, Integer AgeOfUser, String Pass, Context context) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Emailid = Emailid;
        this.AgeOfUser = AgeOfUser;
        this.Pass = Pass;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
            /*progressDialog = new ProgressDialog(Registration.this);
            progressDialog.setMessage("Registering...");
            progressDialog.setCancelable(false);
            progressDialog.show();*/
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try{
            JSONObject user = new JSONObject();
            user.put("firstName", FirstName);
            user.put("lastName", LastName);
            user.put("email", Emailid);
            user.put("ageOfUser", AgeOfUser);
            user.put("password", Pass);


            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.connect();

            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(user.toString());
            bufferedWriter.flush();

            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine())!=null){
                result.append(line).append("\n");
            }

                /*if (progressDialog!=null) {
                    progressDialog.dismiss();
                }*/
            if (bufferedReader !=null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }

        } catch (JSONException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
