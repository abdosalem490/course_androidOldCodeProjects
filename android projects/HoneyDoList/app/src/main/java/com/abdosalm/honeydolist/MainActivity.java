package com.abdosalm.honeydolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText todoListEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveButton = findViewById(R.id.saveButton);
        todoListEditText = findViewById(R.id.todoListEditText);

        saveButton.setOnClickListener(v -> {
            String message = todoListEditText.getText().toString();
            try {
                WriteToFile(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        String message = readFromFile();
        todoListEditText.setText(message);
    }
    private void WriteToFile(String message) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("todolist.txt", Context.MODE_PRIVATE));
        outputStreamWriter.write(message);
        outputStreamWriter.close();
    }
    private String readFromFile(){
        String result = "";
        try {
            InputStream inputStream = openFileInput("todolist.txt");
            if (inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String tempString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((tempString = bufferedReader.readLine()) != null){
                    stringBuilder.append(tempString);
                }
                inputStream.close();
                result = stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}