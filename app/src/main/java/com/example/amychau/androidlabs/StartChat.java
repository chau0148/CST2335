package com.example.amychau.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class StartChat extends Activity {

    ListView list;
    EditText text;
    Button sendButton;
    ArrayList<String> ar;
    //ArrayAdapter<String> arAdapter;
    ChatAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_chat);

        list = (ListView) findViewById(R.id.chatView);
        text = (EditText) findViewById(R.id.editChat);
        sendButton = (Button) findViewById(R.id.sendButton);

        ar = new ArrayList<>();
//        arAdapter = new ArrayAdapter<String>(this, R.layout.activity_start_chat);
        messageAdapter = new ChatAdapter(this);
//        list.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = text.getText().toString();
                ar.add(content);
                list.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();
                text.setText("");
            }
        });


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
    }
}
