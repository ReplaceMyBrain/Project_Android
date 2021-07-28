package com.aoslec.androidproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetNewPwActivity extends AppCompatActivity {

    private static final String TAG = "SetNewPwActivity";
    SharedPreferences sharedPreferences;
    EditText findPwNewPw;
    EditText findPwNewPwCheck;
    TextView pwCheckMsg;
    Button newPwSubmitBtn;
    Context mContext;
    Intent intent;

    String email;
    String pw1, pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_pw);

        mContext = this;

        findPwNewPw = findViewById(R.id.findpw_check_edittext1);
        findPwNewPwCheck = findViewById(R.id.findpw_check_edittext2);

        pwCheckMsg = findViewById(R.id.findpw_check_check);

        newPwSubmitBtn = findViewById(R.id.findpw_check_button);
        newPwSubmitBtn.setOnClickListener(newPw);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

    }


    View.OnClickListener newPw = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pw1 = findPwNewPw.getText().toString().trim();
            pw2 = findPwNewPwCheck.getText().toString().trim();
            if (7 >= pw1.length()) {
                findPwNewPw.requestFocus();
                Toast.makeText(SetNewPwActivity.this, "비밀번호는 8자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                if (7 >= pw2.length()) {
                    findPwNewPwCheck.requestFocus();
                    Toast.makeText(SetNewPwActivity.this, "비밀번호는 8자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (!pw1.equals(pw2)) {
                        findPwNewPw.requestFocus();
                        Toast.makeText(SetNewPwActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        checkValidation(pw1, pw2);
                    }
                }
            }
        }
    };
    private void checkValidation(String new1, String new2){
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$");
        Matcher matcher = pattern.matcher(new1);
        pwCheckMsg.setVisibility(View.INVISIBLE);
        if (!matcher.find()) {
            pwCheckMsg.setText("비밀번호 양식을 확인해주세요");
            pwCheckMsg.setVisibility(View.VISIBLE);
        } else if (!new1.equals(new2)) {
            pwCheckMsg.setText("비밀번호 불일치");
            pwCheckMsg.setVisibility(View.VISIBLE);
        }else {
            passwordChange(new1, new2);
        }
    }

    private void passwordChange(String new1, String new2) {
        String urlAddr = ShareVar.sUrl + "update_pw_user.jsp?email=" + email + "&pw="+new1;
        Log.v("Message", urlAddr);
        String result = connectUpdateData(urlAddr);
        if (result.equals("1")) {
            SaveSharedPreferences.setPrefAutoLogin(SetNewPwActivity.this, "n");
            Toast.makeText(SetNewPwActivity.this, "비밀번호가 변경 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SetNewPwActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(SetNewPwActivity.this, "변경 실패되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    private String connectUpdateData(String urlAddr) {
        String result = null;
        try {
            User_NT userNT = new User_NT(SetNewPwActivity.this, urlAddr, "update");
            Object obj = userNT.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}