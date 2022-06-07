package com.abdosalm.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    int chosenCeleb = 0;
    String[]answers = new String[4];
    int locationOfCorrectAnswer = 0;

    ArrayList<String> celebURLS = new ArrayList<>();
    ArrayList<String> celebNames = new ArrayList<>();

    ImageView imageView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask downloadTask = new DownloadTask();
        imageView = findViewById(R.id.imageView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        String result ="";
        try {
            result  = downloadTask.execute("https://www.imdb.com/list/ls052283250/").get();
            String[] splitResult =result.split("<div class=\"footer filmosearch\">\n" +
                    "    <div class=\"desc\">\n" +
                    "    </div>\n" +
                    "  </div>");

            Pattern p1 = Pattern.compile("src=\"(.*?).jpg\"");
            Matcher m1 = p1.matcher(splitResult[0]);
            while (m1.find()){
                celebURLS.add(m1.group(1)+".jpg");
            }

            Pattern p2 = Pattern.compile("<img alt=\"(.*?)\"");
            Matcher m2 = p2.matcher(splitResult[0]);
            while (m2.find()){
                celebNames.add(m2.group(1));
            }

            newQuestion();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();

        }

    }
    public class DownloadTask extends AsyncTask<String , Void , String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection connection;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();

                InputStream in = connection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1){
                    result.append((char) data);
                    data = reader.read();
                }

                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public class ImageDownloader extends AsyncTask<String , Void , Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public void celebChosen(View view){
        int tag = Integer.parseInt(view.getTag().toString());
        if (tag == locationOfCorrectAnswer){
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Wrong it was "+celebNames.get(chosenCeleb), Toast.LENGTH_SHORT).show();
        }
        newQuestion();
    }
    private void newQuestion(){
        Random rand = new Random();
        chosenCeleb = rand.nextInt(celebURLS.size());

        ImageDownloader imageTask = new ImageDownloader();
        Bitmap celebImage = null;
        try {
            celebImage = imageTask.execute(celebURLS.get(chosenCeleb)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(celebImage);

        locationOfCorrectAnswer = rand.nextInt(4);

        int inCorrectAnswerLocation;
        for (int i = 0 ; i < 4 ; i++){
            if (i == locationOfCorrectAnswer){
                answers[i] = celebNames.get(chosenCeleb);
            }else{
                inCorrectAnswerLocation = rand.nextInt(celebURLS.size());
                while (inCorrectAnswerLocation == chosenCeleb){
                    inCorrectAnswerLocation = rand.nextInt(celebURLS.size());
                }
                answers[i] = celebNames.get(inCorrectAnswerLocation);
            }
        }
        button0.setText(answers[0]);
        button1.setText(answers[1]);
        button2.setText(answers[2]);
        button3.setText(answers[3]);
    }
}