package com.pnpsw.healthy.weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pnpsw.healthy.MenuFragment;
import com.pnpsw.healthy.R;

import java.util.ArrayList;
import java.util.Collections;

public class WeightFragment extends Fragment {

    ArrayList<Weight> weights = new ArrayList<>();
    FirebaseFirestore mdb;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mdb = FirebaseFirestore.getInstance();
        initImportData();
        initAddWeight();
        initBackButton();

    }

    private void initBackButton() {
        Button _btn = getView().findViewById(R.id.weight_back_btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    void initImportData() {
        ListView weightList = getView().findViewById(R.id.weight_list);
        final WeightAdapter weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_wegith_item, weights);
        weightList.setAdapter(weightAdapter);

        final String uid = mAuth.getUid();

        mdb.collection("myfitness").document(uid).collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                weightAdapter.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    weights.add(doc.toObject(Weight.class));
                }
                Collections.reverse(weights);
                initStatusGenerator();

                weightAdapter.notifyDataSetChanged();
            }
        });
    }

    void initStatusGenerator(){
        for(int i=0; i<weights.size() -1; i++){
            if (weights.get(i).getWeight() > weights.get(i+1).getWeight()){
                weights.get(i).setStatus("UP");
            }else if(weights.get(i).getWeight() < weights.get(i+1).getWeight()){
                weights.get(i).setStatus("DOWN");
            }else{
                weights.get(i).setStatus("");
            }
        }
    }
    void initAddWeight() {
        Button _btn = getView().findViewById(R.id.weight_add_btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
