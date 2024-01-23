package com.example.wims_new.ui.receiveCargo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.wims_new.R;
import com.example.wims_new.model.CargoImagesModel;
import com.example.wims_new.model.UldImages;

import java.util.List;

public class UldImagesAdapter extends ArrayAdapter<UldImages> {

    private Context mContext;
    int mResource;

    public UldImagesAdapter(Context context, int resource, List<UldImages> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            String uld_condition = getItem(position).getUldCondition();
            String remarks = getItem(position).getRemarks();
            String img = getItem(position).getFilePath().replace("\\", "/");

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView uld_condition_txt = convertView.findViewById(R.id.cargo_condition);
            TextView remarks_txt = convertView.findViewById(R.id.remarks);
            ImageView img_view = convertView.findViewById(R.id.img);

            uld_condition_txt.setText(uld_condition);
            remarks_txt.setText(remarks);
            show_image(img, img_view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public void show_image(String filename, ImageView img){


//        String base_url = "http://192.168.20.179:33913/wims_api/";
        String base_url = "http://112.199.119.250:33911/wims_api/";
        //String base_url=BuildConfig.DEVICE_BASE_URL;
        String link = base_url + "view_checklist_image/?file_path=" + filename;
        Glide.with(mContext).load(link).into(img);
    }
}
