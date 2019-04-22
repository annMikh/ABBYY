package com.example.annamihaleva.abbyy;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

public class AdapterStackOverFlow extends RecyclerView.Adapter<AdapterStackOverFlow.QuestionHolder> {

    private Context context;
    private ArrayList<Item> questions = new ArrayList<>();;

    AdapterStackOverFlow(Context context){
        this.context = context;
        getList();
    }


    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_question, viewGroup, false);
        return new QuestionHolder(view);
    }


    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull QuestionHolder questionHolder, int i) {

        questionHolder.textQ.setText(questions.get(i).getTitle());
        questionHolder.tags.setText(String.join(" ",
                questions.get(i).getTags().stream().map(it -> "#" + it).collect(Collectors.toList())));
    }

    private void getList(){
        ServiceGetQuestions getQuestions = ServiceGetQuestions.retrofit.create(ServiceGetQuestions.class);
        final Call<Questions> call = getQuestions.GetQuesions();
        new GetQuestions().execute(call);
    }

    @Override
    public int getItemCount() { return questions.size(); }

    static class QuestionHolder extends RecyclerView.ViewHolder {

        TextView textQ;
        TextView tags;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            textQ = itemView.findViewById(R.id.text_question);
            tags = itemView.findViewById(R.id.tags);
        }
    }


    private class GetQuestions extends AsyncTask<Call, Void, Questions>{

        @Override
        protected Questions doInBackground(Call... calls) {

            Call<Questions> call = calls[0];
            Response<Questions> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
                return response != null && response.isSuccessful() ? response.body() : null;
        }


        @Override
        protected void onPostExecute(Questions questions) {
            super.onPostExecute(questions);
            if (questions != null){
                AdapterStackOverFlow.this.questions = questions.getItems();
                AdapterStackOverFlow.this.notifyDataSetChanged();
            }
        }
    }
}
