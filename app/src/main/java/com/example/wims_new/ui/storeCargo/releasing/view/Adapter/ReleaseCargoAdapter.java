package com.example.wims_new.ui.storeCargo.releasing.view.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.wims_new.R;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.databinding.ActivityRackLocationBinding;
import com.example.wims_new.ui.storeCargo.releasing.view.Model.ReleaseCargoModel;
import com.example.wims_new.ui.storeCargo.releasing.view.RackLocation;
import com.example.wims_new.ui.storeCargo.releasing.view.viewModel.RackLocationViewModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageModel;
import com.example.wims_new.utils.FunctionInterface;

import java.util.List;

public class ReleaseCargoAdapter extends ArrayAdapter<ReleaseCargoModel> {
    private Context mContext;
    int mResource;
    RackLocationViewModel viewModel;
    RackLocation activity;
    ActivityRackLocationBinding binding;
    String hawb_no = "";
    String mawb_no = "";
    public ReleaseCargoAdapter(Context context, int resource, List<ReleaseCargoModel> objects, RackLocationViewModel viewModel, RackLocation activity, ActivityRackLocationBinding binding) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.viewModel = viewModel;
        this.activity = activity;
        this.binding = binding;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            String rack_layer_name = getItem(position).getRackName() +" - "+ getItem(position).getLayerName();
            mawb_no = getItem(position).getMawbNumber();

            if(getItem(position).getHawbNumber() != null){
                hawb_no = getItem(position).getHawbNumber();
            }else{
                hawb_no = "";
            }

            String location = getItem(position).getLocation();
            int no_of_pcs = getItem(position).getActualPcs();
            String releasing_status = getItem(position).getReleaseStatus();
            String last_updated = getItem(position).getPaidDt();


            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView rack_layer_name_txt = convertView.findViewById(R.id.rack_layer_name);
            TextView mawb_no_txt = convertView.findViewById(R.id.mawb_no);
            TextView hawb_no_txt = convertView.findViewById(R.id.hawb_no);
            TextView location_no_txt = convertView.findViewById(R.id.location_no);
            TextView no_of_pcs_txt = convertView.findViewById(R.id.no_of_pieces);
            TextView releasing_status_txt = convertView.findViewById(R.id.releasing_status);
            TextView last_updated_txt = convertView.findViewById(R.id.last_updated);

            LinearLayout btn_release = convertView.findViewById(R.id.btn_release);

//            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            rack_layer_name_txt.setText(rack_layer_name);
            mawb_no_txt.setText(mawb_no);
             hawb_no_txt.setText(hawb_no);
            location_no_txt.setText(location);
            no_of_pcs_txt.setText(String.valueOf(no_of_pcs));
            releasing_status_txt.setText(releasing_status);
            last_updated_txt.setText(last_updated);

            btn_release.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertsAndLoaders loaders = new AlertsAndLoaders();
                    loaders.showAlert(4,"Are you sure?","You want to tag this cargo as RELEASED?",mContext, updateStatus);

                }
            });



         } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
    FunctionInterface.Function updateStatus = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
            viewModel.updateStoragerStatus(mContext,binding,activity,hawb_no, mawb_no);
        }
    };

}
