package com.example.yash1300.socialnetwork;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginSuccess extends AppCompatActivity {
    String name;
    String id;
    TextView greet;
    Button changePassword, deleteAccount, logOut;
    List<String> emailList = new ArrayList<>();
    List<String> passwordList = new ArrayList<>();
    List<String> namesList = new ArrayList<>();
    String put_url = "http://192.168.1.3:8090/users";
    String delete_url = "http://192.168.1.3:8090/users/";
    @Override
    public void onBackPressed() {
        Intent i = new Intent(LoginSuccess.this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        getWindow().setStatusBarColor(Color.parseColor("#2196f3"));
        getWindow().setNavigationBarColor(Color.parseColor("#1565c0"));

        name = getIntent().getExtras().getString("name");
        id = getIntent().getExtras().getString("id");
        greet = (TextView) findViewById(R.id.greeting);
        greet.setText("Hello, " + name);

        new GetDataTask(emailList, passwordList, namesList, LoginSuccess.this).execute(put_url);


        changePassword = (Button) findViewById(R.id.changePasswordbtn);
        deleteAccount = (Button) findViewById(R.id.delAccountbtn);
        logOut = (Button) findViewById(R.id.logoutbtn);

        logOut.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginSuccess.this, MainActivity.class);
                startActivity(i);
            }
        });

        changePassword.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int index = emailList.indexOf(id);
                AlertDialog.Builder changePasswordDialogBuilder = new AlertDialog.Builder(LoginSuccess.this);
                View view = getLayoutInflater().inflate(R.layout.change_password_dialog, null, false);
                final EditText originalPassword = (EditText) view.findViewById(R.id.originalPassword);
                final EditText newPassword = (EditText) view.findViewById(R.id.newPassword);
                Button okbtn = (Button) view.findViewById(R.id.okButton);
                Button cancelbtn = (Button) view.findViewById(R.id.cancelButton);

                changePasswordDialogBuilder.setView(view);
                final AlertDialog changePasswordDialog = changePasswordDialogBuilder.create();
                changePasswordDialog.show();

                okbtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (originalPassword.getText().toString().equals(passwordList.get(index))){
                            new PutDataTask(id, newPassword.getText().toString()).execute(put_url);
                            Toast.makeText(LoginSuccess.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                            changePasswordDialog.dismiss();
                        } else {
                            Toast.makeText(LoginSuccess.this, "Original Password entered wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelbtn.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        changePasswordDialog.dismiss();
                    }
                });
            }
        });

        deleteAccount.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteAccountDialogBuilder = new AlertDialog.Builder(LoginSuccess.this);
                View view = getLayoutInflater().inflate(R.layout.delete_account_dialog, null, false);
                Button yes = (Button) view.findViewById(R.id.yesDelete);
                Button no = (Button) view.findViewById(R.id.noDelete);

                deleteAccountDialogBuilder.setView(view);
                final AlertDialog deleteAccountDialog = deleteAccountDialogBuilder.create();
                deleteAccountDialog.show();

                yes.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DeleteDataTask().execute(delete_url+id);
                        Toast.makeText(LoginSuccess.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginSuccess.this, MainActivity.class);
                        startActivity(i);
                    }
                });

                no.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAccountDialog.dismiss();
                    }
                });
            }
        });

    }
}