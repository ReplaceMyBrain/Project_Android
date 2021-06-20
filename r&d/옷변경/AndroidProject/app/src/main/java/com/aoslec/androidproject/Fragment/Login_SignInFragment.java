package com.aoslec.androidproject.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aoslec.androidproject.Activity.MainActivity;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.ShareBar.ShareBar;

import java.util.ArrayList;

public class Login_SignInFragment extends Fragment {

    EditText etEmail,etPw;
    Button login;

    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_sign_in, container, false);
        login = view.findViewById(R.id.signin_btnLogin);

        etEmail = view.findViewById(R.id.signin_etEmail);
        etPw = view.findViewById(R.id.signin_etPw);

        etEmail.setAlpha(v);
        etPw.setAlpha(v);
        login.setAlpha(v);

        etEmail.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        etPw.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();

        login.setOnClickListener(onClickListener);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    };

}