package com.example.lasttask;

import android.content.Context;
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

import com.example.lasttask.services.BusStopArrivalTimes;
import com.example.lasttask.services.GetBusStopArrivalTimesAsyncTask;

import java.util.ArrayList;
import java.util.List;

import static com.example.lasttask.BusStopActivity.BUS_STOP_ID;
import static com.example.lasttask.BusStopActivity.BUS_STOP_NAME;

public class BusStopInformationActivity extends AppCompatActivity {
    private ListView mRouteStopArrivalTimes;
    private BusStopInformationArrayAdapter busStopInformationArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_information);
        TextView mBusStopInfoName = findViewById(R.id.busStopInfoName);
        mRouteStopArrivalTimes = findViewById(R.id.routeStopArrivalTimes);
        busStopInformationArrayAdapter = new BusStopInformationArrayAdapter(this, 0, new ArrayList<BusStopArrivalTimes>());
        String busStopName = getIntent().getStringExtra(BUS_STOP_NAME);
        mBusStopInfoName.setText("Bus Stop : " + busStopName);
        getBusStopInformation();
    }

    private void getBusStopInformation() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        GetBusStopArrivalTimesAsyncTask getBusStopArrivalTimesAsyncTask = new GetBusStopArrivalTimesAsyncTask();
        GetBusStopArrivalTimesAsyncTask.Callback callback = new GetBusStopArrivalTimesAsyncTask.Callback() {
            @Override
            public void onDataReceived(BusStopArrivalTimes[] busStopArrivalTimes) {
                mRouteStopArrivalTimes.setAdapter(busStopInformationArrayAdapter);
                busStopInformationArrayAdapter.addAll(busStopArrivalTimes);
                findViewById(R.id.progress).setVisibility(View.GONE);
            }
        };
        getBusStopArrivalTimesAsyncTask.setCallback(callback);
        getBusStopArrivalTimesAsyncTask.execute("http://transfer.ttc.com.ge:8080/otp/routers/ttc/stopArrivalTimes?stopId=" + getIntent().getIntExtra(BUS_STOP_ID, 0));
    }

    class BusStopInformationArrayAdapter extends ArrayAdapter<BusStopArrivalTimes> {

        private Context context;

        public BusStopInformationArrayAdapter(Context context, int resource, List<BusStopArrivalTimes> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_bus_stop_information, parent, false);
            LinearLayout routeStopArrivalTimes = view.findViewById(R.id.routeStopArrivalTimes);
            final BusStopArrivalTimes busStopArrivalTime = getItem(position);
            TextView busRouteNumberView = routeStopArrivalTimes.findViewById(R.id.arrivalTime);
            busRouteNumberView.setText(Integer.toString(busStopArrivalTime.getRouteNumber()));
            TextView busRouteNameView = routeStopArrivalTimes.findViewById(R.id.destination);
            busRouteNameView.setText(busStopArrivalTime.getDestination());
            TextView busRouteStopAView = routeStopArrivalTimes.findViewById(R.id.routeNumber);
            busRouteStopAView.setText(Integer.toString(busStopArrivalTime.getArrivalTime()));
            return view;
        }
    }
}
