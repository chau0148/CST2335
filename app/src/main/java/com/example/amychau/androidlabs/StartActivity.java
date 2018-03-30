package com.example.amychau.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_start);

        Button startButton = findViewById(R.id.button);
        startButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(StartActivity.this, ListItemActivity.class);
                        startActivityForResult(intent, 50);
                    }
                }
        );

        Button chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, StartChat.class);
                startActivity(intent);
            }
        });

        Button weatherButton = findViewById(R.id.weatherForecast);
        weatherButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Weather Forecast");
                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivity(intent);
            }
        });

        Button toolbarButton = findViewById(R.id.toolbar);
        toolbarButton.setOnClickListener((v) -> {
            Log.i(ACTIVITY_NAME, "User clicked Test Toolbar");
            Intent intent = new Intent(StartActivity.this, TestToolbar.class);
            startActivity(intent);
        });
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

    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if (data != null && requestCode == 50){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if (responseCode == Activity.RESULT_OK){
            Log.i(ACTIVITY_NAME, "Returned Result OK");
        }
        if (data!= null && requestCode == 60){
            Log.i(ACTIVITY_NAME, "In StartChat.java");
        }
        String messagePassed = data.getStringExtra("Response");
        Toast.makeText(this, messagePassed, Toast.LENGTH_LONG).show();

    }
}

