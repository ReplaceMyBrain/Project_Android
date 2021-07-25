package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.aoslec.androidproject.NetworkTask.NetworkTaskAdmin;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.ShareVar;
import com.bumptech.glide.Glide;

public class AdminSelectAd extends AppCompatActivity {

    String stitle,surl,semail,simage, sprice, urlAddr, sadid;

    String adid;

    ImageView imageView;
    TextView titleView, urlview, emailview, priceview;

    Button btnGo, btnStop;
    String pcIp = ShareVar.sUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_select_ad);




        Intent intent = getIntent();
        stitle = intent.getStringExtra("title");
        surl = intent.getStringExtra("url");
        semail = intent.getStringExtra("email");
        sprice = intent.getStringExtra("price");
        simage = intent.getStringExtra("image");
        sadid = intent.getStringExtra("adid");
        Log.v("ggg","이미지 가져오는 값 : " + simage);

        adid = sadid;   // 아이디 값 가져오기

        imageView = findViewById(R.id.select_ad_image);
        titleView = findViewById(R.id.select_ad_title);
        urlview = findViewById(R.id.select_ad_url);
        emailview = findViewById(R.id.select_ad_email);
        priceview = findViewById(R.id.select_ad_price);

        titleView.setText(stitle);
        urlview.setText(surl);
        emailview.setText(semail);
        priceview.setText(sprice + "원");


        //이미지 넣는과정
        String imgUrl = ShareVar.sUrl;

        Glide.with(AdminSelectAd.this)
                .load(imgUrl+"adImage/"+ simage)
                .error(R.drawable.ad)
                .into(imageView);


        btnGo = findViewById(R.id.btn_adGo);
        btnStop = findViewById(R.id.btn_adStop);

        btnGo.setOnClickListener(onClickListener);
        btnStop.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_adGo:

                    //승인 jsp만들기 checkdate업데이트 해주기 ------------------------
                    urlAddr = pcIp + "adUpdateOk.jsp?adid=" + adid;

                    Log.v("ggg","유알엘 = " + urlAddr);

                    String result = connectUpdateGoData();
                    Log.v("ggg","result = " + result);
                    if (result.equals("1")){
                        Toast.makeText(AdminSelectAd.this, "승인 완료.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AdminSelectAd.this, "승인 실패.", Toast.LENGTH_SHORT).show();
                    }
                    finish();  // back버튼과 같은 원리 입력다 하면 메인화면으로
                    Log.v("ggg","finish");
                    break;

                case R.id.btn_adStop:    //

                    //거절 jsp만들기 checkdate업데이트 해주기 ------------------------
                    urlAddr = pcIp + "adUpdateStop.jsp?adid=" + adid;

                    Log.v("ggg","유알엘 = " + urlAddr);


                    String result2 = connectUpdateStopData();
                    Log.v("ggg","result = " + result2);
                    if (result2.equals("1")){
                        Toast.makeText(AdminSelectAd.this, "승인 완료.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AdminSelectAd.this, "승인 실패.", Toast.LENGTH_SHORT).show();
                    }
                    finish();  // back버튼과 같은 원리 입력다 하면 메인화면으로
                    Log.v("ggg","finish");
                    break;
            }

        }
    };

    private  String connectUpdateGoData(){
        String result = null;
        try {
            NetworkTaskAdmin networkTask = new NetworkTaskAdmin(AdminSelectAd.this, urlAddr,"adUpdateOK");
            Object obj = networkTask.execute().get();
            result = (String)obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private  String connectUpdateStopData(){
        String result = null;
        try {
            NetworkTaskAdmin networkTask = new NetworkTaskAdmin(AdminSelectAd.this, urlAddr,"adUpdateStop");
            Object obj = networkTask.execute().get();
            result = (String)obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}