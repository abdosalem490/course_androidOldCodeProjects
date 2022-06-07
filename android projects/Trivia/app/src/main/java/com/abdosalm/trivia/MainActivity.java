package com.abdosalm.trivia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.abdosalm.trivia.data.Repository;
import com.abdosalm.trivia.databinding.ActivityMainBinding;
import com.abdosalm.trivia.model.Question;
import com.abdosalm.trivia.model.Score;
import com.abdosalm.trivia.util.Prefs;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    private int scoreCounter = 0;
    private Score score;
    private Prefs prefs;
    List<Question> questionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        score = new Score();
        binding.scoreText.setText(String.format("Current Score : %s", String.valueOf(score.getScore())));
        prefs = new Prefs(this);

        binding.highestScoreText.setText(String.format("Highest score : %s", String.valueOf(prefs.getHighestScore())));
        currentQuestionIndex = prefs.getState();

        questionList =  new Repository().getQuestions(questionArrayList -> {
            binding.questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
            updateCounter(questionArrayList);
        });

        binding.buttonNext.setOnClickListener(v -> {
            getNextQuestion();
        });
        binding.buttonTrue.setOnClickListener(v -> {
            checkAnswer(true);
            updateQuestion();
        });
        binding.buttonFalse.setOnClickListener(v -> {
            checkAnswer(false);
            updateQuestion();
        });
        binding.shareButton.setOnClickListener(v ->{
            shareScore();
        });
    }

    private void shareScore(){
        String message = "My current score is "+score.getScore() +" and My highest score is "+prefs.getHighestScore();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT , "I am playing Trivia");
        intent.putExtra(Intent.EXTRA_TEXT , message);
        startActivity(intent);
    }

    private void getNextQuestion() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
        updateQuestion();
    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswerTrue();
        int snackMessageId = 0;
        if (answer == userChoseCorrect)
        {
            snackMessageId = R.string.correct_answer;
            fadeAnimationView();
            addPoints();
        }else{
            snackMessageId = R.string.incorrect;
            shakeAnimation();
            deductPoints();
        }
        Snackbar.make(binding.cardView , snackMessageId , Snackbar.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.questionTextView.setText(question);
        updateCounter((ArrayList<Question>) questionList);
    }
    private void updateCounter(ArrayList<Question> questionArrayList)
    {
        binding.textViewOutOf.setText(String.format(getString(R.string.formated), currentQuestionIndex, questionArrayList.size()));
    }
    private void fadeAnimationView()
    {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void shakeAnimation()
    {
        Animation shake = AnimationUtils.loadAnimation(this , R.anim.shake_animation);
        binding.cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void addPoints()
    {
        scoreCounter += 100;
        score.setScore(scoreCounter);
        binding.scoreText.setText(String.format("Current Score : %s", String.valueOf(score.getScore())));
    }
    private void deductPoints()
    {
        if (scoreCounter > 0)
        {
            scoreCounter -= 100;
            score.setScore(scoreCounter);
        }else{
            scoreCounter = 0;
            score.setScore(scoreCounter);
        }
        binding.scoreText.setText(String.format("Current Score : %s", String.valueOf(score.getScore())));
    }

    @Override
    protected void onPause() {
        prefs.saveHighestScore(score.getScore());
        prefs.setState(currentQuestionIndex);
        super.onPause();
    }
}