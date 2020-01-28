package com.amineabbaoui.quizapp_o2.Rest;

public class Question_ {
    private int id;
    private String imageUrl;

    public Question_() {

    }

    public Question_(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
