package com.example.wims_new.ui.receiveCargo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wims_new.R;
import com.example.wims_new.model.UldModel;
import com.example.wims_new.model.UploadImageModel;

import java.util.List;

public class ImageListAdapter extends ArrayAdapter<Uri> {

    private Context mContext;
    int mResource;

    public ImageListAdapter (Context context, int resource, List<Uri> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {

            Uri image = getItem(position);

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            ImageView imageView = convertView.findViewById(R.id.image);

            imageView.setImageURI(image);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
