package com.amineabbaoui.quizapp_o2.logic;

import com.amineabbaoui.quizapp_o2.R;


import java.util.HashMap;
import java.util.Map;

public class RessourcesManager {
    public static Map<String,Integer> listQuestions=new HashMap<>();
    static  void chargeListRessources()
    {
        listQuestions.clear();
        listQuestions.put("q1",R.drawable.q1);
        listQuestions.put("q2",R.drawable.q2);
        listQuestions.put("q3",R.drawable.q3);
        listQuestions.put("q4",R.drawable.q4);
    }

    public static Map<String, Integer> getListQuestions() {
        chargeListRessources();
        return listQuestions;
    }
}
