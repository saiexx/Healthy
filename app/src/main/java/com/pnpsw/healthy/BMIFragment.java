package com.pnpsw.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BMIFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCalcBtn();

    }

    void initCalcBtn(){
        Button _btn = getView().findViewById(R.id.bmi_calc_btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView _result = getView().findViewById(R.id.bmi_result);
                EditText height = getView().findViewById(R.id.bmi_height);
                EditText weight = getView().findViewById(R.id.bmi_weight);

                String height_str = height.getText().toString();
                String weight_str = weight.getText().toString();

                if(height_str.isEmpty() || weight_str.isEmpty()){
                    Toast.makeText(getActivity(),"Please fill blank information",Toast.LENGTH_SHORT).show();
                }else{
                    float height_fl = Float.parseFloat(height_str);
                    float weight_fl = Float.parseFloat(weight_str);

                    float bmi = weight_fl/((height_fl/100)*(height_fl/100));

                    String result_str = String.format("%.2f",bmi);
                    _result.setText(result_str);
                }

            }
        });
    }

}
