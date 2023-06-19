package com.dineInOrder.application.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dineInOrder.application.Activities.DBHelper;
import com.dineInOrder.application.Activities.MainActivity;
import com.dineInOrder.application.R;
import com.dineInOrder.application.databinding.ActivityOrderDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class orderDetails extends AppCompatActivity {
    ActivityOrderDetailsBinding binding;
    ImageView imageView,addBtn,minusBtn;
    TextView foodName,itemPrice,desc,orderQuantity;
    EditText userName,userPhoneNumber;
    Button orderBtn;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        radioGroup=findViewById(R.id.paymentMethod);
        radioGroup.clearCheck();
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

        imageView = findViewById(R.id.detailImage);
        itemPrice = findViewById(R.id.itemPrice);
        foodName = findViewById(R.id.foodName);
        desc = findViewById(R.id.descriptionDetail);
        addBtn = findViewById(R.id.addBtn);
        minusBtn = findViewById(R.id.minusBtn);
        orderQuantity = findViewById(R.id.quantityValue);
        orderBtn = findViewById(R.id.orderBtn);
        userName = findViewById(R.id.userName);
        userPhoneNumber = findViewById(R.id.userPhoneNumber);
        final DBHelper helper = new DBHelper(this);


        FirebaseAuth auth;
        if (getIntent().getIntExtra("type", 0) == 1) {
            auth = FirebaseAuth.getInstance();
            String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

            int image = getIntent().getIntExtra("image", 0);
            int price = Integer.parseInt(getIntent().getStringExtra("price"));
            String description = getIntent().getStringExtra("desc");
            String name = getIntent().getStringExtra("name");

            imageView.setImageResource(image);
            foodName.setText(name);
            itemPrice.setText(String.valueOf(price));
            desc.setText(description);

            orderBtn.setOnClickListener(view -> {
//                if(TextUtils.isEmpty(payment)){
//                    Toast.makeText(this, "Select a payment method", Toast.LENGTH_SHORT).show();
//                    radioGroup.requestFocus();
//                }else
                    if (TextUtils.isEmpty(userName.getText().toString())) {
                    userName.setError("Name is required");
                    userName.requestFocus();
                } else if (TextUtils.isEmpty(userPhoneNumber.getText().toString())) {
                    userPhoneNumber.setError("Phone Number is required");
                    userPhoneNumber.requestFocus();
                }else {
                    boolean isInserted = helper.insertOrder(userName.getText().toString(), userPhoneNumber.getText().toString(), price, image, description, name, userId, Integer.parseInt(orderQuantity.getText().toString()));
                    if (isInserted) {
                        Toast.makeText(this, "Order Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Order Insertion failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            addBtn.setOnClickListener(view -> {
                int quantity = Integer.parseInt(orderQuantity.getText().toString());
                quantity++;
                int finalPrice = Integer.parseInt(itemPrice.getText().toString()) + Integer.parseInt(getIntent().getStringExtra("price"));
                itemPrice.setText(String.valueOf(finalPrice));
                orderQuantity.setText(String.valueOf(quantity));
            });
            minusBtn.setOnClickListener(view -> {
                int quantity = Integer.parseInt(orderQuantity.getText().toString());
                if (quantity == 1) {
                    Toast.makeText(this, "Quantity should be at least 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                quantity--;
                int finalPrice = Integer.parseInt(itemPrice.getText().toString()) - Integer.parseInt(getIntent().getStringExtra("price"));
                itemPrice.setText(String.valueOf(finalPrice));
                orderQuantity.setText(String.valueOf(quantity));
            });
        }else{
              /*
        id=0
        name=1
        phone=2
        price=3
        image=4
        quantity=5
        desc=6
        foodName=7
        userId=8
        payment=9
        */

            int id=getIntent().getIntExtra("id",0);
            Cursor cursor=helper.getOrderById(id);
            final int image=cursor.getInt(4);
            final int unitPrice=cursor.getInt(3)/cursor.getInt(5);
            auth = FirebaseAuth.getInstance();
            String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
            userName.setText(cursor.getString(1));
            userPhoneNumber.setText((cursor.getString(2)));
            itemPrice.setText(String.valueOf(cursor.getInt(3)));
            imageView.setImageResource(cursor.getInt(4));
            orderQuantity.setText(String.valueOf(cursor.getInt(5)));
            desc.setText(cursor.getString(6));
            foodName.setText(cursor.getString(7));
            orderBtn.setText("Update Now");
            orderBtn.setOnClickListener(view -> {
               boolean isUpdated=helper.updateOrder(
                       binding.userName.getText().toString(),
                       binding.userPhoneNumber.getText().toString(),
                       Integer.parseInt(binding.itemPrice.getText().toString()),
                       image,
                       binding.descriptionDetail.getText().toString(),
                       binding.foodName.getText().toString(),userId,
                       Integer.parseInt(binding.quantityValue.getText().toString()),id);
               if(isUpdated){
                   Toast.makeText(this, "Order Updated Successfully", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(this,MainActivity.class));
                   finish();
               }else{
                   Toast.makeText(this, "Order Update failed", Toast.LENGTH_SHORT).show();
                }
            });

            addBtn.setOnClickListener(view -> {
                int quantity = Integer.parseInt(orderQuantity.getText().toString());
                quantity++;
                int finalPrice = Integer.parseInt(itemPrice.getText().toString()) + unitPrice;
                itemPrice.setText(String.valueOf(finalPrice));
                orderQuantity.setText(String.valueOf(quantity));
            });
            minusBtn.setOnClickListener(view -> {
                int quantity = Integer.parseInt(orderQuantity.getText().toString());
                if (quantity == 1) {
                    Toast.makeText(this, "Quantity should be at least 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                quantity--;
                int finalPrice = Integer.parseInt(itemPrice.getText().toString()) - unitPrice;
                itemPrice.setText(String.valueOf(finalPrice));
                orderQuantity.setText(String.valueOf(quantity));
            });
        }
    }
    public void checkButton(View v){
            int radioId=radioGroup.getCheckedRadioButtonId();
            radioButton=findViewById(radioId);
    }
}