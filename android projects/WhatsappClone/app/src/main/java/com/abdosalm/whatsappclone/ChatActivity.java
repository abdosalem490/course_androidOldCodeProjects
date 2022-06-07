package com.abdosalm.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    EditText chatEditText;
    String activeUser = "";
    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Intent intent = getIntent();
        activeUser = intent.getStringExtra("username");

        setTitle("Chat with " + activeUser);

        ListView chatListView = findViewById(R.id.chatListView);
        chatEditText = findViewById(R.id.chatEditText);

        adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1,messages);

        chatListView.setAdapter(adapter);

        ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("Message");
        query1.whereEqualTo("sender",ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("recipient",activeUser);

        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Message");
        query1.whereEqualTo("sender",activeUser);
        query1.whereEqualTo("recipient",ParseUser.getCurrentUser().getUsername());


        List<ParseQuery<ParseObject>> queries = new ArrayList<>();
        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    if (objects.size() > 0){
                        messages.clear();
                        for (ParseObject message : objects){
                            String messageContent = message.getString("message");
                            if (!message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())){
                                messageContent = "> "+messageContent;
                            }
                            messages.add(messageContent);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
    public void sendChat(View view){
        String chat = chatEditText.getText().toString();

        ParseObject message = new ParseObject("Message");
        message.put("sender", ParseUser.getCurrentUser().getUsername());
        message.put("recipient",activeUser);
        message.put("message",chat);
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    messages.add(chat);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        chatEditText.setText("");
    }
}