package com.example.wims_new.LocalDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.example.wims_new.model.MawbData;
import com.example.wims_new.model.MawbDetails;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class LocalDBHelper extends SQLiteOpenHelper {

    public static String dbName = "wims_db.db";
    public static int dbVersion = 1;
    public static String dbPath = "";
    Context myContext;
    SQLiteDatabase db;
    Cursor cursor = null;

    public LocalDBHelper(Context context) {
        super(context, dbName, null, dbVersion);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS mawb_details (actual_pcs INTEGER, weight INTEGER, volume REAL, length INTEGER, width INTEGER, height INTEGER," +
                "cargo_category TEXT, cargo_class TEXT, cargo_status TEXT, mawb_number TEXT, hawb_number TEXT, flight_number TEXT)");

//        db.execSQL("");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging();
        }
    }

    private boolean ExistDatabase() {
        File myFile = new File(dbPath + dbName);
        return myFile.exists();

    }

    private void CopyDatabase() {
        try {
            InputStream myInput = myContext.getAssets().open(dbName);
            OutputStream myOutput = new FileOutputStream(dbPath + dbName);
            byte[] myBuffer = new byte[2048];
            int length;
            while ((length = myInput.read(myBuffer)) > 0) {
                myOutput.write(myBuffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void StartWork() {
//        dbPath = myContext.getFilesDir().getParent() + "/databases/";
//        if (!ExistDatabase()) {
//            this.getWritableDatabase();
//            CopyDatabase();
//        } else {
//            this.getWritableDatabase();
//        }

        dbPath = myContext.getFilesDir().getParent() + "/databases/";
        if (!ExistDatabase()) {
            this.getReadableDatabase();  // Use getReadableDatabase to create the database file
            CopyDatabase();
        }

    }

    public boolean insertRoleId(int role_id) {
        db = getWritableDatabase();
        try {
            db.execSQL("INSERT INTO user_info(role_id) VALUES('" + role_id + "')");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean deleteRoleId() {
        db = getWritableDatabase();
        try {
            db.execSQL("DELETE FROM user_info");
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        return true;

    }


    public int getRoleId() {
        db = getReadableDatabase();
        try {
            cursor = db.rawQuery("SELECT role_id FROM user_info", null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public boolean insertMawbDetails(String actual_pcs, String weight, String volume, String length, String width, String height, String cargo_category, String cargo_class, String cargo_status, String mawb_number, String hawb_number, String flight_number) {
        db = getWritableDatabase();
        try {
            db.execSQL("INSERT INTO mawb_details(actual_pcs, weight, volume, length, width, height, cargo_category, cargo_class, cargo_status, mawb_number, hawb_number,flight_number) " +
                    "VALUES('" + actual_pcs + "', '" + weight + "', '" + volume + "', '" + length + "', '" + width + "', '" + height + "', '" + cargo_category + "', '" + cargo_class + "', " +
                    "'" + cargo_status + "', '" + mawb_number + "', '" + hawb_number + "', '" + flight_number + "')");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean insertUldNumber(String uld_number, String uld_condition) {
        db = getWritableDatabase();
        try {
            db.execSQL("INSERT INTO storage_details(uld_number, uld_condition)" +
                    "VALUES ('" + uld_number + "', '" + uld_condition + "')");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteMawbDetails() {
        db = getWritableDatabase();
        try {
            System.out.println("PASOOOOK NAAAA");
            db.execSQL("DELETE FROM mawb_details");
            System.out.println("LABAAAAS NAAAA");
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        return true;

    }

    public MawbDetails getMawbDetails() {
        db = getReadableDatabase();
        MawbDetails m = null;
        try {
            cursor = db.rawQuery("SELECT actual_pcs, weight, volume, length, width, height, cargo_category, cargo_class, cargo_status, mawb_number, hawb_number, flight_number FROM mawb_details", null);
            if (cursor.moveToFirst()) {
                do {
                    m = new MawbDetails(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getFloat(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getInt(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9),
                            cursor.getString(10),
                            cursor.getString(11)
                    );
                } while (cursor.moveToNext());
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return m;
    }


}
