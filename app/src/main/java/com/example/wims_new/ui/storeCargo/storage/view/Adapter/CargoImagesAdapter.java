package com.example.wims_new.ui.storeCargo.storage.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wims_new.R;
import com.example.wims_new.model.CargoImagesModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageModel;

import java.util.List;

public class CargoImagesAdapter extends ArrayAdapter<CargoImagesModel> {

    private Context mContext;
    int mResource;

    public CargoImagesAdapter(Context context, int resource, List<CargoImagesModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            String cargo_condition = getItem(position).getCargoCondition();
            String remarks = getItem(position).getRemarks();
            String img = getItem(position).getFilePath().replace("\\", "/");

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView cargo_condition_txt = convertView.findViewById(R.id.cargo_condition);
            TextView remarks_txt = convertView.findViewById(R.id.remarks);
            ImageView img_view = convertView.findViewById(R.id.img);

//            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            cargo_condition_txt.setText(cargo_condition);
            remarks_txt.setText(remarks);
            show_image(img, img_view);
//            img_view.setImageURI(img);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public void show_image(String filename, ImageView img){


        String base_url = "http://192.168.20.179:33913/wims_api/";
        //String base_url=BuildConfig.DEVICE_BASE_URL;
        String link = base_url + "view_checklist_image/?file_path=" + filename;
        Glide.with(mContext).load(link).into(img);
    }
}
