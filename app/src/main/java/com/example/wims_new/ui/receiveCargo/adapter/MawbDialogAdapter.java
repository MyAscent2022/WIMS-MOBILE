package com.example.wims_new.ui.receiveCargo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.wims_new.R;
import com.example.wims_new.model.MawbModel;

import java.util.ArrayList;
import java.util.List;

public class MawbDialogAdapter extends ArrayAdapter<MawbModel>{
    private Context mContext;
    int mResource;
    ArrayList<MawbModel> mawbList = new ArrayList<>();
    List<MawbModel> model;

    public MawbDialogAdapter(Context context, int resource, List<MawbModel> objects) {
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
            CheckBox checkBox_ = (CheckBox) convertView.findViewById(R.id.checkbox);

            checkBox_.setChecked(false);
            mawb_no_txt.setText(mawb_no);

            checkBox_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    getItem(position).setSelected(checkBox_.isChecked());
                    /*MawbModel m = getItem(position);
                    mawbList.add(m);
                    if (!mawbList.contains(position)) {
                        mawbList.add(position);
                        checkBox_.setChecked(true);
                    }else {
                        mawbList.clear();
                        checkBox_.setChecked(false);
                    }
                    model.set(position, m);*/
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

}
