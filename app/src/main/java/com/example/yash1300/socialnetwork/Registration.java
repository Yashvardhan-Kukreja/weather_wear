package com.example.yash1300.socialnetwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Registration extends AppCompatActivity {

    EditText first, last, age, email, password, confirmPassword;
    Button btn;
    String firstName, lastName, ageOfUser, emailid, pass, conpass;
    List<String> emailList = new ArrayList<>();
    List<String> passwordList = new ArrayList<>();
    List<String> namesList = new ArrayList<>();
    ProgressDialog progressDialog;

    static final String post_url = "http://192.168.1.3:8090/users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.parseColor("#2196f3"));
        getWindow().setNavigationBarColor(Color.parseColor("#1565c0"));

        setContentView(R.layout.activity_registration);
        first = (EditText) findViewById(R.id.registerFirst);
        last = (EditText) findViewById(R.id.registerLast);
        age = (EditText) findViewById(R.id.registerAge);
        email = (EditText) findViewById(R.id.registerEmail);
        password = (EditText) findViewById(R.id.registerPassword);
        confirmPassword = (EditText) findViewById(R.id.registerConfirm);

        new GetDataTask(emailList, passwordList, namesList, Registration.this).execute(post_url);


        btn = (Button) findViewById(R.id.registerbtn);

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = first.getText().toString();
                lastName = last.getText().toString();
                ageOfUser = age.getText().toString();
                emailid = email.getText().toString();
                pass = password.getText().toString();
                conpass = confirmPassword.getText().toString();


                if (firstName.isEmpty() || lastName.isEmpty() || ageOfUser.isEmpty() || emailid.isEmpty() || pass.isEmpty() || conpass.isEmpty()){
                    Toast.makeText(Registration.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(Registration.this, "Password and Confirm Password don't match", Toast.LENGTH_SHORT).show();
                } else {

                    if (emailList.contains(emailid)){
                        Toast.makeText(Registration.this, "User already registered to this E-mail ID", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog = new ProgressDialog(Registration.this);
                        progressDialog.setMessage("Registering the user");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        new PostDataTask(firstName, lastName, emailid, Integer.parseInt(ageOfUser), pass, Registration.this).execute(post_url);

                        progressDialog.dismiss();
                        Toast.makeText(Registration.this, "User successfully registered", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Registration.this, MainActivity.class);
                        startActivity(i);
                    }
                }
            }
        });
    }
}
