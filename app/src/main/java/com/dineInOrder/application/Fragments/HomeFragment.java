package com.dineInOrder.application.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dineInOrder.application.Adapters.MainAdapter;
import com.dineInOrder.application.Models.MainModel;
import com.dineInOrder.application.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    ArrayList<MainModel> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Rename and change types of parameters

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialized();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        MainAdapter mainAdapter=new MainAdapter(list, getContext());
        recyclerView.setAdapter(mainAdapter);
//        mainAdapter.notifyDataSetChanged();
    }

    private void dataInitialized() {
        list =new ArrayList<>();
        list.add(new MainModel(R.drawable.northern_meal,"Northern Meal","40","Northern Indian cuisine is characterized by the use of rich and creamy gravies, flatbread like naan and roti, and a variety of vegetarian dishes."));
        list.add(new MainModel(R.drawable.dosa,"Dosa","40","Dosa is a popular South Indian dish made from a fermented batter of rice and lentils, typically served with sambar and chutney."));
        list.add(new MainModel(R.drawable.idly,"Idly","30","Idly is a traditional South Indian breakfast dish made from fermented rice and lentil batter, typically served with sambar and coconut chutney."));
        list.add(new MainModel(R.drawable.pokora,"Pakora","20","Pakora is a popular Indian snack made by deep-frying batter-coated vegetables, paneer, or meat, often served with chutney."));
        list.add(new MainModel(R.drawable.shrimp_curry,"Shrimp Curry","100","Shrimp curry is a flavorful Indian dish made with succulent shrimp cooked in a spicy and aromatic sauce typically served with rice or bread."));
        list.add(new MainModel(R.drawable.veg_pulau,"Veg Pulau","60","Veg pulao is a fragrant Indian rice dish made with basmati rice, mixed vegetables, and aromatic spices, typically served with raita or curry."));

    }
}