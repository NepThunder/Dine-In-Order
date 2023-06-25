package com.dineInOrder.application.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dineInOrder.application.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;
    Button login;
    TextView registerHere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText loginEmail=findViewById(R.id.loginEmail);
        EditText loginPassword=findViewById(R.id.loginPassword);
        progressBar=findViewById(R.id.progressBar);
        authProfile=FirebaseAuth.getInstance();
        login=findViewById(R.id.loginbtn);
        registerHere=findViewById(R.id.registerHere);
//        login.setVisibility(View.GONE);
        registerHere.setOnClickListener(view -> registerHere());
        login.setOnClickListener(view -> {
            String email=loginEmail.getText().toString();
            String password=loginPassword.getText().toString();

            if(TextUtils.isEmpty(email)){
                Toast.makeText(this, "Email isn't Entered", Toast.LENGTH_SHORT).show();
                loginEmail.setError("Enter your Email");
                loginEmail.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                loginEmail.setError("Enter your Email");
                loginEmail.requestFocus();
            }else if(TextUtils.isEmpty(password)){
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
                loginPassword.setError("Password is required");
                loginPassword.requestFocus();
            }else{
                progressBar.setVisibility(View.VISIBLE);
                loginUser(email,password);
            }
        });
    }

    private void loginUser(String email, String password) {

        authProfile.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    public void registerHere(){
        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}