package com.example.amychau.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);



    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        //mi.getItemId();
        switch(mi.getItemId()){
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle("Do you want to go back?");
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("User clicked", "OK Button");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("User clicked", "Cancel Button");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");
                AlertDialog.Builder build = new AlertDialog.Builder(TestToolbar.this);
                LayoutInflater inflater = TestToolbar.this.getLayoutInflater();
                build.setView(inflater.inflate(R.layout.custom_dialog, null))
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /**
                                 * string entered will be the new message displayed by
                                 * the Snackbar object when selecting menu item 1
                                 */
                            }
                        });
                break;
            case R.id.action_four:
                Log.d("Toolbar", "About Selected");
                Toast.makeText(getApplicationContext(), "Version 1.0 by Amy Chau", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
