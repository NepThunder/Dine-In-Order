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
    private RecyclerView recyclerView;
    ArrayList<MainModel> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        MainAdapter mainAdapter=new MainAdapter(list, getContext());
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.notifyDataSetChanged();
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