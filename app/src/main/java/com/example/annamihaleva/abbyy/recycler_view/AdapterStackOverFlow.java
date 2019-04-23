package com.example.annamihaleva.abbyy.recycler_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.annamihaleva.abbyy.R;
import com.example.annamihaleva.abbyy.retrofit.ServiceGetQuestions;
import com.example.annamihaleva.abbyy.objects.Item;
import com.example.annamihaleva.abbyy.objects.Questions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

public class AdapterStackOverFlow extends RecyclerView.Adapter<AdapterStackOverFlow.QuestionHolder> {

    private Context context;
    private static ArrayList<Item> questions = new ArrayList<>();
    private ProgressBar loading;

    AdapterStackOverFlow(Context context, ProgressBar loading, boolean isNeeded){
        this.context = context;
        this.loading = loading;

        if (isNeeded)
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
        questionHolder.link.setText(questions.get(i).getLink());
    }

    void getList(){

        ServiceGetQuestions getQuestions = ServiceGetQuestions.retrofit.create(ServiceGetQuestions.class);
        final Call<Questions> call = getQuestions.GetQuestions();
        new GetQuestions().execute(call);
    }

    @Override
    public int getItemCount() { return questions == null ? 0 : questions.size(); }


    public void setItems(ArrayList<Item> q) {
        questions = q;
    }

    public ArrayList<Item> getItems() {
        return questions;
    }

    static class QuestionHolder extends RecyclerView.ViewHolder {

        TextView textQ;
        TextView tags;
        TextView link;

        QuestionHolder(@NonNull View itemView) {
            super(itemView);
            textQ = itemView.findViewById(R.id.text_question);
            tags = itemView.findViewById(R.id.tags);
            link = itemView.findViewById(R.id.link);
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
                AdapterStackOverFlow.questions = questions.getItems();
                AdapterStackOverFlow.this.notifyDataSetChanged();
            }

            loading.setVisibility(View.GONE);
        }
    }
}
