package com.abdosalm.trivia.data;

import com.abdosalm.trivia.controller.AppController;
import com.abdosalm.trivia.model.Question;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository
{
    private static final String TAG = "Repository";

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";


    ArrayList<Question> questionArrayList = new ArrayList<>();
    public List<Question> getQuestions(final AnswerListAsyncResponse callBack)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            Question question = new Question(response.getJSONArray(i).getString(0), response.getJSONArray(i).getBoolean(1));
                            questionArrayList.add(question);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != callBack){
                        callBack.processFinished(questionArrayList);
                    }
                }, error -> {

        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }

}
