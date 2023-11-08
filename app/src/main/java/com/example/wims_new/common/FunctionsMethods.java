package com.example.wims_new.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.example.wims_new.R;


public class FunctionsMethods {
    public boolean viewActivityFromNavivationBottom(MenuItem item,int nav_id,Context context,Activity activity,Class activityClass,boolean is_from_left){
        if(nav_id!=item.getItemId()) {
            goToActivity(context, activityClass, activity,is_from_left);
        }
            return true;
    }

    public boolean viewActivity(Context context,Activity activity,Class activityClass,boolean is_from_left){
            goToActivity(context, activityClass, activity,is_from_left);

        return true;
    }


    public void goToActivity(Context context, Class activityClass, Activity activity,boolean is_from_left){
        context.startActivity(new Intent(context,activityClass));
        //activity.finish();
        if(is_from_left){
            activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }else{
            activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }

    }

    public int[] fragmentTransition(boolean is_from_left){

        int[] ar=new int[4];
            ar[0]=is_from_left?R.anim.slide_in_right:R.anim.slide_in_left;
            ar[1]=is_from_left?R.anim.slide_out_left:R.anim.slide_out_right;
            ar[2]=is_from_left?R.anim.slide_in_left:R.anim.slide_in_right;
            ar[3]=is_from_left?R.anim.slide_out_right:R.anim.slide_out_left;

            return ar;
       
    }
}
