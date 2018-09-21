package com.pnpsw.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment{

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = mAuth.getCurrentUser();
        if(user!=null){
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new MenuFragment())
                    .addToBackStack(null)
                    .commit();
        }
        initLoginBtn();
        initRegisterBtn();

    }

    void initLoginBtn() {
        Button _loginBtn = (Button) getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _userId = (EditText) getView().findViewById(R.id.login_user_id);
                EditText _password = (EditText) getView().findViewById(R.id.login_password);
                String _userIdStr = _userId.getText().toString();
                String _passwordStr = _password.getText().toString();
                if(_userIdStr.isEmpty() || _passwordStr.isEmpty()){
                    Toast.makeText(getActivity(), "Fill your Email or password", Toast.LENGTH_SHORT).show();
                    Log.d("USER", "USER OR PASSWORD IS EMPTY");
                    return;
                }
                mAuth.signInWithEmailAndPassword(_userIdStr,_passwordStr).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("LOGIN","Login Success");
                            user = mAuth.getCurrentUser();
                            if(user.isEmailVerified()) {
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.main_view, new MenuFragment())
                                        .addToBackStack(null)
                                        .commit();
                            }else{
                                Toast.makeText(getActivity(),"Please verify your email",Toast.LENGTH_SHORT).show();
                                Log.d("LOGIN","Email doesn't verify");
                                mAuth.signOut();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Login failed, Please check your ID and Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    void initRegisterBtn() {
        TextView _registerBtn = (TextView) getView().findViewById(R.id.login_resiget_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LOGIN", "go to register");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
