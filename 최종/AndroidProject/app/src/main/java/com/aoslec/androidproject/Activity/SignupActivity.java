package com.aoslec.androidproject.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.ShareVar;
import com.aoslec.androidproject.Util.Counter;
import com.aoslec.androidproject.Util.GMailSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    String urlAddr, pinCode;

    Counter mobileCounter = new Counter();

    EditText et_email_signup, et_pw_signup, et_pwcheck_signup,
            et_name_signup, et_phone_signup, et_code_signup;
    Button btn_ok_signup, btn_code_signup, btn_pin_ok_signup;
    TextView codeTimer, tv_pin_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        codeTimer = findViewById(R.id.codecount_signup);
        et_code_signup = findViewById(R.id.et_code_signup);
        et_phone_signup = findViewById(R.id.et_phone_signup);
        et_email_signup = findViewById(R.id.et_Email_signup);
        et_pw_signup = findViewById(R.id.et_Pw_signup);
        et_pwcheck_signup = findViewById(R.id.et_PwCheck_signup);
        et_name_signup = findViewById(R.id.et_name_signup);
        btn_code_signup = findViewById(R.id.btn_pin_signup);
        btn_ok_signup = findViewById(R.id.btn_ok_signup);
        tv_pin_signup = findViewById(R.id.tv_pin_signup);
        btn_pin_ok_signup = findViewById(R.id.btn_pin_ok_signup);

        btn_ok_signup.setOnClickListener(signupAction);
        btn_pin_ok_signup.setOnClickListener(codeAction);
        btn_code_signup.setOnClickListener(codeAction);

        et_phone_signup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                et_code_signup.setText("");
            }
        });
    }
    View.OnClickListener codeAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_pin_signup :

                    String number = et_phone_signup.getText().toString().trim();
                    Log.v("Message", number);
                    String phoneNum = "+82 " + number.substring(1);
                    getPwWithMobile(phoneNum);
                    break;
                case R.id.btn_pin_ok_signup :
                    if (codeTimer.getText() == "00:00"){
                        Toast.makeText(SignupActivity.this, "인증 시간이 초과되었습니다.",Toast.LENGTH_SHORT).show();
                    } else {
                        tv_pin_signup.setVisibility(View.INVISIBLE);
                        btn_ok_signup.setVisibility(View.INVISIBLE);
                        btn_ok_signup.setEnabled(false);
                        String userCode = et_code_signup.getText().toString();
                        if (!userCode.equals(pinCode)){
                            tv_pin_signup.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(SignupActivity.this, "코드가 일치합니다.",Toast.LENGTH_SHORT).show();
                            btn_ok_signup.setVisibility(View.VISIBLE);
                            btn_ok_signup.setEnabled(true);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    View.OnClickListener signupAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (existCheck("이메일") == true) {
                if (existCheck("비밀번호") == true) {
                    if (existCheck("이름") == true) {
                        if (existCheck("번호") == true) {
                            if (ruleCheck("이메일") == true) {
                                if (ruleCheck("비밀번호") == true) {
//                                final ProgressDialog dialog = new ProgressDialog(this);
//                                dialog.setMessage("회원가입이 진행중입니다");
//                                dialog.show();
//                                Util.hideKeyboard(Registration.this);
                                    btn_ok_signup.setText("가입중");
                                    btn_ok_signup.setEnabled(false);
                                    String strEmail = et_email_signup.getText().toString().trim();
                                    String strPW = et_pw_signup.getText().toString().trim();
                                    String strName = et_name_signup.getText().toString().trim();
                                    String strPhone = et_phone_signup.getText().toString().trim();
                                    String strImage = "null";

                                    urlAddr = ShareVar.sUrl + "insert_user.jsp?email=" + strEmail + "&pw=" + strPW + "&name=" + strName + "&phone=" + strPhone;
                                    Log.v("Message", urlAddr);
                                    String result = connectInsertData();
                                    if (result.equals("1")) {
                                        Toast.makeText(SignupActivity.this, "정상적으로 가입 되었습니다.", Toast.LENGTH_SHORT).show();
                                        finish(); //메인화면으로 이동
                                    } else {
                                        Toast.makeText(SignupActivity.this, "가입 실패되었습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                    /** firebase DB
                                     DbManager dbManager = new DbManager(SignupActivity.this);
                                     int returnint = dbManager.writeNewUser(strName,strID, strPW,strImage);
                                     if (returnint == 0) {
                                     Intent intent = new Intent(SignupActivity.this, SignInActivity.class);
                                     startActivity(intent);
                                     */
//                                }
                                }
                            }
                        }
                    }
                }
            }

        }
    };

    private boolean existCheck(String check) {
        switch (check) {
            case "이메일" :
                if (et_email_signup.length() == 0) {
                et_email_signup.requestFocus();
                    Toast.makeText(SignupActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    return true;
                }
            case "비밀번호" :
                if (7 >= et_pw_signup.length()) {
                    et_pw_signup.requestFocus();
                    Toast.makeText(SignupActivity.this, "비밀번호는 8자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    if (7 >= et_pwcheck_signup.length()) {
                        et_pwcheck_signup.requestFocus();
                        Toast.makeText(SignupActivity.this, "비밀번호는 8자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return false;
                    }else {
                        if (!et_pw_signup.getText().toString().equals(et_pwcheck_signup.getText().toString())) {
                            et_pwcheck_signup.requestFocus();
                            Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            case "이름" :
                if (1 >= et_name_signup.length()) {
                    et_name_signup.requestFocus();
                    Toast.makeText(SignupActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    return true;
                }
            case "번호" :
                if (10 >= et_phone_signup.length() || !et_code_signup.getText().toString().equals(pinCode)) {

                    et_phone_signup.requestFocus();
                    btn_ok_signup.setEnabled(false);
                    Toast.makeText(SignupActivity.this, "번호를 다시 인증해주세요", Toast.LENGTH_SHORT).show();
                    et_code_signup.setText("");
                    return false;
                }else {
                    return true;
                }
        }
        return false;
    };
    private boolean ruleCheck(String check) {
        Pattern pattern;
        Matcher matcher;
        switch (check) {
            case "이메일" :
                pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$");
                matcher = pattern.matcher(et_email_signup.getText().toString());
                if(matcher.find()){
                    //이메일 형식에 맞을 때
                    return true;
                }else{
                    //이메일 형식에 맞지 않을 때
                    Toast.makeText(SignupActivity.this, "이메일을 형식에 맞춰 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            case "비밀번호" :
                //숫자, 문자, 특수문자 중 2가지 포함(8~15자)
                pattern = Pattern.compile("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$");
                matcher = pattern.matcher(et_pw_signup.getText().toString());
                if(matcher.find()){
                    //이메일 형식에 맞을 때
                    return true;
                }else{
                    //이메일 형식에 맞지 않을 때
                    Toast.makeText(SignupActivity.this, "비밀번호는 숫자, 문자, 특수문자 중 2가지 포함(8~15자)으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            default:
                break;
        }
        return false;
    };

    private String connectInsertData() {
        String result = null;
        try {
            User_NT userNT = new User_NT(SignupActivity.this, urlAddr, "insert");
            Object obj = userNT.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    private void getPwWithMobile(String phoneNum) {
        /**
         * @Method Name : getPwWithMobile
         * @작성일 : 2021/06/21
         * @작성자 : biso
         * @Method 설명 : 전화번호로 Pincode를 전송한다.
         * @변경이력 :
         * @Param&return : [phoneNum] & void
         */
        mobileCounter.countDownTimer(codeTimer);
        mobileCounter.stopCounter(codeTimer);
        ShareVar.code_context = "signup";
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(SignupActivity.this, "전송 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(SignupActivity.this, "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(SignupActivity.this, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(SignupActivity.this, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(SignupActivity.this, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(SignupActivity.this, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(SignupActivity.this, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));
        GMailSender gMailSender = new GMailSender("tunaweather@gmail.com", "tkwhckacl000");
        //인증코드
        pinCode=gMailSender.getEmailCode();
        Log.d("Message", "pin code : " + pinCode);
        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(phoneNum, null, "Weather Signup Code : "+pinCode, sentIntent, deliveredIntent);
        et_code_signup.setVisibility(View.VISIBLE);
        codeTimer.setVisibility(View.VISIBLE);
        btn_pin_ok_signup.setVisibility(View.VISIBLE);
        mobileCounter.countDownTimer(codeTimer).start();
    }
    /** 얘가 중요 **/
    private void processIntent(Intent intent){
        if(intent != null){
            // 인텐트에서 전달된 데이터를 추출하여, 활용한다.(여기서는 edittext를 통하여 내용을 화면에 뿌려주었다.)
            String string = intent.getStringExtra("signupCode");
            et_code_signup.setText(string);
        }
    }
    // (2) 이미 실행된 상태였는데 리시버에 의해 다시 켜진 경우
    // (이러한 경우 onCreate()를 거치지 않기 때문에 이렇게 오버라이드 해주어야 모든 경우에 SMS문자가 처리된다!
    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }
}