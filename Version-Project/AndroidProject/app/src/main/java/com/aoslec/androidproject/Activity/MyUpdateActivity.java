package com.aoslec.androidproject.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;
import com.aoslec.androidproject.Util.GMailSender;
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

public class MyUpdateActivity extends Activity {

    GoogleSignInClient mGoogleSignInClient;

    EditText nowPw, newPw1, newPw2, newname, newphone;

    Button signout, pwChange, namebutton, phonebutton;

    TextView namecontent, nametitle, namehint, phonecontent, phonetitle, phonehint;

    String pinCode, newPhoneNum;

    int count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_update);

        if (SaveSharedPreferences.getLoginMethod(MyUpdateActivity.this).equals("")) {
        } else {
            Toast.makeText(MyUpdateActivity.this, "일반 회원만 정보 변경이 가능합니다.", Toast.LENGTH_SHORT).show();
        }

        nowPw = findViewById(R.id.et_pw_myupdate);
        newPw1 = findViewById(R.id.et_newpw1_myupdate);
        newPw2 = findViewById(R.id.et_newpw2_myupdate);
        newname = findViewById(R.id.et_new_name_myupdate);
        newphone = findViewById(R.id.et_new_phone_myupdate);

        nametitle = findViewById(R.id.tv_title_name_myupdate);
        namecontent = findViewById(R.id.tv_content_name_myupdate);
        namehint = findViewById(R.id.tv_hint_name_myupdate);

        phonetitle = findViewById(R.id.tv_title_phone_myupdate);
        phonecontent = findViewById(R.id.tv_content_phone_myupdate);
        phonehint = findViewById(R.id.tv_hint_phone_myupdate);

        signout = findViewById(R.id.btn_signout_myupdate);
        pwChange = findViewById(R.id.btn_changepw_myupdate);
        namebutton = findViewById(R.id.btn_change_name_myupdate);
        phonebutton = findViewById(R.id.btn_change_phone_myupdate);

        nametitle.setText("설정 이름");
        namebutton.setText("이름 변경하기");
        newname.setVisibility(View.GONE);
        phonebutton.setText("코드 전송하기");
        phonetitle.setText("휴대폰 번호");
        phonehint.setText("번호 변경을 위해서 인증 절차가 필요합니다.");


        namebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog("이름");
            }
        });
        phonebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPhoneNum = newphone.getText().toString().trim();
                getPwWithMobile(newPhoneNum);
            }
        });
        pwChange.setOnClickListener(onClickListener);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.v("","뭐지");
//                AlertDialog.Builder builder = new AlertDialog.Builder(MyUpdateActivity.this);
//                builder.setTitle("정말 탈퇴하시겠습니까?");
//                builder.setMessage("탈퇴하신 아이디는 복구되지 않습니다!");
//                builder.setNegativeButton("탈퇴하기", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
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
                            SaveSharedPreferences.setPrefIsLogin(MyUpdateActivity.this,"n");
                            SaveSharedPreferences.setFirstVisitUser(MyUpdateActivity.this,"y");
                            SaveSharedPreferences.setPrefPhone(MyUpdateActivity.this, "");
                            SaveSharedPreferences.setPrefEmail(MyUpdateActivity.this, "");
                            SaveSharedPreferences.setPrefName(MyUpdateActivity.this, "");
                            SaveSharedPreferences.setPrefImage(MyUpdateActivity.this, "");
                            SaveSharedPreferences.setPrefPw(MyUpdateActivity.this, "");
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
//                    }
//                });
//                builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        namecontent.setText(SaveSharedPreferences.getPrefName(MyUpdateActivity.this));
        phonecontent.setText(SaveSharedPreferences.getPrefPhone(MyUpdateActivity.this));
        namehint.setVisibility(View.GONE);
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
            SaveSharedPreferences.setPrefPw(MyUpdateActivity.this, new1);
            Toast.makeText(MyUpdateActivity.this, "비밀번호가 변경 되었습니다.", Toast.LENGTH_SHORT).show();
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

    private AlertDialog makeDialog(String title) {
        final EditText et = new EditText(MyUpdateActivity.this);

        if (title.equals("이름")) {
            et.setHint("변경하실 내용을 입력해주세요!");
            AlertDialog.Builder builder = new AlertDialog.Builder(MyUpdateActivity.this);
            builder.setTitle(title+" 변경");
            builder.setMessage(title+"을 변경하시겠습니까?");
            builder.setView(et);
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String value = et.getText().toString();
                    updateUserData(title, value);
                    dialog.dismiss();
                }
            });
            builder.setNeutralButton("아니요", new DialogInterface.OnClickListener() {
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
        }else {
            et.setHint("코드를 입력해주세요!");
            AlertDialog.Builder builder = new AlertDialog.Builder(MyUpdateActivity.this);
            builder.setTitle("휴대폰 번호 변경");
            builder.setMessage(title);
            builder.setView(et);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String value = et.getText().toString().trim();
                    if (pinCode.equals(value)) {
                        updateUserData("휴대폰번호", newPhoneNum);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(MyUpdateActivity.this, "코드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("다시 전송받기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getPwWithMobile(newphone.getText().toString().trim());
                }
            });
            builder.setNeutralButton("아니요", new DialogInterface.OnClickListener() {
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
//        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        et.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }
    private void updateUserData(String title, String value) {
        String key = "";
        switch (title) {
            case "이름" :
                title = "name";
                break;
            case "휴대폰번호" :
                title = "phone";
                break;
        }
        String urlAddr = ShareVar.sUrl + "update_user.jsp?"+"email="+ SaveSharedPreferences.getPrefEmail(MyUpdateActivity.this)+"&"+title+"="+value;

        Log.v("Message",urlAddr);
        String result = null;
        try {
            User_NT networkTask = new User_NT(MyUpdateActivity.this, urlAddr, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(result.equals("1")) {
            Toast.makeText(MyUpdateActivity.this, value+"로 수정 되었습니다.", Toast.LENGTH_SHORT).show();
            switch (title) {
                case "이름" :
                    SaveSharedPreferences.setPrefName(MyUpdateActivity.this, value);
                    break;
                case "휴대폰 번호" :
                    SaveSharedPreferences.setPrefPhone(MyUpdateActivity.this, value);
                    break;
            }
        }else {
            Toast.makeText(MyUpdateActivity.this, "수정 실패되었습니다.", Toast.LENGTH_SHORT).show();
        }
        onResume();
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
        count ++;
        ShareVar.code_context = "find";
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(MyUpdateActivity.this, "전송 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(MyUpdateActivity.this, "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(MyUpdateActivity.this, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(MyUpdateActivity.this, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(MyUpdateActivity.this, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(MyUpdateActivity.this, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(MyUpdateActivity.this, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));
        GMailSender gMailSender = new GMailSender("tunaweather@gmail.com", "tkwhckacl000");
        //인증코드
        pinCode=gMailSender.getEmailCode();
        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(phoneNum, null, "Weather Find Password Code : "+pinCode, sentIntent, deliveredIntent);
        if (count == 1) {
            makeDialog("코드를 입력하세요!");
        }
    }

}