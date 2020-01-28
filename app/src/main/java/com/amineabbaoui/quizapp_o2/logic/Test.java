package com.amineabbaoui.quizapp_o2.logic;

import android.content.Intent;
import android.widget.Toast;

import com.amineabbaoui.quizapp_o2.MainActivity;
import com.amineabbaoui.quizapp_o2.QuizActivity;
import com.amineabbaoui.quizapp_o2.R;
import com.amineabbaoui.quizapp_o2.Rest.API;
import com.amineabbaoui.quizapp_o2.Rest.Question_;
import com.amineabbaoui.quizapp_o2.Rest.Utilisateur;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test {
    private List<Question>  questions;
    private int cpt;
    private Question q;
    private int score;
    private API jsonPlaceHolderApi;

    private void connectRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(API.adresse)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(API.class);
    }
    public Test() {
        this.questions = new ArrayList<>();
        this.score=0;
        connectRetrofit();


        Question q1=new Question(R.drawable.q1);
        Reponse q1_r1=new Reponse("1 - مركز الشرطة.",false);
        Reponse q1_r2=new Reponse("2 - موقف السيارات.",true);
        q1.addReponse(q1_r1);
        q1.addReponse(q1_r2);


        Question q2=new Question(R.drawable.q2);
        Reponse q2_r1=new Reponse("1 - نعم.",false);
        Reponse q2_r2=new Reponse("2 - لا.",true);
        q2.addReponse(q2_r1);
        q2.addReponse(q2_r2);

        Question q3=new Question(R.drawable.q3);
        Reponse q3_r1=new Reponse("1 - نسمح لي جاين من ليمن و ندوز.",true);
        Reponse q3_r2=new Reponse("2 - ندوز.",false);
        q3.addReponse(q3_r1);
        q3.addReponse(q3_r2);

        Question q4=new Question(R.drawable.q4);
        Reponse q4_r1=new Reponse("1 - يمكن ليا نزيد فسرعة ديالي.",false);
        Reponse q4_r2=new Reponse("2 - نشد ليمن مزيان.",true);
        q4.addReponse(q4_r1);
        q4.addReponse(q4_r2);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        cpt=0;
        getQuestions();
    }


    private void getQuestions() {
        Call<List<Question_>> call = jsonPlaceHolderApi.getQuestions_();

     call.enqueue(new Callback<List<Question_>>() {
         @Override
         public void onResponse(Call<List<Question_>> call, Response<List<Question_>> response) {
             if (!response.isSuccessful()) {
                 //textViewResult.setText("Code: " + response.code());
                 return;
             }

             List<Question_> question_s = response.body();

             int i=0;
             for (Question_ q : question_s) {
                 questions.get(i).setImageUrl(q.getImageUrl());
                i++;
             }
         }

         @Override
         public void onFailure(Call<List<Question_>> call, Throwable t) {

         }
     });
        }



            public Question next()
    {
        if(cpt<questions.size())
        {
            q=questions.get(cpt);
            cpt++;
            return q;
        }

        return null;
    }

    public void plusScore()
    {
        score++;
    }

    public int getScore() {
        return score;
    }
    public int getNumberOfQuestions()
    {
        return questions.size();
    }
}

