package com.example.wims_new.ui.receiveCargo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wims_new.R;
import com.example.wims_new.model.FlightsModel;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

public class FlightListAdapter extends ArrayAdapter<FlightsModel> {
    private Context mContext;
    int mResource;

    public FlightListAdapter(Context context, int resource, List<FlightsModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            String flight_no = getItem(position).getFlightNumber();
            String travel_status = getItem(position).getStatus();
            String finish_flight = getItem(position).getTff();
            String tmr = getItem(position).getTbs();
            String tbs = getItem(position).getTbs();
            String tbf = getItem(position).getTff();

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView flight_no_txt = convertView.findViewById(R.id.flight_no);
            TextView travel_status_txt = convertView.findViewById(R.id.travel_status);
            TextView finish_flight_txt = convertView.findViewById(R.id.finish_flight);
            TextView tmr_txt = convertView.findViewById(R.id.tmr);
            TextView tbs_txt = convertView.findViewById(R.id.tbs);
            TextView tbf_txt = convertView.findViewById(R.id.tbf);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            flight_no_txt.setText(flight_no);
            travel_status_txt.setText(travel_status);
            finish_flight_txt.setText(finish_flight);
            tmr_txt.setText(tmr);
            tbs_txt.setText(tbs);
            tbf_txt.setText(tbf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
