package com.dineInOrder.application.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.dineInOrder.application.Fragments.HomeFragment;
import com.dineInOrder.application.Fragments.cartFragment;
import com.dineInOrder.application.Fragments.profileFragment;
import com.dineInOrder.application.R;
import com.dineInOrder.application.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    FrameLayout frameLayout;
    cartFragment cartfragment;
    profileFragment profilefragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Home");
        bottomNavigationView =findViewById(R.id.bottomNavigationBar);
        frameLayout=findViewById(R.id.container);
        homeFragment=new HomeFragment();
        cartfragment=new cartFragment();
        profilefragment=new profileFragment();
        String openFragment = getIntent().getStringExtra("openFragment");
        if (openFragment != null && openFragment.equals("fragmentProfile")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, profilefragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.profile); // Set the selected item in the bottom navigation view
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        }
        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.home:
                    setTitle("Home");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                    return true;
                case R.id.cart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,cartfragment,"cart_Fragment").commit();
                    return true;
                case R.id.profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,profilefragment).commit();
                    return true;
            }
            return false;
        } );
    }
}