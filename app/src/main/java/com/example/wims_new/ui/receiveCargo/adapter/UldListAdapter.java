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
import com.example.wims_new.model.UldModel;

import org.w3c.dom.Text;

import java.util.List;

public class UldListAdapter extends ArrayAdapter<UldModel> {

    private Context mContext;
    int mResource;

    public UldListAdapter (Context context, int resource, List<UldModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            String uld_no = getItem(position).getUldNo();
            int uld_type = getItem(position).getUldType();
            int tc = getItem(position).getTotalCount();
            int sl = getItem(position).getShortLanded();
            int te = getItem(position).getTotalExpected();
//            String uld_type = getItem(position).getUldTypeModel().getType();

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView uld_no_txt = convertView.findViewById(R.id.uld_no);
            TextView uld_type_txt = convertView.findViewById(R.id.uld_type);
            TextView tc_txt = convertView.findViewById(R.id.tc);
            TextView sl_txt = convertView.findViewById(R.id.sl);
            TextView te_txt = convertView.findViewById(R.id.total);

            uld_no_txt.setText(uld_no);
            uld_type_txt.setText(uld_type);
            tc_txt.setText(tc);
            sl_txt.setText(sl);
            te_txt.setText(te);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
