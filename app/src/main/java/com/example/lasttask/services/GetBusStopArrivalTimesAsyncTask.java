package com.example.lasttask.services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetBusStopArrivalTimesAsyncTask extends AsyncTask<String, Void, BusStopArrivalTimes[]> {
    private Callback callback;

    @Override
    protected BusStopArrivalTimes[] doInBackground(String... strings) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            String jsonArray = new Gson().fromJson(jsonData, JsonObject.class).getAsJsonObject().getAsJsonArray("ArrivalTime").toString();
            return new Gson().fromJson(jsonArray, BusStopArrivalTimes[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(BusStopArrivalTimes[] busStopArrivalTimes) {
        Log.d("onPostExecute", Thread.currentThread().getName());
        if (callback != null) {
            callback.onDataReceived(busStopArrivalTimes);
        }
    }

    public interface Callback {

        void onDataReceived(BusStopArrivalTimes[] busStopArrivalTimes);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
