package com.aoslec.androidproject.Util;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

public class Counter{
    private static CountDownTimer countDownTimer;
    private final static String TAG = "counter";


    public static final int MILLISINFUTURE = 120 * 1000; //총 시간
    public static final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)


    public Counter() {
    }

    public CountDownTimer countDownTimer(TextView counter) {

        countDownTimer= new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL){

            @Override
            public void onTick(long millisUntilFinished) {
                long phoneAuthCount = millisUntilFinished/1000;
                Log.d(TAG, phoneAuthCount + "");

                if ((phoneAuthCount - ((phoneAuthCount / 60) * 60)) >= 10){
                    counter.setText(((phoneAuthCount)/60) + ":" + (phoneAuthCount - ((phoneAuthCount / 60) * 60))) ;
                }else {
                    counter.setText(((phoneAuthCount)/60) + ":0" + (phoneAuthCount - ((phoneAuthCount / 60) * 60)));
                }

            }

            @Override
            public void onFinish() {
                counter.setText("00:00");
            }
        };


        return countDownTimer;

    }
    public static void stopCounter(TextView textView){
        countDownTimer.cancel();
        textView.setText("02:00");
    }
}
