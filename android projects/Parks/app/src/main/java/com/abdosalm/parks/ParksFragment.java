package com.abdosalm.parks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdosalm.parks.adapter.OnParkClickListener;
import com.abdosalm.parks.adapter.ParkRecyclerViewAdapter;
import com.abdosalm.parks.model.Park;
import com.abdosalm.parks.model.ParkViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParksFragment extends Fragment implements OnParkClickListener {
    private RecyclerView recyclerView;
    private ParkRecyclerViewAdapter parkRecyclerViewAdapter;
    private List<Park> parkList;
    private ParkViewModel parkViewModel;
    private static final String TAG = "ParksFragment";

    public ParksFragment() {
        // Required empty public constructor
    }

    public static ParksFragment newInstance() {
        ParksFragment fragment = new ParksFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parks, container, false);
        recyclerView = view.findViewById(R.id.park_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parkViewModel = new ViewModelProvider(requireActivity()).get(ParkViewModel.class);

        if (parkViewModel.getParks().getValue() != null){
            parkList = parkViewModel.getParks().getValue();
            parkRecyclerViewAdapter = new ParkRecyclerViewAdapter(parkList , this);
            recyclerView.setAdapter(parkRecyclerViewAdapter);
        }
      //  Repository.getParks(parks -> {

      //  });
    }

    @Override
    public void onParkClicked(Park park) {
        parkViewModel.selectPark(park);
        getFragmentManager().beginTransaction().replace(R.id.park_fragment , DetailsFragment.newInstance()).commit();
    }
}