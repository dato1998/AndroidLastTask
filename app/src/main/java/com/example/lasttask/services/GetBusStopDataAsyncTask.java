package com.example.lasttask.services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetBusStopDataAsyncTask  extends AsyncTask<String, Void, RouteStops[]> {
    private Callback callback;

    @Override
    protected RouteStops[] doInBackground(String... strings) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            String jsonArray = new Gson().fromJson(jsonData,JsonObject.class).getAsJsonObject().getAsJsonArray("RouteStops").toString();
            return new Gson().fromJson(jsonArray, RouteStops[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(RouteStops[] routeStops) {
        Log.d("onPostExecute", Thread.currentThread().getName());
        if (callback != null) {
            callback.onDataReceived(routeStops);
        }
    }

    public interface Callback {

        void onDataReceived(RouteStops[] routeStops);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
