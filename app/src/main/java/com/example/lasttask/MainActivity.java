package com.example.lasttask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lasttask.services.BusRoute;
import com.example.lasttask.services.GetBusRouteDataAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String BUS_ROUTE_NUMBER = "busRouteNumber";

    private ListView mBusRoutes;
    private BusRouteArrayAdapter busRouteArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBusRoutes = findViewById(R.id.busRoutes);
        busRouteArrayAdapter = new BusRouteArrayAdapter(this, 0, new ArrayList<BusRoute>());
        getBusRoutes();
    }

    private void getBusRoutes() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        GetBusRouteDataAsyncTask getBusRouteDataAsyncTask = new GetBusRouteDataAsyncTask();
        GetBusRouteDataAsyncTask.Callback callback = new GetBusRouteDataAsyncTask.Callback() {
            @Override
            public void onDataReceived(List<BusRoute> busRoutes) {
                mBusRoutes.setAdapter(busRouteArrayAdapter);
                busRouteArrayAdapter.addAll(busRoutes);
                findViewById(R.id.progress).setVisibility(View.GONE);
            }
        };
        getBusRouteDataAsyncTask.setCallback(callback);
        getBusRouteDataAsyncTask.execute();
    }


    class BusRouteArrayAdapter extends ArrayAdapter<BusRoute> {

        private Context context;

        public BusRouteArrayAdapter(Context context, int resource, List<BusRoute> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_bus_route_item , parent, false);
            LinearLayout busRouteLayout = view.findViewById(R.id.busRoute);
            final BusRoute busRoute = getItem(position);
            busRouteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), BusStopActivity.class);
                    intent.putExtra(BUS_ROUTE_NUMBER, busRoute.getRouteNumber());
                    startActivity(intent);
                }
            });
            TextView busRouteNumberView = busRouteLayout.findViewById(R.id.busRouteNumber);
            busRouteNumberView.setText(Integer.toString(busRoute.getRouteNumber()));
            TextView busRouteNameView = busRouteLayout.findViewById(R.id.busRouteName);
            busRouteNameView.setText(busRoute.getName());
            TextView busRouteStopAView = busRouteLayout.findViewById(R.id.busRouteStopA);
            busRouteStopAView.setText(busRoute.getStopA());
            TextView busRouteStopBView = busRouteLayout.findViewById(R.id.busRouteStopB);
            busRouteStopBView.setText(busRoute.getStopB());
            return view;
        }
    }
}
