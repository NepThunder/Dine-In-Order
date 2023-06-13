package com.dineInOrder.application.Fragments;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dineInOrder.application.R;

public class orderDetails extends AppCompatActivity {
    ImageView imageView,addBtn,minusBtn;
    TextView foodName,itemPrice,desc,orderQuantity;

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

        int image=getIntent().getIntExtra("image",0);
        int price=Integer.parseInt(getIntent().getStringExtra("price"));
        String description=getIntent().getStringExtra("desc");
        String name=getIntent().getStringExtra("name");

        imageView.setImageResource(image);
        foodName.setText(name);
        itemPrice.setText(String.valueOf(price));
        desc.setText(description);

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
                Toast.makeText(this, "Qunatity should be at least 1", Toast.LENGTH_SHORT).show();
                return;
            }
            quantity--;
            int finalPrice=Integer.parseInt(itemPrice.getText().toString())-Integer.parseInt(getIntent().getStringExtra("price"));
            itemPrice.setText(String.valueOf(finalPrice));
            orderQuantity.setText(String.valueOf(quantity));
        });
    }
}