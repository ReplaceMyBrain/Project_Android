package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class AdminApproveSelectAd extends AppCompatActivity {

    String stitle, surl, semail, sprice, simage, sadid, urlAddr;

    String adid;

    ImageView imageView;
    TextView tvTitle, tvUrl, tvEmail, tvPrice;
    Button btnStop;
    String pcIp = ShareVar.sUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approve_select_ad);



        Intent intent = getIntent();
        stitle = intent.getStringExtra("title");
        surl = intent.getStringExtra("url");
        semail = intent.getStringExtra("email");
        sprice = intent.getStringExtra("price");
        sadid = intent.getStringExtra("adid");
        simage = intent.getStringExtra("image");

        adid = sadid;
        Log.v("ggg", "adid : " + adid);

        imageView = findViewById(R.id.detail_approve_image);
        tvTitle = findViewById(R.id.detail_approve_title);
        tvUrl = findViewById(R.id.detail_approve_url);
        tvEmail = findViewById(R.id.detail_approve_email);
        tvPrice = findViewById(R.id.detail_approve_price);

        tvTitle.setText(stitle);
        tvUrl.setText(surl);
        tvEmail.setText(semail);
        tvPrice.setText(sprice);

        //이미지 넣는과정
        String imgUrl = ShareVar.sUrl;

        Glide.with(AdminApproveSelectAd.this)
                .load(imgUrl+"adImage/"+ simage)
                .error(R.drawable.ad)
                .into(imageView);

        btnStop = findViewById(R.id.detail_approve_btnStop);
        btnStop.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //승인 jsp만들기 checkdate업데이트 해주기 ------------------------
            urlAddr = pcIp + "adUpdateStopAD.jsp?adid=" + adid;

            Log.v("ggg","유알엘 = " + urlAddr);

            String result = connectUpdatestopData();
            Log.v("ggg","result = " + result);
            if (result.equals("1")){
                Toast.makeText(AdminApproveSelectAd.this, "광고중지 완료.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(AdminApproveSelectAd.this, "광고중지", Toast.LENGTH_SHORT).show();
            }
            finish();  // back버튼과 같은 원리 입력다 하면 메인화면으로
            Log.v("ggg","finish");

        }
    };

    private  String connectUpdatestopData(){
        String result = null;
        try {
            NetworkTaskAdmin networkTask = new NetworkTaskAdmin(AdminApproveSelectAd.this, urlAddr,"adUpdateStopAD");
            Object obj = networkTask.execute().get();
            result = (String)obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}