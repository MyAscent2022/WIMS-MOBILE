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
import com.example.wims_new.model.HawbModel;
import com.example.wims_new.model.MawbModel;

import java.util.List;

public class HawbListAdapter extends ArrayAdapter<HawbModel> {
    private Context mContext;
    int mResource;

    public HawbListAdapter (Context context, int resource, List<HawbModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            String hawb_no = getItem(position).getHawbNumber();
            int pieces = getItem(position).getNumberOfPackages();
            float weight = getItem(position).getGrossMass();

            System.out.println("PIECES>>>>>>>>>>>>>>>>> " + pieces);
            System.out.println("WEIGHT>>>>>>>>>>>>>>>>> " + weight);

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView hawb_no_txt = convertView.findViewById(R.id.hawb);
            TextView pieces_txt = convertView.findViewById(R.id.no_of_pieces);
            TextView weight_txt = convertView.findViewById(R.id.weight);

            hawb_no_txt.setText(hawb_no);
            pieces_txt.setText(pieces+"");
            weight_txt.setText((int) weight+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
