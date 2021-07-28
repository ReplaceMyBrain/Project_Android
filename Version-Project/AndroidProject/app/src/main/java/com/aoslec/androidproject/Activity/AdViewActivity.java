package com.aoslec.androidproject.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.aoslec.androidproject.NetworkTask.AdPayment_NetworkTask;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.ShareVar;

public class AdViewActivity extends Activity {

    String type, ad_id, payment_id, ad_image, ad_location, ad_title, ad_url, ad_indate, ad_outdate, ad_price;

    WebView wv_image;
    TextView tv_title, tv_url, tv_location, tv_date;
    Button btn_1, btn_left, btn_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_view);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        ad_id = intent.getStringExtra("ad_id");
        payment_id= intent.getStringExtra("payment_id");
        ad_image= intent.getStringExtra("ad_image");
        ad_location = intent.getStringExtra("ad_location");
        ad_title = intent.getStringExtra("ad_title");
        ad_url = intent.getStringExtra("ad_url");
        ad_indate = intent.getStringExtra("ad_indate");
        ad_outdate = intent.getStringExtra("ad_outdate");
        ad_price = intent.getStringExtra("ad_price");

        wv_image = findViewById(R.id.wv_image_ad_view);
        tv_location = findViewById(R.id.tv_location_ad_view);
        tv_title = findViewById(R.id.tv_title_ad_view);
        tv_url = findViewById(R.id.tv_url_ad_view);
        tv_date = findViewById(R.id.tv_date_ad_view);
        btn_1 = findViewById(R.id.btn_1_ad_view);
        btn_left = findViewById(R.id.btn_left_ad_view);
        btn_right = findViewById(R.id.btn_right_ad_view);

        btn_1.setOnClickListener(onClick);
        btn_right.setOnClickListener(onButtonClick);
        btn_left.setOnClickListener(onButtonClick);
        tv_title.setText(ad_title);
        tv_location.setText(ad_location);
        tv_url.setText(ad_url);
        tv_url.setLinksClickable(true);
        wv_image.loadData(htmlData(ad_image), "text/html", "UTF-8");
        tv_date.setText(ad_indate+" ~ "+ad_outdate);

        switch (type) {
            case "now":
                btn_1.setText("광고 중단");
                btn_1.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.GONE);
                btn_left.setVisibility(View.GONE);
                tv_date.setText("D-Day  " + ad_outdate);
                break;
            case "wait":
                btn_left.setText("신청 취소");
                btn_right.setText("수정하기");
                break;
            case "history":
                btn_1.setText("수정 후 재신청");
                btn_1.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.GONE);
                btn_left.setVisibility(View.GONE);
                break;
            case "cancel":
                btn_1.setText("수정 후 재신청");
                btn_1.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.GONE);
                btn_left.setVisibility(View.GONE);
                break;
        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (btn_1.getText().toString()) {
                case "수정 후 재신청" :
                    updateIntentAction();
                    break;
                case "광고 중단" :
//                    Log.v("Message", "dgjakljgl");
                    getIdDialog("정말 해당 광고를 내리시겠습니까?", "고객님의 취소로 인한 잔여 일자는 환불되지 않습니다!", "id_ad");
                    break;
            }
        }
    };
    View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_left_ad_view :
                    getIdDialog("신청 취소", "신청 취소를 원하시면 내리기 버튼을 눌러주세요.", "id_payment");
                    break;
                case R.id.btn_right_ad_view :
                    updateIntentAction();
                    break;
            }
        }
    };

    private String htmlData(String img){

        String image = "<html><head><style type=\"text/css\">\n" +
                "img {\n" +
                " position: absolute;" +
                " top: 0;" +
                " left: 0;" +
                " right: 0;" +
                " bottom: 0;" +
                " max-width: 100%;" +
                " height: auto;\n" +
                "}" +
                "    </style>"+
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />"+
                "</head><body>"+
                "<img src=\""+ ShareVar.sUrl +"adImage/"+ img + "\" width =\"auto\" height=\"100%\">" +
                "</body></html>";
        return image;
    }

    private AlertDialog getIdDialog(String A, String B, String C) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(A);
        builder.setMessage(B);
        builder.setPositiveButton("광고 내리기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                connectUpdateData(C);
                dialog.dismiss();
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

    private void connectUpdateData(String C) {
        String urlAddr;
        if (C.equals(ad_id)) {
            urlAddr = ShareVar.sUrl+"update_ad_payment_outdate.jsp?id_ad="+ad_id;
        }else {
            urlAddr = ShareVar.sUrl+"update_ad_payment_outdate.jsp?id_payment="+payment_id;
        }
        String result = null;
        try {
            AdPayment_NetworkTask adPayment_networkTask = new AdPayment_NetworkTask(AdViewActivity.this, urlAddr, "update");
            Object obj = adPayment_networkTask.execute().get();
            result = (String) obj;
            if (result.equals("1")) {
                Toast.makeText(AdViewActivity.this, "정상적으로 처리되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(AdViewActivity.this, "처리에 실패하였습니다. 네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateIntentAction() {
        Intent intent = new Intent(AdViewActivity.this, AdUpdateActivity.class);
        intent.putExtra("type", "wait");
        intent.putExtra("ad_id", ad_id);
        intent.putExtra("payment_id", payment_id);
        intent.putExtra("ad_image", ad_image);
        intent.putExtra("ad_location", ad_location);
        intent.putExtra("ad_title", ad_title);
        intent.putExtra("ad_url", ad_url);
        intent.putExtra("ad_indate", ad_indate);
        intent.putExtra("ad_outdate", ad_outdate);
        intent.putExtra("ad_price", ad_price);
        startActivity(intent);
    }
}