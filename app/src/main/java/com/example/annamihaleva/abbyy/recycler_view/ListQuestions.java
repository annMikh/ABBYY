package com.example.annamihaleva.abbyy.recycler_view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.annamihaleva.abbyy.R;

public class ListQuestions extends AppCompatActivity {

    private RecyclerView listOfQuestions;
    private AdapterStackOverFlow adapter;
    private ProgressBar loading;
    private SwipeRefreshLayout swipe;
    private final String TAG_ADAPTER = "Adapter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);

        listOfQuestions = findViewById(R.id.list_questions);
        loading = findViewById(R.id.loading);
        swipe = findViewById(R.id.refresh_content);

        listOfQuestions.setLayoutManager(new LinearLayoutManager(this));

        if (checkConnection() && savedInstanceState == null) {
            loading.setVisibility(View.VISIBLE);
            adapter = new AdapterStackOverFlow(this, loading, true);
        } else if (savedInstanceState == null)
            adapter = new AdapterStackOverFlow(this, loading, false);

        listOfQuestions.setAdapter(adapter);
        swipe.setOnRefreshListener(this::refresh);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TAG_ADAPTER, adapter.getItems());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        adapter = new AdapterStackOverFlow(this, loading, false);
        adapter.setItems(savedInstanceState.getParcelableArrayList(TAG_ADAPTER));
        listOfQuestions.setAdapter(adapter);
    }

    public boolean checkConnection(){

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork == null || !activeNetwork.isConnected())
            Toast.makeText(getApplicationContext(), R.string.no_connection, Toast.LENGTH_SHORT).show();

        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void refresh(){
        new Handler().postDelayed(() -> {

            if (checkConnection())
                adapter.getList();

            swipe.setRefreshing(false);
        }, 1000);
    }
}
