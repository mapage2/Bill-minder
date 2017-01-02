package com.example.amasio.bill_minder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
    }

    public void register(View v){
        Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(i);
    }

    public void login(View v){
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
