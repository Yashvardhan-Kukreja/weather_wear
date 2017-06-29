package com.example.yash1300.socialnetwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
TextView t1, t2;
    Button btn;
    EditText id, password;
    List<String> emailList = new ArrayList<>();
    List<String> passwordList = new ArrayList<>();
    List<String> namesList = new ArrayList<>();
    static final String get_url = "http://192.168.1.3:8090/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*** Here is where I used Wifi Manager***/
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        Toast.makeText(MainActivity.this, ip, Toast.LENGTH_SHORT).show();
         /***    Till here    ***/

         
        getWindow().setStatusBarColor(Color.parseColor("#2196f3"));
        getWindow().setNavigationBarColor(Color.parseColor("#1565c0"));

        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.sometext);
        btn = (Button) findViewById(R.id.loginbtn);

        id = (EditText) findViewById(R.id.emailid);
        password = (EditText) findViewById(R.id.password);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new GetDataTask(emailList, passwordList, namesList, MainActivity.this).execute(get_url);

        progressDialog.dismiss();
        t1.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registration.class);
                startActivity(i);
            }
        });

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = emailList.indexOf(id.getText().toString());
                if (!emailList.contains(id.getText().toString())){
                    Toast.makeText(MainActivity.this, "User not registered yet", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.getText().toString().equals(passwordList.get(index))){
                        Intent i = new Intent(MainActivity.this, LoginSuccess.class);
                        i.putExtra("name", namesList.get(index));
                        i.putExtra("id", emailList.get(index));
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "Wrong password entered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
