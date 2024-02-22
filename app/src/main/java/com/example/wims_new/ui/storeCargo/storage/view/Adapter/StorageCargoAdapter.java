package com.example.wims_new.ui.storeCargo.storage.view.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wims_new.R;
import com.example.wims_new.model.FlightsModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageModel;
import com.google.android.gms.common.util.ScopeUtil;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.List;

public class StorageCargoAdapter extends ArrayAdapter<StorageModel> {

    private Context mContext;
    int mResource;

    public StorageCargoAdapter(Context context, int resource, List<StorageModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            String flight_no = getItem(position).getFlightNumber();
            String class1 = getItem(position).getClassDesc();
            String mawb_no = getItem(position).getMawbNumber();
            String hawb_no = getItem(position).getHawbNumber();
            int pcs = getItem(position).getActualPcs();

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView flight_no_txt = convertView.findViewById(R.id.flight_no);
            TextView class_txt = convertView.findViewById(R.id.class1);
            TextView mawb_no_txt = convertView.findViewById(R.id.mawb_no);
            TextView hawb_no_txt = convertView.findViewById(R.id.hawb_no);
            TextView pcs_txt = convertView.findViewById(R.id.pcs);

//            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");


            System.out.println("CARGO CLASS >>>>>>>>>>>>>>>>>>>> " + class1);
            flight_no_txt.setText(flight_no);
            class_txt.setText(class1);
            mawb_no_txt.setText(mawb_no);
            hawb_no_txt.setText(hawb_no);
            pcs_txt.setText(pcs+"");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }


}
