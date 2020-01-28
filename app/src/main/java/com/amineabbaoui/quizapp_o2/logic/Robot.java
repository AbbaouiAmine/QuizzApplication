package com.amineabbaoui.quizapp_o2.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.core.view.ViewCompat;

import com.amineabbaoui.quizapp_o2.Rest.API;

import java.io.InputStream;
import java.util.List;

public class Robot {

    Context context;
    ImageView imageView;
    RadioGroup radioGroup;
    Test test;
    Question q;

    public Robot(ImageView iv, RadioGroup rg, Context context) {
        this.imageView = iv;
        this.radioGroup=rg;
        test=new Test();
        this.context=context;
    }

    public int next(){

        q = test.next();
        if(q!=null)
        {

            new DownloadImageTask(imageView)
                    .execute(API.adresse+q.getImageUrl());
            //imageView.setImageResource(q.getImage_id());
            List<Reponse> list_reponses=q.getReponses();
            radioGroup.removeAllViews();

            for (Reponse r:list_reponses ) {
                RadioButton rb=new RadioButton(context);
                rb.setLayoutParams (new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
                ViewCompat.setLayoutDirection(rb, ViewCompat.LAYOUT_DIRECTION_RTL);

                rb.setText(r.getText());
                radioGroup.addView(rb);
            }
            return 1;
        }
            return -1;

    }
    public void verify()
    {
        RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        List<Reponse> list_reponses=q.getReponses();

        for (Reponse r:list_reponses ) {
                if(r.getText().equals(checkedRadioButton.getText()))
                {
                    if(r.isType())
                    test.plusScore();
                }

        }
    }

    public String getscore() {
       double res= ((double)test.getScore()/(double)test.getNumberOfQuestions())*100;
       if(res>70)
           return "Fort";
       else
        if(res>50 && res <70)
            return "Moyen";

            return "Faible";


    }
    public String getScoreString()
    {
        return test.getScore()+"/"+test.getNumberOfQuestions();
    }
    public double getScorePourc()
    {
        return ((double)test.getScore()/(double)test.getNumberOfQuestions())*100;
    }


}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            //Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}