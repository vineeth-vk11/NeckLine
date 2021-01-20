package com.example.NeckLine.HomeHelper;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.NeckLine.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FirebaseFirestore db;
    RecyclerView modelsRecycler;
    ArrayList<ModelsModel> modelsModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseApp.initializeApp(getContext());

        db = FirebaseFirestore.getInstance();
        modelsRecycler = view.findViewById(R.id.modelsRecycler);
        modelsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        modelsRecycler.setHasFixedSize(true);

        modelsModelArrayList = new ArrayList<>();

        db.collection("models").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                modelsModelArrayList.clear();

                for(DocumentSnapshot documentSnapshot: value.getDocuments()){

                    ModelsModel modelsModel = new ModelsModel();
                    modelsModel.setItemName(documentSnapshot.getString("name"));
                    modelsModel.setItemImage(documentSnapshot.getString("image"));
                    modelsModel.setItemId(documentSnapshot.getId());

                    modelsModelArrayList.add(modelsModel);

                }

                ModelsAdapter modelsAdapter = new ModelsAdapter(getContext(), modelsModelArrayList);
                modelsRecycler.setAdapter(modelsAdapter);

            }
        });

        return view;
    }
}