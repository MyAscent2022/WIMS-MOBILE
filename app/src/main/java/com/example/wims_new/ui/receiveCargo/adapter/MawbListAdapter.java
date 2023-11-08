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
import com.example.wims_new.model.MawbModel;

import java.util.List;

public class MawbListAdapter extends ArrayAdapter<MawbModel> {
    private Context mContext;
    int mResource;

    public MawbListAdapter(Context context, int resource, List<MawbModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            String mawb_no = getItem(position).getMawbNumber();

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView mawb_no_txt = convertView.findViewById(R.id.mawb_no);

            mawb_no_txt.setText(mawb_no);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
