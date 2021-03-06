package com.aoslec.androidproject.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.androidproject.Bean.User;
import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.ShareVar;
import com.aoslec.androidproject.Util.Counter;
import com.aoslec.androidproject.Util.GMailSender;
import com.aoslec.androidproject.Util.MailTread;

import java.util.ArrayList;

public class IdPwActivity extends Activity implements View.OnClickListener {

    String urlAddr;
    int findpwcheck = 1;
    ArrayList<User> users;

    SharedPreferences userIdShared;
    SharedPreferences.Editor editor;

    private final static String TAG = "FindActivity";
    Context mContext;
    Counter mobileCounter = new Counter();

    String pinCode;

    TextView findAccountTextView;
    TextView findPwTextView;
    TextView findIdMobileCounter;
    TextView findpw_email_phone_check;
    TextView findpw_pin_check;

    EditText mobile;
    EditText findpw_phone_pin;
    EditText findpw_phone_edittext;
    EditText findpw_id_phone_edittext;

    Button checkMobileButton;
    Button findpw_input_button;
    Button findpw_pin_button;
    
    RadioGroup pwFindRadios;
    RadioButton withEmail;
    RadioButton withMobile;
    LinearLayout findPwWithMobile, findpw_email_layout;

    ImageView pwUnderBar;
    ImageView accountUnderBar;

    LinearLayout findAccount;
    LinearLayout findPw;

    /* phonepwcheck?????? userId??? ??????????????? ????????????
       ?????????????????? ????????? ?????? ???????????????????????? ??????
    */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_idpw);
        mContext = this;

        findAccount = findViewById(R.id.findIdContainer);
        findPw = findViewById(R.id.findPwContainer);

        pwUnderBar = findViewById(R.id.myUnderImg3);
        accountUnderBar = findViewById(R.id.myUnderImg2);

        pwFindRadios = findViewById(R.id.pwFindRadioGroup);
        withMobile = findViewById(R.id.findpw_phone_radiobutton);
        withEmail = findViewById(R.id.findpw_emill_radiobutton);

        findIdMobileCounter = findViewById(R.id.findIdMobileCount);
        findPwWithMobile = findViewById(R.id.findpw_phone_layout);
//        findpw_email_layout = findViewById(R.id.findpw_email_layout);


        //find_email_page
        mobile = findViewById(R.id.findid_phone_edittext);
        checkMobileButton = findViewById(R.id.findid_phonebutton);

        //find_pw_page
        findpw_input_button = findViewById(R.id.findpw_phone_button);
        findpw_pin_button = findViewById(R.id.findpw_pin_button);

        findpw_phone_pin = findViewById(R.id.findpw_phone_pin);
        findpw_phone_edittext = findViewById(R.id.findpw_phone_edittext);
        findpw_id_phone_edittext = findViewById(R.id.findpw_id_phone_edittext);
        findpw_email_phone_check = findViewById(R.id.findpw_email_phone_check);
        findpw_pin_check = findViewById(R.id.findpw_pin_check);

        withEmail.setChecked(true);

//        backImageButton = findViewById(R.id.backImageButton);
//        backImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent logInIntent = new Intent(IdPwActivity.this, SignInActivity.class);
//                logInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(logInIntent);
//            }
//        });

        findAccountTextView = findViewById(R.id.findAccountTextView);
        findAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTab(0);
            }
        });


        checkMobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * @Method Name : onClick
                 * @????????? : 2021/06/20
                 * @????????? : biso
                 * @Method ?????? : ???????????? ????????? ??????
                 * @???????????? :
                 * @Param&return : [v] & void
                 */
                checkMobileButton.setEnabled(false);
                String checkMobiletext = String.valueOf(mobile.getText()).trim();
                ShareVar.login_fail = 0;
                if (checkMobiletext.equals("") || checkMobiletext == null) {
                    Toast.makeText(getApplicationContext(), "????????? ????????? ?????????", Toast.LENGTH_SHORT).show();
                    mobile.requestFocus();
                }else {
                    try {
                        urlAddr = ShareVar.sUrl + "select_find_user_where_phone.jsp?phone=" + checkMobiletext;
                        Log.v(TAG, urlAddr);
                        User_NT userNT = new User_NT(IdPwActivity.this, urlAddr, "select");
                        Object obj = userNT.execute().get();
                        users = (ArrayList<User>) obj;
//                            Toast.makeText(IdPwActivity.this, users.get(0).getPhone(), Toast.LENGTH_SHORT).show();
                        if (ShareVar.login_fail != 0) {
                            Toast.makeText(IdPwActivity.this, "?????? ????????? ????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                            ShareVar.login_fail = 0;
                        } else if (checkMobiletext.equals(users.get(0).getPhone())) {
                            getIdDialog("?????? ?????? ??????",users.get(0).getEmail());
//                            AlertDialog.Builder dialog = new AlertDialog.Builder(IdPwActivity.this);
//                            dialog.setTitle("").setMessage().show();

                        } else {
                            Toast.makeText(IdPwActivity.this, "?????? ????????? ????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                            ShareVar.login_fail = 0;
                        }
                    }catch (Exception e){
                        Toast.makeText(IdPwActivity.this, "?????? ????????? ????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                checkMobileButton.setEnabled(true);
            }
        });

        findPwTextView = findViewById(R.id.findPwTextView);
        findPwTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTab(1);
            }
        });

        withMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findpw_email_layout.setVisibility(View.VISIBLE);
                findPwWithMobile.setVisibility(View.VISIBLE);
                findpwcheck = 0;

            }
        });
        withEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findpw_email_layout.setVisibility(View.VISIBLE);
                findPwWithMobile.setVisibility(View.GONE);
                findpwcheck = 1;
            }
        });

        findpw_input_button.setOnClickListener(this);
        findpw_pin_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String checkedEmail = findpw_id_phone_edittext.getText().toString().trim();
        int check;
        switch (v.getId()){
            case R.id.findpw_phone_edittext:
                break;
            case  R.id.findpw_pin_button:
                findpw_pin_check.setVisibility(View.INVISIBLE);
                Log.d(TAG,"???????????? : " + pinCode );
                String userCode = findpw_phone_pin.getText().toString();
                Log.d(TAG, "???????????? : " + userCode);
                if (userCode.equals(pinCode)){
                    Intent intent = new Intent(IdPwActivity.this, SetNewPwActivity.class);
                    intent.putExtra("email", findpw_id_phone_edittext.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }else {
                    Log.d(TAG,"???????????? ?????????"+ userCode + ":" + pinCode);
                    findpw_pin_check.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.findpw_phone_button:
                    Log.d(TAG, "email ?????? " + checkedEmail);
                    checkExistEmail(checkedEmail,findpw_email_phone_check);
                    break;
        }

    }



    private void checkExistEmail(String checkedEmail, TextView errorMsg) {/* findType 0??? ??????????????? 1??? ????????? ??????*/
        errorMsg.setVisibility(View.INVISIBLE);
        Log.d(TAG, "CHECKEEXISTEMAIL : ????????? ?????? ??????" );
        ShareVar.login_fail = 0;
        if (checkedEmail.equals("") || checkedEmail == null) {
            Toast.makeText(getApplicationContext(), "???????????? ????????? ?????????", Toast.LENGTH_SHORT).show();
            findpw_id_phone_edittext.requestFocus();
        }else if(findpw_phone_edittext.getText().toString().trim().equals("") || findpw_phone_edittext.getText().toString().trim() == null) {
            Toast.makeText(getApplicationContext(), "????????? ????????? ?????????", Toast.LENGTH_SHORT).show();
            findpw_phone_edittext.requestFocus();
        }else {
            try {
                urlAddr = ShareVar.sUrl + "select_find_user_where_email.jsp?email=" + checkedEmail;
                Log.v(TAG, urlAddr);
                User_NT userNT = new User_NT(IdPwActivity.this, urlAddr, "select");
                Object obj = userNT.execute().get();
                users = (ArrayList<User>) obj;
                Log.d(TAG,checkedEmail + users.get(0).getEmail());
                if (ShareVar.login_fail != 0) {
                    Toast.makeText(IdPwActivity.this, "????????? ???????????? ?????? ??????????????????!", Toast.LENGTH_SHORT).show();
                    errorMsg.setVisibility(View.VISIBLE);
                    ShareVar.login_fail = 0;
                } else if (findpw_phone_edittext.getText().toString().trim().equals(users.get(0).getPhone())) {
                    if (findpw_phone_edittext.getText().toString().trim().equals(users.get(0).getPhone())) {
                        String number = findpw_phone_edittext.getText().toString().trim();
                        String phoneNum = "+82 " + number.substring(1);
                        Log.d(TAG, "???????????? ?????? ????????? ?????? ?????? : " + phoneNum);
                        getPwWithMobile(phoneNum);
                        MailTread mailTread = new MailTread(checkedEmail, IdPwActivity.this);
                        mailTread.start();
                        pinCode = mailTread.pinCodeReturn();
                    } else {
                        Toast.makeText(IdPwActivity.this, "????????? ??????????????? ?????? ??????????????????!", Toast.LENGTH_SHORT).show();
                    }
//                    Log.d(TAG,checkedEmail + "??????, ?????? ??????");
//                    //?????????????????? ????????? ?????? ?????? -> ?????? ???????????? ?????? ???????????? ???????????? ?????? ??????
//                    userIdShared = getSharedPreferences("FindAccountUserId", Context.MODE_PRIVATE);
//                    editor  = userIdShared.edit();
//                    editor.remove("userId");
//                    editor.commit();
//                    editor.putString("userId", checkedEmail);
//                    Log.d(TAG, checkedEmail+"?????? ???????????? ??????");
//                    editor.commit();
//                    Log.d(TAG, "?????? ????????? ??????" + userIdShared.getString("userId",null));
//                    if (findpwcheck == 0) {
//
//                    } else {
//                        Toast.makeText(IdPwActivity.this, "????????? ???????????? ?????? ??????????????????!", Toast.LENGTH_SHORT).show();
//                    }
//                    if (findpwcheck == 1){
//                        MailTread mailTread = new MailTread(checkedEmail, IdPwActivity.this);
//                        mailTread.start();
//                        pinCode = mailTread.pinCodeReturn();
//                        //????????? ???????????? ????????? ??????
//                        if (ShareVar.login_fail == 0) {
////                            mobileCounter.stopCounter(findIdMobileCounter);
////                            mobileCounter.countDownTimer(findIdMobileCounter);
////                            mobileCounter.countDownTimer(findIdMobileCounter).start();
////                            findpw_phone_pin.setEnabled(true);
////                            findpw_pin_button.setEnabled(true);
////                            mobileCounter.countDownTimer(findIdMobileCounter).start();
//                        }else {
//                            Toast.makeText(IdPwActivity.this, "????????? ???????????? ?????? ??????????????????!", Toast.LENGTH_SHORT).show();
////                            errorMsg.setVisibility(View.VISIBLE);
//                            ShareVar.login_fail = 0;
//                        }
//                    }
//                } else {
//                    Toast.makeText(IdPwActivity.this, "????????? ???????????? ?????? ??????????????????!", Toast.LENGTH_SHORT).show();
//                    errorMsg.setVisibility(View.VISIBLE);
//                    ShareVar.login_fail = 0;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    private AlertDialog getIdDialog(String title, String userId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(userId);
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("???????????? ??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTab(1);
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

    public void moveTab(int page) {
        if (page == 0) {
            findPw.setVisibility(View.INVISIBLE);
            pwUnderBar.setVisibility(View.INVISIBLE);
            findAccount.setVisibility(View.VISIBLE);
            accountUnderBar.setVisibility(View.VISIBLE);
        } else if (page == 1){
            findAccount.setVisibility(View.INVISIBLE);
            accountUnderBar.setVisibility(View.INVISIBLE);
            findPw.setVisibility(View.VISIBLE);
            pwUnderBar.setVisibility(View.VISIBLE);
        }
        if (findpw_pin_button.isEnabled()) {
            mobileCounter.stopCounter(findIdMobileCounter);
        }
    }


    private void showToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    private void getPwWithMobile(String phoneNum) {
        /**
         * @Method Name : getPwWithMobile
         * @????????? : 2021/06/21
         * @????????? : biso
         * @Method ?????? : ??????????????? Pincode??? ????????????.
         * @???????????? :
         * @Param&return : [phoneNum] & void
         */
        ShareVar.code_context = "find";
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(mContext, "?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(mContext, "?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(mContext, "????????? ????????? ????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(mContext, "??????(Radio)??? ??????????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(mContext, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(mContext, "SMS ?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(mContext, "SMS ?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));
        GMailSender gMailSender = new GMailSender("tunaweather@gmail.com", "tkwhckacl000");
        //????????????
        pinCode=gMailSender.getEmailCode();
        Log.d(TAG, "pin code : " + pinCode);
        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(phoneNum, null, "Weather Find Password Code : "+pinCode, sentIntent, deliveredIntent);
        findpw_phone_pin.setEnabled(true);
        findpw_pin_button.setEnabled(true);
        mobileCounter.stopCounter(findIdMobileCounter);
        mobileCounter.countDownTimer(findIdMobileCounter);
        mobileCounter.countDownTimer(findIdMobileCounter).start();
    }

    /** ?????? ?????? **/
    private void processIntent(Intent intent){
        if(intent != null){
            // ??????????????? ????????? ???????????? ????????????, ????????????.(???????????? edittext??? ????????? ????????? ????????? ???????????????.)
            String string = intent.getStringExtra("findCode");
            findpw_phone_pin.setText(string);
        }
    }
    // (2) ?????? ????????? ??????????????? ???????????? ?????? ?????? ?????? ??????
    // (????????? ?????? onCreate()??? ????????? ?????? ????????? ????????? ??????????????? ???????????? ?????? ????????? SMS????????? ????????????!
    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }


}
