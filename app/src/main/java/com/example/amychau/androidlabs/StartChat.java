package com.example.amychau.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        if (frameLayout != null){
            //Frame Layout was loaded and you are using the tablet layout,
            // The screen is at least 600 pixels wide
            checkLayout = true;
            Log.i(ACTIVITY_NAME, "Fragment Loaded");
        } else {
            checkLayout = false;
            Log.i(ACTIVITY_NAME, "Fragment Not Loaded");
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

        final FragmentManager manager = getFragmentManager();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = dbHelper.getData();
                //cursor.moveToFirst();
                Log.e("pos",l+"");
                cursor.moveToPosition((int)l);
                long id = cursor.getLong(cursor.getColumnIndex(dbHelper.KEY_ID));
                String message = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_MESSAGE));
                //long id = messageAdapter.getItemId((int) l);

                if(checkLayout){
                //Landscape
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", id);
                    bundle.putString("message", message);
                    MessageFragment mfragment = new MessageFragment();
                    mfragment.setArguments(bundle);
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.message_container, mfragment);
                    transaction.commit();
                }
                else {
                    //Goes to new activity created
                    Intent intent = new Intent(StartChat.this, MessageDetails.class);
                    intent.putExtra("id", id);
                    intent.putExtra("message", message);
                    startActivity(intent);
                }
            }}
        );
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

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = StartChat.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView) result.findViewById(R.id.message_text);
            final String messageTxt = getItem(position);
            message.setText(messageTxt);
            return result;
        }
        @Override
        public long getItemId(int position){
            return position;}
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        dbHelper.close();
        Log.i(ACTIVITY_NAME,  "In onDestroy()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = dbHelper.getData();
        cursor.moveToFirst();
        ar.clear();
        while(!cursor.isAfterLast()){
            String newMessage = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_MESSAGE));
            ar.add(newMessage);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + newMessage);
            cursor.moveToNext();
        }

        messageAdapter.notifyDataSetChanged();
    }
}
