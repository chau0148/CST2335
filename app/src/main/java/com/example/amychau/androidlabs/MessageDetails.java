package com.example.amychau.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle bundle = new Bundle();
        bundle.putLong("id", getIntent().getLongExtra("id", 2));
        bundle.putString("message", getIntent().getStringExtra("message"));
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment messageFragment = new MessageFragment();
        messageFragment.setArguments(bundle);
        transaction.replace(R.id.fragmentMessageDetail, messageFragment);
        transaction.commit();

    }
}
