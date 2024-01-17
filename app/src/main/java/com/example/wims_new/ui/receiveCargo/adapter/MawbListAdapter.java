package com.example.wims_new.ui.receiveCargo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wims_new.R;
import com.example.wims_new.databinding.ActivityReceiveCargoBinding;
import com.example.wims_new.model.MawbModel;
import com.example.wims_new.ui.receiveCargo.view.ReceiveCargo;
import com.example.wims_new.ui.receiveCargo.viewModel.ReceiveCargoViewModel;

import java.util.ArrayList;
import java.util.List;

public class MawbListAdapter extends ArrayAdapter<MawbModel> {
    private Context mContext;
    int mResource;
    ActivityReceiveCargoBinding mBinding;

    ArrayList<MawbModel> mawbList = new ArrayList<>();
    ReceiveCargoViewModel viewModel;
    List<MawbModel> model;

    public MawbListAdapter(Context context, int resource, List<MawbModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewModel = new ReceiveCargoViewModel();
        try {
            String mawb_no = getItem(position).getMawbNumber();
            String hawbCount = String.valueOf(getItem(position).getHawbCount());

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView mawb_no_txt = convertView.findViewById(R.id.mawb_no);
            TextView hawb_count_txt = convertView.findViewById(R.id.hawb_count);

            mawb_no_txt.setText(mawb_no);
            hawb_count_txt.setText(hawbCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
