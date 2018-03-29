package com.example.amychau.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import lab5.ChatDatabaseHelper;

import static com.example.amychau.androidlabs.StartChat.ACTIVITY_NAME;

public class MessageFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM = "_id";
    private String item;
    Boolean checkLayout;
    TextView messageTxt;
    TextView ID;
    Button deleteBtn;

    SQLiteDatabase db;
    ChatDatabaseHelper dbHelper;

    View view;
    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new ChatDatabaseHelper(getActivity());
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_fragment, container, false);

        messageTxt = view.findViewById(R.id.messageHere);
        ID = view.findViewById(R.id.idText);
        View frameLayout = view.findViewById(R.id.message_container);

        if (frameLayout != null){
            //Frame Layout was loaded and you are using the tablet layout,
            // The screen is at least 600 pixels wide
            checkLayout = true;
            Log.i(ACTIVITY_NAME, "Fragment Loaded");
        } else {
            checkLayout = false;
            Log.i(ACTIVITY_NAME, "Fragment Not Loaded");
        }

        messageTxt.setText(getArguments().getString("message"));
        ID.setText(Long.toString(getArguments().getLong("id")));
        final long id = getArguments().getLong("id");

        // Inflate the layout for this fragment
        if (item != null){
            ((TextView) view.findViewById(R.id.messageHere)).setText(item);
        }

        deleteBtn = view.findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.removeData(id);
            }
        });

        return view;
    }


}
