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
import android.widget.Toast;

/**
 * Created by LAB203_53 on 20/8/2561.
 */

public class LoginFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button _loginBtn = (Button) getView().findViewById(R.id.button3);
        _loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText _userID = (EditText) getView().findViewById(R.id.userID);
                EditText _password = (EditText) getView().findViewById(R.id.passwordID);
                String _userIdStr = _userID.getText().toString();
                String _passwordStr = _password.getText().toString();

                Log.d("LOGIN","Username: "+_userIdStr);
                Log.d("LOGIN","Password: "+_passwordStr);
                if(_userIdStr.isEmpty() || _passwordStr.isEmpty()){
                    Toast.makeText(getActivity(),"Please fill up Username and Password", Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN", "USERNAME OR PASSWORD IS EMPTY");
                }
                else if(_userIdStr.equals("admin") && _passwordStr.equals("admin")){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new BMIFragment()).addToBackStack(null).commit();
                    Log.d("LOGIN", "GO TO BMI");
                }
                else{
                    Toast.makeText(getActivity(),"Incorrect ID", Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN", "INCORRECT ID");
                }
            }
        });
        Button _registerBtn = (Button) getView().findViewById(R.id.registerBtn);
        _registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).addToBackStack(null).commit();
                Log.d("LOGIN", "GO TO REGISTER");
            }
        });
    }
}
