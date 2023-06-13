package com.dineInOrder.application.Models;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.dineInOrder.application.Activities.LoginActivity;
import com.dineInOrder.application.R;

public class Profile extends AppCompatActivity {
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        logout=findViewById(R.id.logoutbtn);
        logout.setOnClickListener(view -> logOut());
    }
    public void logOut(){
        Intent intent=new Intent(Profile.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}