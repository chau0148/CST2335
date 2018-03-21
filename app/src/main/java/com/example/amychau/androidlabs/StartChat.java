package com.example.amychau.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lab5.ChatDatabaseHelper;

public class StartChat extends Activity {

    ListView list;
    EditText text;
    Button sendButton;
    ArrayList<String> ar = new ArrayList<>();
    ChatAdapter messageAdapter;
    Boolean checkLayout;
    protected static final String ACTIVITY_NAME = "ChatWindow";

    SQLiteDatabase db;
    ChatDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_chat);

        list = (ListView) findViewById(R.id.chatView);
        text = (EditText) findViewById(R.id.editChat);
        sendButton = (Button) findViewById(R.id.sendButton);
        View frameLayout = findViewById(R.id.message_container);

        if (frameLayout == null){
            // Frame layout wasn't loaded and you are using the phone layout
            frameLayout.setOnTouchListener((View.OnTouchListener) this);
            setContentView(R.layout.activity_start_chat);
        } else {
            //Frame Layout was loaded and you are using the tablet layout,
            // The screen is at least 600 pixels wide
            frameLayout.setOnTouchListener((View.OnTouchListener) this);
            setContentView(frameLayout);

        }

        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

       Cursor cursor = dbHelper.getData();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String newMessage = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_MESSAGE));
            ar.add(newMessage);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + newMessage);
            cursor.moveToNext();
        }

        //prints out the name of each colum returned by the cursor
        for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++){
            cursor.getColumnName(columnIndex);
            Log.i(ACTIVITY_NAME, "Cursor's column count = " +cursor.getColumnCount());
        }
        // Messages in the chat stored in an Arraylist
        messageAdapter = new ChatAdapter(this);
        list.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Puts the message content into an array and display it in the listView
                String content = text.getText().toString();
                ar.add(content);
                list.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();

                //Insert the new message into the database, contentValues object will put the new message
                ContentValues contentValues = new ContentValues();
                contentValues.put(dbHelper.KEY_MESSAGE, text.getText().toString());
                long insertCheck = db.insert(dbHelper.TABLE_NAME, "null", contentValues);
                Log.i("StartChat", "insert data result: " + insertCheck);
                text.setText("");
            }
        });

       // list.setOnItemClickListener();
    }


    private class ChatAdapter extends ArrayAdapter<String> {
        Context context;
        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return ar.size();
        }

        public String getItem(int position){
            return ar.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = StartChat.this.getLayoutInflater();
            View result = null;
            if(position%2==0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }

        public long getId(int position){
            return position;
        }

        public long getItemId(int position){ return position; }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        dbHelper.close();
        Log.i(ACTIVITY_NAME,  "In onDestroy()");
    }
}
