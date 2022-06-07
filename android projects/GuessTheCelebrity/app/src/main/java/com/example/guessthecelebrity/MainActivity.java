package com.example.guessthecelebrity;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ArrayList<String> celebURLs = new ArrayList<>();
    ArrayList<String> celebNames = new ArrayList<>();
    int ChosenCeleb =0 ;
    ImageView imageView;
    int locationOfCorrectAnswer = 0;
    String[] answers = new String[4];
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView =findViewById(R.id.imageView);
        button0 = findViewById(R.id.button1);
        button1 = findViewById(R.id.button2);
        button2 = findViewById(R.id.button3);
        button3 = findViewById(R.id.button4);
        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            result = task.execute("//http://www.posh24.se/kandisar").get();

            String [] splitResult = result.split("<div class=\"sidebarContainer\">");
            Pattern p =  Pattern.compile("src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);
            while (m.find())
            {
                celebURLs.add(m.group(1));
            }
            p =  Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);
            while (m.find())
            {
                celebNames.add(m.group(1));
            }
            createNewQuestion();
           // Log.d(TAG, "onCreate: "+result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void celebChosen(View view)
    {
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer)))
        {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Wrong : it was "+celebNames.get(ChosenCeleb), Toast.LENGTH_SHORT).show();
        }
        createNewQuestion();

    }
    public class ImageDownloader extends AsyncTask<String , Void , Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class DownloadTask extends AsyncTask<String , Void , String>
    {
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1)
                {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
    public void createNewQuestion()
    {
        try {
        Random random = new Random();
        ChosenCeleb = random.nextInt(celebURLs.size());
        ImageDownloader imageTask = new ImageDownloader();
        Bitmap celebImage;

            celebImage = imageTask.execute(celebURLs.get(ChosenCeleb)).get();

        imageView.setImageBitmap(celebImage);
        locationOfCorrectAnswer = random.nextInt(4);
        int inCorrectAnswerLocation ;
        for (int i = 0 ; i < 4 ; i++)
        {
            if (i == locationOfCorrectAnswer)
            {
                answers[i] = celebNames.get(ChosenCeleb);
            }else{
                inCorrectAnswerLocation = random.nextInt(celebURLs.size());
                while (inCorrectAnswerLocation == ChosenCeleb)
                {
                    inCorrectAnswerLocation = random.nextInt(celebURLs.size());
                }
                answers[i] = celebNames.get(inCorrectAnswerLocation);
            }
        }
        button0.setText(answers[0]);
        button1.setText(answers[1]);
        button2.setText(answers[2]);
        button3.setText(answers[3]);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}