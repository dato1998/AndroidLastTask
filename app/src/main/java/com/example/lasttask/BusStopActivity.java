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

import com.example.lasttask.services.GetBusStopDataAsyncTask;
import com.example.lasttask.services.RouteStops;

import java.util.ArrayList;
import java.util.List;

public class BusStopActivity extends AppCompatActivity {
    private ListView mBusStops;
    private BusStopArrayAdapter bustStopArrayAdapter;
    private int busRouteNumber;
    public static final String BUS_STOP_ID = "busStopId";
    public static final String BUS_STOP_NAME = "busStopName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);
        mBusStops = findViewById(R.id.routeStops);
        bustStopArrayAdapter = new BusStopArrayAdapter(this, 0, new ArrayList<RouteStops>());
        busRouteNumber = getIntent().getIntExtra(MainActivity.BUS_ROUTE_NUMBER, 0);
        getBusStops();
    }

    private void getBusStops() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        GetBusStopDataAsyncTask getBusStopDataAsyncTask = new GetBusStopDataAsyncTask();
        GetBusStopDataAsyncTask.Callback callback = new GetBusStopDataAsyncTask.Callback() {
            @Override
            public void onDataReceived(RouteStops[] busStops) {
                mBusStops.setAdapter(bustStopArrayAdapter);
                bustStopArrayAdapter.addAll(busStops);
                findViewById(R.id.progress).setVisibility(View.GONE);
            }
        };
        getBusStopDataAsyncTask.setCallback(callback);
        getBusStopDataAsyncTask.execute("http://transfer.ttc.com.ge:8080/otp/routers/ttc/routeInfo?routeNumber=" + busRouteNumber + "&type=bus");
    }


    class BusStopArrayAdapter extends ArrayAdapter<RouteStops> {

        private Context context;

        public BusStopArrayAdapter(Context context, int resource, List<RouteStops> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_bus_stop_item, parent, false);
            LinearLayout busStopLayout = view.findViewById(R.id.routeStops);
            final RouteStops routeStops = getItem(position);
            busStopLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                    intent.putExtra(BUS_STOP_ID, routeStops.getStopId());
                    intent.putExtra(BUS_STOP_NAME, routeStops.getName());
                    startActivity(intent);
                }
            });
            TextView busStopNameView = busStopLayout.findViewById(R.id.busStopName);
            busStopNameView.setText(routeStops.getName());
            TextView busSequenceView = busStopLayout.findViewById(R.id.busSequence);
            busSequenceView.setText(Integer.toString(routeStops.getSequence()));
            TextView busForwardView = busStopLayout.findViewById(R.id.busForward);
            busForwardView.setText(Integer.toString(routeStops.getSequence()));
            return view;
        }
    }
}
