package com.aoslec.androidproject.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.aoslec.androidproject.Activity.IdPwActivity;
import com.aoslec.androidproject.Activity.SignupActivity;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Broadcastreceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void onReceive(Context context, Intent intent) {
        // sms가 오면 onReceive() 가 호출된다. 여기에 처리하는 코드 작성하면 된다.
        // Log.d(TAG, "onReceive() 호출됨.");

        Bundle bundle = intent.getExtras();
        // parseSmsMessage() 메서드의 코드들은 SMS문자의 내용을 뽑아내는 정형화된 코드이다.
        // 복잡해 보일 수 있으나 그냥 그대로 가져다 쓰면 된다.
        SmsMessage[] messages = parseSmsMessage(bundle);

        if(messages.length>0){
            // 문자메세지에서 송신자와 관련된 내용을 뽑아낸다.
            String sender = messages[0].getOriginatingAddress();
            Log.d(TAG, "sender: "+sender);

            // 문자메세지 내용 추출
            String contents = messages[0].getMessageBody().toString();
            Log.d(TAG, "contents: "+contents);

            // 수신 날짜/시간 데이터 추출
            Date receivedDate = new Date(messages[0].getTimestampMillis());
            Log.d(TAG, "received date: "+receivedDate);


            // 해당 내용을 모두 합쳐서 액티비티로 보낸다.
            Log.d(TAG, "received : "+SaveSharedPreferences.getPrefPhone(context)+"/"+sender+"/"+contents.substring(0, 22)+"/"+ShareVar.code_context);
            switch (ShareVar.code_context) {
                case "signup" :
                    sendToActivity(context, contents.substring(22), "signup");
                    break;
                case "find" :
                    sendToActivity(context, contents.substring(29), "find");
                    break;
            }
        }
    }

    private void sendToActivity(Context context, String str, String where){
        Log.d(TAG, "received where: "+where);
        if (where == "find") {
            Intent intent = new Intent(context, IdPwActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("findCode", str);
            context.startActivity(intent);
        } else if (where == "signup") {
            Intent intent = new Intent(context, SignupActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("signupCode", str);
            context.startActivity(intent);
        }

    }

    // 정형화된 코드. 그냥 가져다 쓰면 된다.
    private SmsMessage[] parseSmsMessage(Bundle bundle){
        Object[] objs = (Object[])bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0;i<objs.length;i++){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i], format);
            }
            else{
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
            }
        }
        return messages;
    }
}