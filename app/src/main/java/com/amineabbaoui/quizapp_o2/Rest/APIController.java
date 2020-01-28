package com.amineabbaoui.quizapp_o2.Rest;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIController {

    Retrofit retrofit;
    API jsonPlaceHolderApi;
    List<Post> posts;

    public APIController(String baseUrl) {
        this.retrofit= new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.jsonPlaceHolderApi = retrofit.create(API.class);

    }

    public List<Post> getPosts()
    {
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Log.v("Log_Connexion", "onResponse");
                    return;
                }
                //Execution OK !!!
                posts = response.body();
            }

            //Erreur de l'execution
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.v("Log_Connexion", "onFailure");
                return;
            }
        });

        return posts;
    }


}
