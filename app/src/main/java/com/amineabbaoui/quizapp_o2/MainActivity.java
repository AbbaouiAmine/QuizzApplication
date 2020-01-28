package com.amineabbaoui.quizapp_o2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amineabbaoui.quizapp_o2.Rest.API;
import com.amineabbaoui.quizapp_o2.Rest.Produit;
import com.amineabbaoui.quizapp_o2.Rest.Utilisateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    public static String NomUser;
    public static String EmailUser;

    private TextView tvRegister;
    private Button button_sign;
    private EditText et_login;
    private EditText et_password;
    private  API jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingViews();
        //et_login.setText("admin");
        //et_password.setText("admin");
        Log.v("Adresse",API.adresse);

        connectRetrofit();


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        button_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsers();
            }
        });
    }

    private void connectRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(API.adresse)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(API.class);
    }

    private void getUsers() {
        Call<List<Utilisateur>> call = jsonPlaceHolderApi.getUtilisateurs();

        call.enqueue(new Callback<List<Utilisateur>>() {
            @Override
            public void onResponse(Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Utilisateur> utilisateurs = response.body();

                boolean isCorrect = false;
                for (Utilisateur u : utilisateurs) {
                    //String content = "";
                    String login = u.getLogin();
                    String pass = u.getPass();
                    if (et_login.getText().toString().equals(login) && et_password.getText().toString().equals(pass)) {
                        MainActivity.NomUser=u.getName();
                        MainActivity.EmailUser=u.getEmail();
                        isCorrect = true;
                        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                        startActivity(intent);
                    }

                }
                if (!isCorrect) {
                    Toast t = Toast.makeText(MainActivity.this, "Verifier vos données", Toast.LENGTH_SHORT);
                    t.show();
                }

            }

            @Override
            public void onFailure(Call<List<Utilisateur>> call, Throwable t) {
                Toast t1 = Toast.makeText(MainActivity.this, "Connexion error", Toast.LENGTH_SHORT);
                t1.show();
            }
        });
    }
    public void bindingViews()
    {
        tvRegister=(TextView)findViewById(R.id.tv_register);
        button_sign=(Button)findViewById(R.id.btn_sign_in);
        et_login=(EditText)findViewById(R.id.et_login_sign);
        et_password=(EditText)findViewById(R.id.et_password_sign);
    }
}
