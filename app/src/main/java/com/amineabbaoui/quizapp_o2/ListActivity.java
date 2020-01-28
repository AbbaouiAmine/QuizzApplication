package com.amineabbaoui.quizapp_o2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.amineabbaoui.quizapp_o2.Rest.API;
import com.amineabbaoui.quizapp_o2.Rest.APIController;
import com.amineabbaoui.quizapp_o2.Rest.Comment;
import com.amineabbaoui.quizapp_o2.Rest.Post;
import com.amineabbaoui.quizapp_o2.Rest.Produit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity {

    private APIController apiController;
    private TextView textViewResult;
    private  API jsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        mapViews();
       // apiController=new APIController("http://localhost:8085/");

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.1.9:8085/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       jsonPlaceHolderApi = retrofit.create(API.class);

       deleteProduit();
       //createProduit();
       //getProduis();
       //getPosts();
        //getComments();

    }
    private void getPosts()
    {
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getProduis()
    {
        Call<List<Produit>> call = jsonPlaceHolderApi.getProduits();

        call.enqueue(new Callback<List<Produit>>() {
            @Override
            public void onResponse(Call<List<Produit>> call, Response<List<Produit>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Produit> produits = response.body();

                for (Produit p : produits) {
                    String content = "";
                    content += "ID: " + p.getId() + "\n";
                    content += "User ID: " + p.getDesignation() + "\n";
                    content += "Title: " + p.getPrice() + "\n";
                    content += "Text: " + p.getQuantite() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Produit>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        Post post = new Post();

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createProduit() {
        Produit produit = new Produit();
        produit.setId(65443);
        produit.setDesignation("Android 2");
        produit.setPrice(1002.4);
        produit.setQuantite(298);

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");

        Call<Produit> call = jsonPlaceHolderApi.createProduit(produit);

        call.enqueue(new Callback<Produit>() {
            @Override
            public void onResponse(Call<Produit> call, Response<Produit> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Produit postResponse = response.body();

                String content = "";
                content += response.code() + "\n";
                content +=  postResponse.getId() + "\n";
                content +=  postResponse.getDesignation() + "\n";
                content +=  postResponse.getQuantite() + "\n";
                content +=   postResponse.getPrice() + "\n\n";

                //textViewResult.setText(content);
                textViewResult.setText("Est ajouté");
            }

            @Override
            public void onFailure(Call<Produit> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void deleteProduit() {
        Call<Void> call = jsonPlaceHolderApi.deleteProduit(57);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        Post post = new Post();

        Call<Post> call = jsonPlaceHolderApi.patchPost(5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void mapViews()
    {
        this.textViewResult=findViewById(R.id.textResultList);
    }
}
