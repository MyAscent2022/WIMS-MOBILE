package com.example.wims_new.utils;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SharedPref {

    public static final String SECURED_PREFS_KEY = "com.ascentdev.wims.secured_prefs";
    public static final String PREFS_KEY = "com.ascentdev.wims.pref_key";
    public static final String DEVICE_FIREBASE_TOKEN  = "com.ascentdev.wims.firebase_token";
    public static final String USERNAME  = "com.ascentdev.wims.user_name";
    public static final String PASSWORD  = "com.ascentdev.wims.password";
    public static final String USER_ID = "com.ascentdev.wims.user_id";
    public static final String EMAIL  = "com.ascentdev.wims.email";
    public static final String ROLE_ID  = "com.ascentdev.wims.role_id";
    public static final String TOKEN  = "com.ascentdev.wims.token";
    public static final String FREIGHT_TYPE  = "com.ascentdev.wims.token"; //1-AF, 2-SF



    public void writePrefString(Context context, String key, String value) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(key, value);
        edit.apply();
    }
    public void secureWritePrefBoolean(Context context, String key, Boolean value) throws GeneralSecurityException, IOException {
        getEncryptedPrefs(context).edit().putBoolean(key, value).apply();
    }

    public String readPrefString(Context context, String key){
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }


    public int readPrefInt(Context context, int key){
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        return sharedPref.getInt(String.valueOf(key), -1);
    }

    public void removePrefs(Context context, String key) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor edit = sharedPref.edit();
        edit.remove(key).apply();
    }



    public void secureWritePrefString(Context context,String key , String value) throws GeneralSecurityException, IOException {
        getEncryptedPrefs(context).edit().putString(key, value).apply();
    }



    public String secureReadPrefString(Context context,String key) throws GeneralSecurityException, IOException {
        return getEncryptedPrefs(context).getString(key, null);
    }

    public void secureWritePrefInt(Context context,String key , int value) throws GeneralSecurityException, IOException {
        getEncryptedPrefs(context).edit().putInt(key, value).apply();
    }

    public int secureReadPrefInt(Context context,String key) throws GeneralSecurityException, IOException {
        return getEncryptedPrefs(context).getInt(key, -1);
    }


    public boolean secureReadPrefBoolean(Context context,String key) throws GeneralSecurityException, IOException {
        return getEncryptedPrefs(context).getBoolean(key, false);
    }

    public void secureRemovePrefs(Context context,String key) throws GeneralSecurityException, IOException {
        getEncryptedPrefs(context).edit().remove(key).apply();
    }


    public void  writePrefInt(Context context,String key, int value) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor edit = sharedPref.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public android.content.SharedPreferences getEncryptedPrefs(Context context) throws GeneralSecurityException, IOException {
        MasterKey masterKey=new MasterKey.Builder(context,MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
        return EncryptedSharedPreferences.create(context,SECURED_PREFS_KEY,masterKey,EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
    }

    Bitmap generateQRCode(String text) throws WriterException {
        int width = 500;
        int height = 500;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        MultiFormatWriter codeWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            int n = 0;
            while (n < width) {
                int x = n++;
                int n2 = 0;
                while (n2 < height) {
                    int y = 0;
                    bitmap.setPixel(x, y, bitMatrix.get(x, y = n2++) ? -16777216 : -1);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
