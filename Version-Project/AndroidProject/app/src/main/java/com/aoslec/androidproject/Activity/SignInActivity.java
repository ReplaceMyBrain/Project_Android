package com.aoslec.androidproject.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.androidproject.Bean.User;
import com.aoslec.androidproject.KakaoSetting.SessionCallback;
import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class SignInActivity extends Activity {

    ArrayList<User> users;
    String urlAddr;

    private static final String TAG = "SignInActivity";

    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    EditText etEmail,etPw;
    Button btn_common_login, btn_kakao_login, btn_intent_signup, btn_search_idpw;
    CheckBox cb_AutoLogin;
    private SignInButton btn_google_login;
    private SessionCallback sessionCallback;
    Session session;

    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SaveSharedPreferences.getPrefAutoLogin(this).equals("y") || SaveSharedPreferences.getPrefIsLogin(this).equals("y")) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_signin);

        getHashKey();

        btn_search_idpw = findViewById(R.id.btn_search_idpw);
        btn_common_login = findViewById(R.id.btn_common_login);
        btn_kakao_login = findViewById(R.id.btn_kakao_login);
        btn_google_login = findViewById(R.id.btn_google_login);
        btn_intent_signup = findViewById(R.id.singup_page_btn);
        cb_AutoLogin = findViewById(R.id.cb_AutoLogin);


        etEmail = findViewById(R.id.signin_etEmail);
        etPw = findViewById(R.id.signin_etPw);

        Log.e(TAG, "onResume: 자동로그인여부 : " + SaveSharedPreferences.getPrefAutoLogin(this));


        //카카오 로그인 콜백 초기화
        sessionCallback = new SessionCallback(SignInActivity.this);
        Session.getCurrentSession().addCallback(sessionCallback);

        //카카오 자동 로그인
        Session.getCurrentSession().checkAndImplicitOpen();

        mAuth = FirebaseAuth.getInstance();
        // DEFAULT_SIGN_IN parameter는 유저의 ID와 기본적인 프로필 정보를 요청하는데 사용된다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail() // email addresses도 요청함
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(SignInActivity.this, gso);
        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(SignInActivity.this);
        // 구글 자동 로그인
        if (gsa != null && gsa.getId() != null) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        etEmail.setAlpha(v);
        etPw.setAlpha(v);
        btn_common_login.setAlpha(v);
        btn_kakao_login.setAlpha(v);
        btn_google_login.setAlpha(v);

        etEmail.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        etPw.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        btn_common_login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        btn_kakao_login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(900).start();
        btn_google_login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();


        btn_common_login.setOnClickListener(onClickListener);
        btn_kakao_login.setOnClickListener(kakaoClickListener);
        btn_google_login.setOnClickListener(googleClickListener);
        btn_search_idpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, IdPwActivity.class);
                startActivity(intent);
            }
        });
        btn_intent_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String strID = etEmail.getText().toString().trim();
            String strPW = etPw.getText().toString().trim();
            if (strID.isEmpty()) {
                Toast.makeText(SignInActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else if (strPW.isEmpty()) {
                Toast.makeText(SignInActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                loginAction(strID, strPW);

            }
        }
    };
    View.OnClickListener kakaoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, SignInActivity.this);
        }
    };
    View.OnClickListener googleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    };

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignInActivity.this, "Authentication Successed.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    /**
     * 로그인 버튼 클릭 후, 다시 값을 받아옴
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        else if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * 구글
     * 로그인 후, 데이터를 받아오는 메소드
     * @param completedTask
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            if (acct != null) {
                SaveSharedPreferences.setPrefIsLogin(SignInActivity.this, "y");
                SaveSharedPreferences.setPrefAutoLogin(SignInActivity.this, "y");
                SaveSharedPreferences.setLoginMethod(SignInActivity.this, "Google");
                SaveSharedPreferences.setPrefEmail(SignInActivity.this, acct.getEmail());
                SaveSharedPreferences.setPrefName(SignInActivity.this, acct.getDisplayName());
                SaveSharedPreferences.setPrefImage(SignInActivity.this, acct.getPhotoUrl().toString());
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    //HashKey얻는 메소드
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    /**
     * @Method Name : loginAction
     * @작성일 : 2021/06/19
     * @작성자 : biso
     * @Method 설명 :구글이나 페이스북 연동 없이 로그인
     * @변경이력 :
     * @Param&return : [id, pw] & void
     */
    public void loginAction(String id, String pw) {
        ShareVar.login_fail = 0;
        Log.e(TAG, "login: 로그인 진입");
        btn_common_login.setEnabled(false);

        if (etEmail.getText().toString().trim().equals("") || etEmail.getText().toString().trim() == null) {
            Toast.makeText(getApplicationContext(), "이메일 입력해 주세요", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
        } else if (etPw.getText().toString().trim().equals("") || etPw.getText().toString().trim() == null) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            etPw.requestFocus();
        } else {
            try {
                urlAddr = ShareVar.sUrl + "select_find_user_where_email.jsp?email=" + etEmail.getText().toString();
                Log.v("Message", urlAddr);

                User_NT userNT = new User_NT(SignInActivity.this, urlAddr, "select");
                Object obj = userNT.execute().get();
                users = (ArrayList<User>) obj;
                if (ShareVar.login_fail != 0) {
                    Toast.makeText(SignInActivity.this, "로그인 실패되었습니다.", Toast.LENGTH_SHORT).show();
                    ShareVar.login_fail = 0;
                } else if (etEmail.getText().toString().equals(users.get(0).getEmail()) && etPw.getText().toString().equals(users.get(0).getPw())) {
                    Toast.makeText(SignInActivity.this, "정상적으로 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    SaveSharedPreferences.setPrefIsLogin(SignInActivity.this, "y");
                    SaveSharedPreferences.setLoginMethod(SignInActivity.this, "");
                    SaveSharedPreferences.setPrefEmail(SignInActivity.this, users.get(0).getEmail());
                    SaveSharedPreferences.setPrefPw(SignInActivity.this, users.get(0).getPw());
                    SaveSharedPreferences.setPrefName(SignInActivity.this, users.get(0).getName());
                    SaveSharedPreferences.setPrefPhone(SignInActivity.this, users.get(0).getPhone());

                    if (cb_AutoLogin.isChecked()) {
                        SaveSharedPreferences.setPrefAutoLogin(SignInActivity.this, "y");
                    }


                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, "로그인 실패되었습니다.", Toast.LENGTH_SHORT).show();
                    ShareVar.login_fail = 0;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        btn_common_login.setEnabled(true);
    }

}