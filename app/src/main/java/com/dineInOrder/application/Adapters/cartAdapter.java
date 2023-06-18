package com.dineInOrder.application.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dineInOrder.application.Activities.DBHelper;
import com.dineInOrder.application.Fragments.orderDetails;
import com.dineInOrder.application.Models.cartModel;
import com.dineInOrder.application.R;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.viewHolder>{

    ArrayList<cartModel> list;
    Context context;

    public cartAdapter(ArrayList<cartModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.order_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final cartModel model=list.get(position);
        holder.orderImage.setImageResource(model.getOrderImage());
        holder.orderName.setText(model.getOrderName());
        holder.orderId.setText(model.getOrderId());
        holder.price.setText(model.getPrice());

        holder.itemView.setOnClickListener(view->{
            Intent intent=new Intent(context, orderDetails.class);
            intent.putExtra("id",Integer.parseInt(model.getOrderId()));
            intent.putExtra("type",2);
            context.startActivity(intent);
        });
        holder.itemView.setOnLongClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        DBHelper helper=new DBHelper(context);
                        if(helper.deleteOrder(model.getOrderId())>0){
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            ((Activity) context).recreate();
                        }else {
                            Toast.makeText(context, "Unexpected error occurred", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No",(dialogInterface, i) -> Toast.makeText(context, "Order Deletion Canceled", Toast.LENGTH_SHORT).show()).show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{

        ImageView orderImage;
        TextView orderName,orderId,price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            orderImage=itemView.findViewById(R.id.orderImage);
            orderName=itemView.findViewById(R.id.orderName);
            orderId=itemView.findViewById(R.id.orderId);
            price=itemView.findViewById(R.id.price);
        }
    }
}
