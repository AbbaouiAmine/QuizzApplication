package com.amineabbaoui.quizapp_o2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amineabbaoui.quizapp_o2.Rest.API;
import com.amineabbaoui.quizapp_o2.Rest.Post;
import com.amineabbaoui.quizapp_o2.Rest.Utilisateur;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends Activity {

    EditText loginText;
    EditText passText;
    EditText emailText;
    EditText nametext;
    Button button;
    private  API jsonPlaceHolderApi;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindingControls();
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(API.adresse)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(API.class);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login=loginText.getText().toString();
                String pass=passText.getText().toString();
                String name=nametext.getText().toString();
                String email=emailText.getText().toString();
                createUser(login,pass,name,email);
            }
        });
    }


    private void createUser(String login,String pass,String name,String email) {
        Utilisateur user = new Utilisateur();
        user.setLogin(login);
        user.setPass(pass);
        user.setName(name);
        user.setEmail(email);


        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");

        Call<Utilisateur> call = jsonPlaceHolderApi.createUser(user);
call.enqueue(new Callback<Utilisateur>() {
    @Override
    public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
        Toast t=Toast.makeText(RegisterActivity.this,"You are registred",Toast.LENGTH_LONG);
        t.show();
        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onFailure(Call<Utilisateur> call, Throwable t) {
        Toast t1=Toast.makeText(RegisterActivity.this,"Message : You are registred",Toast.LENGTH_LONG);
        t1.show();
        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }
});

        /*call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                //textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
            }
        });*/
    }

    private void  bindingControls()
    {
        button=(Button)findViewById(R.id.btn_register);
        this.loginText=findViewById(R.id.et_login_register);
        this.passText=findViewById(R.id.et_password_register);
        this.nametext=findViewById(R.id.et_name);
        this.emailText=findViewById(R.id.et_mail);
    }





}
