package com.example.generatoreparole.model;

import java.util.HashMap;
import java.util.Map;
public class Parola {

    private String word;
    private String category;
    private int length;
    private boolean isSingolare;

    public Parola() {
        // Vuoto costruttore richiesto per Firestore
    }

    public Parola(String word, String category, int length, boolean isSingolare) {
        this.word = word;
        this.category = category;
        this.length = length;
        this.isSingolare = isSingolare;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isSingolare() {
        return isSingolare;
    }

    public void setSingolare(boolean singolare) {
        isSingolare = singolare;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("word", word);
        map.put("category", category);
        map.put("length", length);
        map.put("isSingolare", isSingolare);
        return map;
    }
}

