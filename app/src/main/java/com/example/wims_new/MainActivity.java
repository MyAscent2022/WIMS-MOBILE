package com.example.wims_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.wims_new.ui.Login.view.LoginPage;
import com.example.wims_new.ui.mainMenu.MainMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent in = new Intent(MainActivity.this, LoginPage.class);
//        startActivity(in);


//        AppVersion v = new AppVersion();
//        txt_version.setText("version " + v.version);

        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                    Intent in = new Intent(MainActivity.this, LoginPage.class);
                    startActivity(in);
//                    startActivity(new Intent(MainActivity.this, LoginPage.class));
//                    Animatoo.INSTANCE.animateSwipeLeft(MainActivity.this);

                    finish();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        };
        background.start();
    }


}