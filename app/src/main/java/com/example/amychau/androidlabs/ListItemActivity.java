package com.example.amychau.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemActivity extends Activity {

    protected static final String ACTIVITY_NAME = "ListItemActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton startButton;
    Switch switch1;
    CheckBox checkBox1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_list_item);

        //Opens Camera when ImageButton is clicked
        startButton = findViewById(R.id.imageButton);
        startButton.setOnClickListener(
                new ImageButton.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                }
        );

        //Shows if switch is on or off
        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(getApplicationContext(), "SWITCH IS ON", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "SWITCH IS OFF", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Show dialog box for checkBox

        checkBox1 = (CheckBox) findViewById(R.id.checkBox);
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemActivity.this);
                    builder
                            .setTitle(R.string.dialog_title)
                            .setMessage(R.string.dialog_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    //User clicked OK Button
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("Response", "ListItemsActivity passed: My information to share");
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int id) {
                                    //User Cancelled the dialog
                                }
                            })
                            .show();
                }
            }
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && responseCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            startButton.setImageBitmap(image);
        }
    }


}
