package com.amineabbaoui.quizapp_o2.logic;

import com.amineabbaoui.quizapp_o2.R;

import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

public class Question{

    private int id;
    private String imageUrl;
    private int image_id= R.drawable.q1;
    private List<Reponse> reponses;

    public Question(int image_id) {
        this.image_id = image_id;
        reponses=new ArrayList<>();
    }

    public List<Reponse> getReponses() {
        return reponses;
    }

    public int getImage_id(){
        return image_id;
    }
    public void addReponse(Reponse r)
    {
        this.reponses.add(r);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
