package com.example.lasttask.services;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.lasttask.services.XmlHelper.getTextFromElement;
import static com.example.lasttask.services.XmlHelper.getXmlDoc;

public class GetBusRouteDataAsyncTask extends AsyncTask<Void, Void, List<BusRoute>> {

    private Callback callback;

    @Override
    protected List<BusRoute> doInBackground(Void... voids) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://transfer.ttc.com.ge:8080/otp/routers/ttc/routes")
                    .build();
            Response response = client.newCall(request).execute();
            String xmlData = response.body().string();
            return getBusRoutesFromXml(xmlData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<BusRoute> getBusRoutesFromXml(String xmlData) {
        Document doc = getXmlDoc(xmlData);
        NodeList nodes = doc.getElementsByTagName("Route");
        List<BusRoute> busRoutes = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            if ("bus".equals(getTextFromElement(element, "Type"))) {
                BusRoute busRoute = new BusRoute();
                busRoute.setName(getTextFromElement(element, "LongName"));
                busRoute.setRouteNumber(Integer.valueOf(getTextFromElement(element, "RouteNumber")));
                busRoute.setStopA(getTextFromElement(element, "StopA"));
                busRoute.setStopB(getTextFromElement(element, "StopB"));
                busRoutes.add(busRoute);
            }

        }
        return busRoutes;
    }

    @Override
    protected void onPostExecute(List<BusRoute> busRoutes) {
        Log.d("onPostExecute", Thread.currentThread().getName());
        if (callback != null) {
            callback.onDataReceived(busRoutes);
        }
    }

    public interface Callback {

        void onDataReceived(List<BusRoute> busRoutes);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
