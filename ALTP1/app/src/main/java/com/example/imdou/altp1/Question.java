package com.example.imdou.altp1;

import java.io.Serializable;

/**
 * Created by imdou on 6/7/2017.
 */

public class Question implements Serializable {
    public Question(){

    }
    String q_title;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    int correct_answer;
    int level;
}
