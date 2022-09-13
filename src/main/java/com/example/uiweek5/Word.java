package com.example.uiweek5;

import java.io.Serializable;
import java.util.ArrayList;

import java.io.Serializable;
import java.util.ArrayList;

public class Word implements Serializable{
    public ArrayList<String> badWords;
    public ArrayList<String> goodWords;

    public Word(){
        badWords = new ArrayList<String>();
        goodWords = new ArrayList<String>();
        goodWords.add("happy");
        goodWords.add("enjoy");
        goodWords.add("life");
        badWords.add("fuck");
        badWords.add("olo");
    }
}

