package com.example.annamihaleva.abbyy;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ServiceGetQuestions {

    @GET("/2.2/questions?%100order=desc&sort=activity&tagged=android&site=stackoverflow")
    Call<Questions> GetQuesions();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
