package com.aoslec.androidproject.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aoslec.androidproject.R;

public class Login_SignUpFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_sign_up, container, false);


        return view;
    }
}
