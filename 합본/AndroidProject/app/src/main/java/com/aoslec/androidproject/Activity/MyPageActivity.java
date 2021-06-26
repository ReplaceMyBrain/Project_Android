package com.aoslec.androidproject.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.aoslec.androidproject.AdActivity.AdPaymentActicity;
import com.aoslec.androidproject.Bean.Ad_PaymentBean;
import com.aoslec.androidproject.Bean.User;
import com.aoslec.androidproject.NetworkTask.AdPayment_NetworkTask;
import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.util.ArrayList;

public class MyPageActivity extends AppCompatActivity {

    ViewPager viewPager;
    GoogleSignInClient mGoogleSignInClient;
    ArrayList<User> users;
    LinearLayout layout_now, layout_wait, layout_history, layout_cancel;
    ImageView iv_profile;
    WebView wv_profile;
    TextView tv_name, tv_logout, linear2_name, linear2_email, linear2_phone, tv_link,
            tv_wait, tv_now, tv_history, tv_cancel;

    String urlAddr = null;
    int tabindex;
    ArrayList<Ad_PaymentBean> ad_paymentBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        iv_profile = findViewById(R.id.iv_icon_maypage);
        tv_name = findViewById(R.id.tv_name_mypage);
        linear2_name = findViewById(R.id.linear2_tv1);
        linear2_email = findViewById(R.id.linear2_tv2);
        linear2_phone = findViewById(R.id.linear2_tv3);
        tv_link = findViewById(R.id.linear2_tv_link);
        tv_now = findViewById(R.id.tv_count_now_mypage);
        tv_wait = findViewById(R.id.tv_count_wait_mypage);
        tv_cancel =  findViewById(R.id.tv_count_cancel_mypage);
        tv_history = findViewById(R.id.tv_count_history_mypage);
        layout_cancel = findViewById(R.id.ll_cancel_mypage);
        layout_history = findViewById(R.id.ll_history_mypage);
        layout_now = findViewById(R.id.ll_now_mypage);
        layout_wait = findViewById(R.id.ll_wait_mypage);

        tv_logout = findViewById(R.id.tv_logout_mypage);

        tv_name.setText(SaveSharedPreferences.getPrefName(MyPageActivity.this));
        linear2_name.setText(SaveSharedPreferences.getPrefName(MyPageActivity.this));
        linear2_email.setText(SaveSharedPreferences.getPrefEmail(MyPageActivity.this));
        linear2_phone.setText(SaveSharedPreferences.getPrefPhone(MyPageActivity.this));

        ad_countAction();

        //profile 이미지를 동그랗게
        iv_profile.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            iv_profile.setClipToOutline(true);
        if (SaveSharedPreferences.getPrefImage(MyPageActivity.this) != null || SaveSharedPreferences.getPrefImage(MyPageActivity.this) != "")
            Glide.with(this).load(SaveSharedPreferences.getPrefImage(MyPageActivity.this)).into(iv_profile);

        tv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog(MyPageActivity.this);
            }
        });
        tv_logout.setOnClickListener(logoutAction);

        layout_wait.setOnClickListener(layoutClickAction);
        layout_now.setOnClickListener(layoutClickAction);
        layout_history.setOnClickListener(layoutClickAction);
        layout_cancel.setOnClickListener(layoutClickAction);

    }

    View.OnClickListener layoutClickAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_now_mypage:
                    tabindex = 0;
                    break;
                case R.id.ll_wait_mypage:
                    tabindex = 1;
                    break;
                case R.id.ll_history_mypage:
                    tabindex = 2;
                    break;
                case R.id.ll_cancel_mypage:
                    tabindex = 3;
                    break;
            }
            Intent intent = new Intent(MyPageActivity.this, AdPaymentActicity.class);
            intent.putExtra("tabindex", tabindex);
            startActivity(intent);
        }
    };

    //액션바 타이틀 변경 메소드
    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    View.OnClickListener logoutAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (SaveSharedPreferences.getLoginMethod(MyPageActivity.this)) {
                case "Kakao" :
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            SaveSharedPreferences.setPrefIsLogin(MyPageActivity.this, "n");
                            SaveSharedPreferences.setPrefAutoLogin(MyPageActivity.this, "n");
                            Intent intent = new Intent(MyPageActivity.this, SignInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
//                            finish();
                        }
                    });
                    break;
                case "Google" :
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail() // email addresses도 요청함
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(MyPageActivity.this, gso);
                    GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(MyPageActivity.this);
                    mGoogleSignInClient.signOut();
                    SaveSharedPreferences.setPrefIsLogin(MyPageActivity.this, "n");
                    SaveSharedPreferences.setPrefAutoLogin(MyPageActivity.this, "n");
//                    finish();
                    break;
                case "" :
                    SaveSharedPreferences.setPrefIsLogin(MyPageActivity.this, "n");
                    SaveSharedPreferences.setPrefAutoLogin(MyPageActivity.this, "n");
                    Intent intent = new Intent(MyPageActivity.this, SignInActivity.class);
                    startActivity(intent);
//                    finish();
                    break;
                default:
                    break;
            }
            Intent intent = new Intent(MyPageActivity.this, SignInActivity.class);
            startActivity(intent);
        }
    };
    private AlertDialog getDialog(Context context) {
        final EditText et = new EditText(context);
        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("정보 수정 페이지")
                .setMessage("비밀번호를 입력하세요!\n"+SaveSharedPreferences.getPrefEmail(context))
                .setView(et)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = et.getText().toString();
                        if (value.equals(SaveSharedPreferences.getPrefPw(context))) {
                            Intent intent = new Intent(context, MyUpdateActivity.class);
                            startActivity(intent);
//                            MyPageActivity.getContext.finish();
                            dialog.dismiss();
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Error!")
                                    .setMessage("비밀번호가 틀렸습니다.")
                                    .setNegativeButton("닫기", null)
                                    .show();
                        }
                    }
                });
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.show();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON1).setTextColor(Color.BLACK);
                alertDialog.getButton(AlertDialog.BUTTON3).setTextColor(Color.rgb(245, 127, 23));
            }
        });

        return alertDialog;
    }
    private void countAction() {
        try {
            urlAddr = ShareVar.sUrl + "select_user_ad_payment_count.jsp?email=" + SaveSharedPreferences.getPrefEmail(MyPageActivity.this);
            Log.v("Message", urlAddr);

            User_NT userNT = new User_NT(MyPageActivity.this, urlAddr, "select");
            Object obj = userNT.execute().get();
            users = (ArrayList<User>) obj;
//                Toast.makeText(SignInActivity.this, "정상적으로 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
//                SaveSharedPreferences.setPrefIsLogin(SignInActivity.this, "y");
//                SaveSharedPreferences.setPrefAutoLogin(SignInActivity.this, "y");
//                SaveSharedPreferences.setLoginMethod(SignInActivity.this, "");
//                SaveSharedPreferences.setPrefEmail(SignInActivity.this, users.get(0).getEmail());
//                SaveSharedPreferences.setPrefPw(SignInActivity.this, users.get(0).getPw());
//                SaveSharedPreferences.setPrefName(SignInActivity.this, users.get(0).getName());
//                SaveSharedPreferences.setPrefPhone(SignInActivity.this, users.get(0).getPhone());
//                SaveSharedPreferences.setPrefImage(SignInActivity.this, users.get(0).getImage());
//                SaveSharedPreferences.setPrefIndate(SignInActivity.this, users.get(0).getIndate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void ad_countAction () {
        String urlAddr = ShareVar.sUrl + "select_ad_count.jsp?email=" + SaveSharedPreferences.getPrefEmail(MyPageActivity.this);
        Log.v("Message", urlAddr);
        try {
            AdPayment_NetworkTask adPayment_networkTask = new AdPayment_NetworkTask(MyPageActivity.this, urlAddr, "select_count");
            Object obj = adPayment_networkTask.execute().get();
            ad_paymentBeans = (ArrayList<Ad_PaymentBean>) obj;
            Log.v("MessageCount", ad_paymentBeans.get(0).getAd_count()+"/"+ad_paymentBeans.get(1).getAd_count()+"/"+ad_paymentBeans.get(2).getAd_count()+"/"+ad_paymentBeans.get(3).getAd_count());

            tv_now.setText(ad_paymentBeans.get(0).getAd_count());
            tv_wait.setText(ad_paymentBeans.get(1).getAd_count());
            tv_history.setText(ad_paymentBeans.get(2).getAd_count());
            tv_cancel.setText(ad_paymentBeans.get(3).getAd_count());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }





}
