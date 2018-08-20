package com.example.a59070180y3.healthy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a59070180y3.healthy.R;

/**
 * Created by LAB203_53 on 20/8/2561.
 */

public class RegisterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button _regisBtn = (Button) getView().findViewById(R.id.regisBtn);
        _regisBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText _userID = (EditText) getView().findViewById(R.id.userID);
                String _userIDStr = _userID.getText().toString();
                Log.d("REGISTER","Username:"+_userIDStr);

                EditText _password = (EditText) getView().findViewById(R.id.passwordField);
                String _passwordStr = _password.getText().toString();
                Log.d("REGISTER","Password:"+_passwordStr);

                EditText _name = (EditText) getView().findViewById(R.id.nameField);
                String _nameStr = _name.getText().toString();
                Log.d("REGISTER","Name:"+_nameStr);

                EditText _age = (EditText) getView().findViewById(R.id.ageField);
                String _ageStr = _age.getText().toString();
                int _ageInt = Integer.parseInt(_ageStr);
                Log.d("REGISTER","Age:"+_ageStr);

                if(_userIDStr.isEmpty() || _passwordStr.isEmpty() || _nameStr.isEmpty() || _ageStr.isEmpty()){
                    Toast.makeText(getActivity(),"Username, Password, Age, or Name is EMPTY",Toast.LENGTH_SHORT).show();
                }
                else if(_userIDStr.equals("admin")){
                    Toast.makeText(getActivity(),"Username Already Exist",Toast.LENGTH_SHORT).show();
                }
                else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new BMIFragment()).addToBackStack(null).commit();
                }
            }
        });
    }
}
