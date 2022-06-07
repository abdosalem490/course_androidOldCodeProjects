package com.abdosalm.truecitizen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.abdosalm.truecitizen.databinding.ActivityMainBinding;
import com.abdosalm.truecitizen.model.Question;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private final Question[] questionBank = new Question[]{
            new Question(R.string.question_amendments, false), //correct: 27
            new Question(R.string.question_constitution, true),
            new Question(R.string.question_declaration, true),
            new Question(R.string.question_independence_rights, true),
            new Question(R.string.question_religion, true),
            new Question(R.string.question_government, false),
            new Question(R.string.question_government_feds, false),
            new Question(R.string.question_government_senators, true)
    };

    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        binding.questionTextView.setText(questionBank[currentQuestionIndex++].getAnswerResId());

        binding.nextButton.setOnClickListener(v-> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.length;
               updateQuestion();
        });
        binding.prevButton.setOnClickListener(v->{
            if (currentQuestionIndex > 0)
            {
                currentQuestionIndex = (currentQuestionIndex - 1) % questionBank.length;
                updateQuestion();
            }
        });
        binding.trueButton.setOnClickListener(v->
            checkAnswer(true)
        );
        binding.falseButton.setOnClickListener(v->
                checkAnswer(false)
        );
    }

    private void updateQuestion() {
        binding.questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
    }
    private void checkAnswer(boolean userChosenCorrect) {
        boolean answerIsCorrect = questionBank[currentQuestionIndex].isAnswerTrue();
        int messageId;

        if (userChosenCorrect == answerIsCorrect){
            messageId = R.string.correct_answer;
        }else{
            messageId = R.string.wrong_answer;
        }
        Snackbar.make(binding.imageView , messageId , Snackbar.LENGTH_SHORT).show();
    }
}