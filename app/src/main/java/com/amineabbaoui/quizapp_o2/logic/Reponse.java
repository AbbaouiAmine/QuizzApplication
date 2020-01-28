package com.amineabbaoui.quizapp_o2.logic;

public class Reponse {
    private String text;
    private boolean type;

    public Reponse(String text,boolean type) {
        this.text = text;
        this.type=type;
    }

    public String getText() {
        return text;
    }

    public boolean isType() {
        return type;
    }
}