package com.example.a59070180y3.healthy;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by LAB203_53 on 20/8/2561.
 */

public class BMIFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bmi, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button _calculateBtn = (Button) getView().findViewById(R.id.calculateBtn);
        _calculateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText _weight = (EditText) getView().findViewById(R.id.weight);
                EditText _height = (EditText) getView().findViewById(R.id.height);
                String _heightStr = _height.getText().toString();
                String _weightStr = _weight.getText().toString();
                double _heightNum = Double.parseDouble(_heightStr);
                double _weightNum = Double.parseDouble(_weightStr);
                double bmi = _weightNum/((_heightNum*_heightNum)/100)*100;
                String bmiStr = String.format("%.2f",bmi);
                Log.d("BMI","W = "+_weightNum);
                Log.d("BMI","H = "+_heightNum);
                Log.d("BMI","BMI = "+bmiStr);

                TextView bmiView = (TextView) getView().findViewById(R.id.bmiResult);
                bmiView.setText(bmiStr);

            }
        });
        Button _backBtn = (Button) getView().findViewById(R.id.backBtn);
        _backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).addToBackStack(null).commit();
            }
        });
    }
}
