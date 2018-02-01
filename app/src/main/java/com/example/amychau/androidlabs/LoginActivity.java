package com.example.amychau.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    EditText savedEmail;
    TextView emailText;
    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        try {
                            SharedPreferences sharedPref = getSharedPreferences("defaultEmail", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPref.edit();
                            edit.putString("email", savedEmail.getText().toString());
                            edit.apply();

                            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                            startActivity(intent);
                        } catch (NullPointerException nex){

                        }
                    }
                }
        );

        savedEmail = findViewById(R.id.email);
        emailText = findViewById(R.id.email);

        try {
            SharedPreferences sharedPref = getSharedPreferences("defaultEmail", Context.MODE_PRIVATE);
            String x = sharedPref.getString("email", "");
            emailText.setText(x);
        } catch (NullPointerException nex) {

        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}
