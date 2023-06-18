package com.dineInOrder.application.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dineInOrder.application.Activities.DBHelper;
import com.dineInOrder.application.Adapters.cartAdapter;
import com.dineInOrder.application.Models.cartModel;
import com.dineInOrder.application.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class cartFragment extends Fragment {

    public cartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    ArrayList<cartModel> list;
    DBHelper helper;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            getActivity().setTitle("Cart");
        }
        helper=new DBHelper(getContext());
        list=helper.getOrders();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        cartAdapter adapter=new cartAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
