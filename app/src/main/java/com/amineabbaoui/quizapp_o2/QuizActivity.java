package com.amineabbaoui.quizapp_o2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amineabbaoui.quizapp_o2.logic.Robot;

import java.io.InputStream;

public class QuizActivity extends AppCompatActivity {

    Button valider;
    ImageView imageViewQuestion;
    RadioGroup radioGroup;
    Robot robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        onLoad();
        btn_valider_OnClick();



    }
    public void onLoad()
    {
        imageViewQuestion=(ImageView)findViewById(R.id.iv_question);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup_quiz);
        robot=new Robot(imageViewQuestion,radioGroup,this);
        valider=(Button)findViewById(R.id.btn_validate);
    }


    public void rg_radiogroup_OnClick(){
        radioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void btn_valider_OnClick()
    {
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(valider.getText().equals("valider")) {
                    try {
                        robot.verify();
                    }catch (Exception ex)
                    {
                        Toast t=Toast.makeText(QuizActivity.this,"Cocher une reponse",Toast.LENGTH_LONG);
                        t.show();
                        return;
                    }


                }
                if(valider.getText().equals("commencer")) {
                    valider.setText("valider");
                    imageViewQuestion.getLayoutParams().height=300;

                }

                int res=robot.next();
                if(res==-1)
                {
                    Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
                    intent.putExtra("score",robot.getscore());
                    intent.putExtra("score_msg",robot.getScoreString());
                    intent.putExtra("score_prc",String.valueOf(robot.getScorePourc()));

                    startActivity(intent);
                }




            }
        });
    }
}

