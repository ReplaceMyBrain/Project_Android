package com.aoslec.androidproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.androidproject.Adapter.MyPage_UpdateAdapter;
import com.aoslec.androidproject.Bean.MyPayge_UpdateBean;
import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.kakao.network.ApiErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUpdateActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    private ArrayList<MyPayge_UpdateBean> data = null;
    private MyPage_UpdateAdapter
            adapter = null;
    private ListView listView = null;

    EditText nowPw, newPw1, newPw2;

    Button signout, pwChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_update);

        if (SaveSharedPreferences.getLoginMethod(MyUpdateActivity.this).equals("")) {
        } else {
//        else {
//                        Toast.makeText(MyUpdateActivity.this, "일반 회원만 비밀번호 변경이 가능합니다.", Toast.LENGTH_SHORT).show();
//                    }
        }

        nowPw = findViewById(R.id.et_pw_myupdate);
        newPw1 = findViewById(R.id.et_newpw1_myupdate);
        newPw2 = findViewById(R.id.et_newpw2_myupdate);

        signout = findViewById(R.id.btn_signout_myupdate);
        pwChange = findViewById(R.id.btn_changepw_myupdate);

        // Data 만들기
        data = new ArrayList<MyPayge_UpdateBean>();
        data.add(new MyPayge_UpdateBean(null, "성명", SaveSharedPreferences.getPrefName(MyUpdateActivity.this), null, null, "수정", "이름 변경하기"));
        data.add(new MyPayge_UpdateBean(null, "휴대폰번호", SaveSharedPreferences.getPrefPhone(MyUpdateActivity.this), null, "번호 변경을 위해서 인증 절차가 필요합니다.", "수정", "인증 문자 전송"));


        // Adapter 연결
        adapter = new MyPage_UpdateAdapter(MyUpdateActivity.this, R.layout.custom_button_layout, data);

        // ListView
        listView = findViewById(R.id.lv_custom_myupdate);
        listView.setAdapter(adapter);

        signout.setOnClickListener(signoutAction);
        pwChange.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(newPw1.getText().toString().trim()==null||nowPw.getText().toString().trim()==null) {
                Toast.makeText(MyUpdateActivity.this, "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else {
                if (SaveSharedPreferences.getPrefPw(MyUpdateActivity.this).equals(nowPw.getText().toString().trim())) {
                    if (7 >= newPw1.getText().toString().trim().length()) {
                        Toast.makeText(MyUpdateActivity.this, "패스워드는 최소 8자 입니다.", Toast.LENGTH_SHORT).show();
                        newPw1.requestFocus();
                    } else {
                        if (newPw1.getText().toString().trim().equals(newPw2.getText().toString().trim())) {
                            checkValidation(newPw1.getText().toString().trim());
                        } else {
                            Toast.makeText(MyUpdateActivity.this, "패스워드가 같지 않습니다.", Toast.LENGTH_SHORT).show();
                            newPw2.requestFocus();
                        }
                    }
                } else {
                    Toast.makeText(MyUpdateActivity.this, "기존 패스워드가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    nowPw.requestFocus();
                }
            }
        }
    };
    private void checkValidation(String new1){
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$");
        Matcher matcher = pattern.matcher(new1);
        if (!matcher.find()) {
            Toast.makeText(MyUpdateActivity.this, "비밀번호 양식을 확인해주세요", Toast.LENGTH_SHORT).show();
        }else {
            passwordChange(new1);
        }
    }
    private void passwordChange(String new1) {
        String urlAddr = ShareVar.sUrl + "update_pw_user.jsp?email=" + SaveSharedPreferences.getPrefEmail(MyUpdateActivity.this) + "&pw="+new1;
        Log.v("Message", urlAddr);
        String result = connectUpdateData(urlAddr);
        if (result.equals("1")) {
            SaveSharedPreferences.setPrefAutoLogin(MyUpdateActivity.this, "n");
            Toast.makeText(MyUpdateActivity.this, "비밀번호가 변경 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(MyUpdateActivity.this, "변경 실패되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    private String connectUpdateData(String urlAddr) {
        String result = null;
        try {
            User_NT userNT = new User_NT(MyUpdateActivity.this, urlAddr, "update");
            Object obj = userNT.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    View.OnClickListener signoutAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (SaveSharedPreferences.getLoginMethod(MyUpdateActivity.this)) {
                case "Kakao" :
                    UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                        @Override
                        public void onFailure(ErrorResult errorResult) { //회원탈퇴 실패 시
                            int result = errorResult.getErrorCode();
                            if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                Toast.makeText(MyUpdateActivity.this, "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MyUpdateActivity.this, "회원탈퇴에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) { //로그인 세션이 닫혀있을 시
                            //다시 로그인해달라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                            Toast.makeText(MyUpdateActivity.this, "로그인 세션이 닫혔습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onNotSignedUp() { //가입되지 않은 계정에서 회원탈퇴 요구 시
                            //가입되지 않은 계정이라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                            Toast.makeText(MyUpdateActivity.this, "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onSuccess(Long result) { //회원탈퇴에 성공하면
                            //"회원탈퇴에 성공했습니다."라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                            SaveSharedPreferences.clearAllData(MyUpdateActivity.this);
                            Toast.makeText(MyUpdateActivity.this, "회원탈퇴에 성공했습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
                case "Google" :
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(MyUpdateActivity.this, gso);
                    GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(MyUpdateActivity.this);
                    mGoogleSignInClient.revokeAccess();
                    SaveSharedPreferences.clearAllData(MyUpdateActivity.this);
                    break;
                case "" :
                    String urlAddr = ShareVar.sUrl + "update_outdate_user.jsp?email=" + SaveSharedPreferences.getPrefEmail(MyUpdateActivity.this);
                    Log.v("Message", urlAddr);
                    String result = connectUpdateData(urlAddr);
                    if (result.equals("1")) {
                        SaveSharedPreferences.setPrefAutoLogin(MyUpdateActivity.this, "n");
                        Toast.makeText(MyUpdateActivity.this, "정상적으로 탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
                        SaveSharedPreferences.clearAllData(MyUpdateActivity.this);
                        Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
                        startActivity(intent);
                        MyUpdateActivity.this.finish();
                    } else {
                        Toast.makeText(MyUpdateActivity.this, "탈퇴 실패되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
            Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
            startActivity(intent);
        }

        private String connectUpdateData(String urlAddr) {
            String result = null;
            try {
                User_NT userNT = new User_NT(MyUpdateActivity.this, urlAddr, "update");
                Object obj = userNT.execute().get();
                result = (String) obj;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    };
}