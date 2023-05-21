package com.example.mainproject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.roger.catloadinglibrary.CatLoadingView;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    AppCompatActivity contextParent;
    CatLoadingView mView;

    public MyAsyncTask(AppCompatActivity contextParent) {
        this.contextParent = contextParent;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mView =new CatLoadingView();
        mView.show(contextParent.getSupportFragmentManager(), "");

    }
    @Override
    protected void onPostExecute(Void aVoid) {

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                mView.dismiss();
            }
        }, 3000);
    }

}
