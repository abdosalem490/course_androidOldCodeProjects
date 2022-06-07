package com.abdosalm.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button goButton;
    Button playAgain;

    TextView sumTextView;
    TextView resultTextView;
    TextView scoreTextView;
    TextView timerTextView;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    ConstraintLayout playLayout;

    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;

    ArrayList<Integer> answers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);
        sumTextView = findViewById(R.id.sumTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        playAgain = findViewById(R.id.playAgainButton);
        playLayout = findViewById(R.id.playLayout);

        newQuestion();

        playAgain(timerTextView);
    }
    public void start(View view){
        goButton.setVisibility(View.GONE);
        playLayout.setVisibility(View.VISIBLE);
    }
    public void choseAnswer(View view){
        int tag = Integer.parseInt(view.getTag().toString());
        if (tag == locationOfCorrectAnswer){
            resultTextView.setText("Correct");
            score++;
        }else{
            resultTextView.setText("Wrong");
        }
        numberOfQuestions++;
        scoreTextView.setText(score+"/"+numberOfQuestions);
        newQuestion();

    }
    public void newQuestion(){
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        answers.clear();

        sumTextView.setText(a+"+"+b);

        locationOfCorrectAnswer = rand.nextInt(4);
        for (int i = 0 ; i < 4 ; i++ ){
            if (i == locationOfCorrectAnswer){
                answers.add(a+b);
            }else{
                int wrongAnswer = rand.nextInt(41);
                while (wrongAnswer == a+b){
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }
        button0.setText(String.valueOf(answers.get(0)));
        button1.setText(String.valueOf(answers.get(1)));
        button2.setText(String.valueOf(answers.get(2)));
        button3.setText(String.valueOf(answers.get(3)));
    }
    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;
        newQuestion();
        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        resultTextView.setText("");
        new CountDownTimer(10100 , 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText((int) (millisUntilFinished/1000) +"s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                playAgain.setVisibility(View.VISIBLE);
            }
        }.start();

        playAgain.setVisibility(View.GONE);
    }
}