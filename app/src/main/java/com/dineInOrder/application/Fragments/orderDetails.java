package com.dineInOrder.application.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dineInOrder.application.Activities.DBHelper;
import com.dineInOrder.application.Activities.MainActivity;
import com.dineInOrder.application.R;
import com.dineInOrder.application.databinding.ActivityOrderDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class orderDetails extends AppCompatActivity {
    ActivityOrderDetailsBinding binding;
    private FirebaseAuth auth;
    ImageView imageView,addBtn,minusBtn;
    TextView foodName,itemPrice,desc,orderQuantity;
    EditText userName,userPhoneNumber;
    Button orderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        imageView=findViewById(R.id.detailImage);
        itemPrice=findViewById(R.id.itemPrice);
        foodName=findViewById(R.id.foodName);
        desc=findViewById(R.id.descriptionDetail);
        addBtn=findViewById(R.id.addBtn);
        minusBtn=findViewById(R.id.minusBtn);
        orderQuantity=findViewById(R.id.quantityValue);
        orderBtn=findViewById(R.id.orderBtn);
        userName=findViewById(R.id.userName);
        userPhoneNumber=findViewById(R.id.userPhoneNumber);

        auth=FirebaseAuth.getInstance();
        String userId=auth.getCurrentUser().getUid();

        int image=getIntent().getIntExtra("image",0);
        int price=Integer.parseInt(getIntent().getStringExtra("price"));
        String description=getIntent().getStringExtra("desc");
        String name=getIntent().getStringExtra("name");

        imageView.setImageResource(image);
        foodName.setText(name);
        itemPrice.setText(String.valueOf(price));
        desc.setText(description);
        final DBHelper helper=new DBHelper(this);


       orderBtn.setOnClickListener(view -> {
            boolean isInserted=helper.insertOrder(userName.getText().toString(),userPhoneNumber.getText().toString(),price,image,description,name,userId,Integer.parseInt(orderQuantity.getText().toString()));
            if(isInserted){
                Toast.makeText(this, "Order Inserted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }else{
                Toast.makeText(this, "Order Insertion failed", Toast.LENGTH_SHORT).show();
            }
        });

        addBtn.setOnClickListener(view->{
            int quantity=Integer.parseInt(orderQuantity.getText().toString());
            quantity++;
            int finalPrice=Integer.parseInt(itemPrice.getText().toString())+Integer.parseInt(getIntent().getStringExtra("price"));
            itemPrice.setText(String.valueOf(finalPrice));
            orderQuantity.setText(String.valueOf(quantity));
        });
        minusBtn.setOnClickListener(view->{
            int quantity=Integer.parseInt(orderQuantity.getText().toString());
            if(quantity==1){
                Toast.makeText(this, "Quantity should be at least 1", Toast.LENGTH_SHORT).show();
                return;
            }
            quantity--;
            int finalPrice=Integer.parseInt(itemPrice.getText().toString())-Integer.parseInt(getIntent().getStringExtra("price"));
            itemPrice.setText(String.valueOf(finalPrice));
            orderQuantity.setText(String.valueOf(quantity));
        });
    }
}