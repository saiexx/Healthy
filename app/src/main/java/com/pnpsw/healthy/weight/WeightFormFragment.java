package com.pnpsw.healthy.weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pnpsw.healthy.R;

public class WeightFormFragment extends Fragment{

    FirebaseFirestore mdb;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mdb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        initSaveBtn();

    }

    void initSaveBtn() {
        mAuth.getCurrentUser();
        final String uid = mAuth.getUid();
        Button addBtn = getView().findViewById(R.id.weight_form_save_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText weight = getView().findViewById(R.id.weight_form_weightText);
                EditText date = getView().findViewById(R.id.weight_form_dateText);

                int weightInt = Integer.parseInt(weight.getText().toString());
                String dateStr = date.getText().toString();
                weight.clearFocus();
                date.clearFocus();

                Weight weightObj = new Weight(dateStr, weightInt);

                mdb.collection("myfitness").document(uid).collection("weight").document(dateStr).set(weightObj).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_view, new WeightFragment())
                                    .addToBackStack(null)
                                    .commit();
                        }else{
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
