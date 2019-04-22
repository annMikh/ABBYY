package com.example.annamihaleva.abbyy;


import java.util.ArrayList;

public class Item {

    public String getTitle() {
        return title;
    }

    public String getLink() {

        return link;
    }

    public Integer getQuestion_id() {

        return question_id;
    }

    public Long getLast_activity_date() {

        return last_activity_date;
    }

    public ArrayList<String> getTags() {

        return tags;
    }

    private ArrayList<String> tags;
    private Long last_activity_date;
    private Integer question_id;
    private String link;
    private String title;
}
