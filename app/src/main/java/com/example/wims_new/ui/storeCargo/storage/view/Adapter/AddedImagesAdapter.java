package com.example.wims_new.ui.storeCargo.storage.view.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wims_new.R;
import com.example.wims_new.model.CargoImagesModel;

import java.util.List;

public class AddedImagesAdapter extends ArrayAdapter<CargoImagesModel> {

    private Context mContext;
    int mResource;

    public AddedImagesAdapter(Context context, int resource, List<CargoImagesModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            String cargo_condition = getItem(position).getCargoCondition();
            String remarks = getItem(position).getRemarks();
            Uri img = getItem(position).getImageUri();

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView cargo_condition_txt = convertView.findViewById(R.id.cargo_condition);
            TextView remarks_txt = convertView.findViewById(R.id.remarks);
            ImageView img_view = convertView.findViewById(R.id.img);

//            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            cargo_condition_txt.setText(cargo_condition);
            remarks_txt.setText(remarks);
//            show_image(img, img_view);
            img_view.setImageURI(img);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }


}
