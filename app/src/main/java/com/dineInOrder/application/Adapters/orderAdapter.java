package com.dineInOrder.application.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dineInOrder.application.Models.orderModel;
import com.dineInOrder.application.R;

import java.util.ArrayList;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.viewHolder>{

    ArrayList<orderModel> list;
    Context context;

    public orderAdapter(ArrayList<orderModel> list, Context context) {
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
        final orderModel model=list.get(position);
        holder.orderImage.setImageResource(model.getOrderImage());
        holder.orderName.setText(model.getOrderName());
        holder.orderId.setText(model.getOrderId());
        holder.price.setText(model.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

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
