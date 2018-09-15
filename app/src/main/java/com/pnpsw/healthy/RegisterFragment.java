package com.pnpsw.healthy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class RegisterFragment extends Fragment {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private EditText editTextEmail;
    private EditText editTextPw;
    private EditText editTextRePw;
    private Button buttonRegist;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        editTextEmail = (EditText) getView().findViewById(R.id.email_text);
        editTextPw = (EditText) getView().findViewById(R.id.password_text);
        editTextRePw = (EditText) getView().findViewById(R.id.re_text);

        buttonRegist = (Button) getView().findViewById(R.id.register_btn);

        buttonRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPw.getText().toString().trim();
        String repassword = editTextRePw.getText().toString().trim();

        //checking empty text
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(),"Please fill your email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(),"Please fill your password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(repassword)) {
            Toast.makeText(getActivity(),"Please re enter your password",Toast.LENGTH_LONG).show();
            return;
        }

        //if password are not the same
        if(!TextUtils.equals(password,repassword)){
            Toast.makeText(getActivity(),"Please re enter your same password",Toast.LENGTH_LONG).show();
            return;
        }

        //if password length is lower than 6
        if(password.length() < 6){
            Toast.makeText(getActivity(),"Please fill password more than 6 character",Toast.LENGTH_LONG).show();
            return;
        }
        Log.d("register", "regist success");
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("register", "created user");
                    user = firebaseAuth.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("EMAIL","sent");
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).addToBackStack(null).commit();
                                Log.d("register", "go to menu");
                            }else{
                                Log.d("EMAIL","Not sent");
                            }
                        }
                    });
                }else{
                    Log.d("register", "fail to create user");
                    Toast.makeText(getActivity(), "Fail to create User.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
