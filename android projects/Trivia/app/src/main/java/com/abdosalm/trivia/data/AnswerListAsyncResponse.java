package com.abdosalm.trivia.data;

import com.abdosalm.trivia.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse
{
    void processFinished(ArrayList<Question> questionArrayList);
}
