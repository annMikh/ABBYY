package com.example.annamihaleva.abbyy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ListQuestions extends AppCompatActivity {

    private RecyclerView listOfQuestions;
    private AdapterStackOverFlow adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);

        listOfQuestions = findViewById(R.id.list_questions);

        listOfQuestions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterStackOverFlow(this);
        listOfQuestions.setAdapter(adapter);
    }




}
