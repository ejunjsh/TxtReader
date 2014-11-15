package com.sky.txtReader.widget;


import android.content.Context;
import android.os.AsyncTask;

import com.sky.txtReader.activity.BaseActivity;
import com.sky.txtReader.activity.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.CharBuffer;

/**
 * Created by shaojunjie on 2014/10/17.
 */
public class AsyncTextLoadTask extends AsyncTask<Object, String,String> {
    private BaseActivity context;
    private MainActivity activity;
    private BufferedReader br;

    public AsyncTextLoadTask(BaseActivity context, BufferedReader br) {
        this.context = context;
        this.br = br;
        activity = (MainActivity)context;
    }

    @Override
    protected String doInBackground(Object... params) {

        StringBuilder paragraph=new StringBuilder();

        try {

            String line = "";

            int index = 0;
            while(index < 50 && (line=br.readLine())!=null&&!line.equals("")){
                paragraph.append(line + "\n");
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return paragraph.toString();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        activity.editContent.append(result);
        activity.isLoading = false;
    }

}