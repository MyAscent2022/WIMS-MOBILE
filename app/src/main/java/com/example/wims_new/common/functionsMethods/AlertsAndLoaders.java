package com.example.wims_new.common.functionsMethods;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wims_new.R;
import com.example.wims_new.utils.FunctionInterface;

public class AlertsAndLoaders {

    private AlertDialog dialog=null;
    private AlertDialog.Builder builder=null;
    private View view=null;
    private TextView content_txt;
    private Button btn_ok,btn_cancel;

    /**
     *Show alerts and loader
     */
    @SuppressLint("InflateParams")
    public AlertDialog showAlert(int id, String message, Context context, Activity activity, FunctionInterface.Function myFunction) {
        builder = new AlertDialog.Builder(context);
        switch (id) {


            /**
             *Show success dialog
             */
            case 0:
                view= activity.getLayoutInflater().inflate(R.layout.dialog_success, null);
                content_txt=view.findViewById(R.id.content_txt);
                btn_ok=view.findViewById(R.id.btn_ok);
                content_txt.setText(message);
                btn_ok.setOnClickListener(view1 -> {
                    dialog.cancel();
                    executeFunction(myFunction);
                });
                break;


            /**
             *Show error dialog
             */
            case 1:
                view= activity.getLayoutInflater().inflate(R.layout.dialog_error, null);
                content_txt=view.findViewById(R.id.content_txt);
                btn_ok=view.findViewById(R.id.btn_ok);
                content_txt.setText(message);
                btn_ok.setOnClickListener(view12 -> {
                    dialog.cancel();
                    executeFunction(myFunction);
                });
                break;


            /**
             *Show loader dialog
             */
            case 2:
                view= activity.getLayoutInflater().inflate(R.layout.dialog_loader, null);
//                content_txt=view.findViewById(R.id.content_txt);
//                content_txt.setText(message);
                break;


            /**
             *Show confirmation dialog
             */
            case 3:
                view= activity.getLayoutInflater().inflate(R.layout.dialog_confirmation, null);
                content_txt=view.findViewById(R.id.content_txt);
                btn_ok=view.findViewById(R.id.btn_ok);
                btn_cancel=view.findViewById(R.id.btn_cancel);
                content_txt.setText(message);
                btn_ok.setOnClickListener(view13 -> {
                    dialog.cancel();
                    executeFunction(myFunction);
                });

                btn_cancel.setOnClickListener(view14 -> dialog.cancel());
                break;
        }

        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        return dialog;


    }



    public void executeFunction(FunctionInterface.Function function) {
        function.perform();
    }
}
