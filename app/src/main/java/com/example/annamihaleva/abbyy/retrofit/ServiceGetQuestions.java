package com.example.annamihaleva.abbyy.retrofit;

import com.example.annamihaleva.abbyy.objects.Questions;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ServiceGetQuestions {

    @GET("/2.2/questions?%20todate=1555200000&order=desc&max=1555804800&sort=activity&tagged=android&site=stackoverflow")
    Call<Questions> GetQuestions();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
