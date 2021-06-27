package com.aoslec.androidproject.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.aoslec.androidproject.NetworkTask.ImageNetworkTask;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.ShareVar;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdUpdateActivity extends Activity {

    String type, ad_id, payment_id, ad_image, ad_location, ad_title, ad_url, ad_indate, ad_outdate, ad_price, new_price;
    Date newDate;
    SimpleDateFormat format1, format2, format3;

    String urlAddr, imageName, simg;

    WebView wv_image;
    RadioGroup radioGroup;
    RadioButton rbtn1, rbtn2, rbtn3;
    ImageView img;
    EditText etTitle, etUrl, etLocation;
    TextView tvTemp;
    Button btnRegister, btn_indate;

    String devicePath = Environment.getDataDirectory().getAbsolutePath() + "/data/com.aoslec.contactproject/";
    private final int REQ_CODE_SELECT_IMAGE = 300; // Gallery Return Code
    private String img_path = null; // 최종 file name
    private String f_ext = null;    // 최종 file extension
    File tempSelectFile;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_update);

        urlAddr = ShareVar.sUrl+ "adInsert.jsp?";
        getHashKey();

        Intent intent = getIntent();
        ad_id = intent.getStringExtra("ad_id");
        payment_id= intent.getStringExtra("payment_id");
        ad_image= intent.getStringExtra("ad_image");
        ad_location = intent.getStringExtra("ad_location");
        ad_title = intent.getStringExtra("ad_title");
        ad_url = intent.getStringExtra("ad_url");
        ad_indate = intent.getStringExtra("ad_indate");
        ad_outdate = intent.getStringExtra("ad_outdate");
        ad_price = intent.getStringExtra("ad_price");

        wv_image = findViewById(R.id.register_wv_update);
        img = findViewById(R.id.register_iv_update);
        etLocation = findViewById(R.id.register_etLocation_update);
        radioGroup = findViewById(R.id.register_radio_update);
        etTitle = findViewById(R.id.register_etTitle_update);
        etUrl = findViewById(R.id.register_etUrl_update);
        btnRegister = findViewById(R.id.register_btnRegister_update);
        rbtn1 = findViewById(R.id.register_rbtn1_update);
        rbtn2 = findViewById(R.id.register_rbtn2_update);
        rbtn3 = findViewById(R.id.register_rbtn3_update);

        ActivityCompat.requestPermissions(AdUpdateActivity.this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        btn_indate = findViewById(R.id.register_btn_ad_indate);
        btn_indate.setText(ad_indate);
        img.setOnClickListener(imgLoad);

        switch (ad_price) {
            case "10000" :
                rbtn1.isChecked();
                break;
            case "20000" :
                rbtn2.isChecked();
                break;
            case "30000" :
                rbtn3.isChecked();
                break;
        }

        WebSettings webSettings = wv_image.getSettings();
        webSettings.setJavaScriptEnabled(true); // JavaScript 사용 가능
        webSettings.setBuiltInZoomControls(true); // 확대 축소 가능
        webSettings.setDisplayZoomControls(false); // 돋보기 없애기

        etTitle.setText(ad_title);
        etUrl.setText(ad_url);
        etLocation.setText(ad_location);
        wv_image.loadData(htmlData(ad_image), "text/html", "UTF-8");

        btn_indate.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              DatePickerDialog dialog = new DatePickerDialog(AdUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                                                  @Override
                                                  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                                      String m;
                                                      String d;
                                                      if (1>Integer.toString(month+1).length()) {
                                                          m = "0"+Integer.toString(month+1);
                                                      }else {
                                                          m = Integer.toString(month+1);
                                                      }
                                                      if (1>Integer.toString(dayOfMonth).length()) {
                                                          d = "0"+Integer.toString(dayOfMonth);
                                                      } else {
                                                          d = Integer.toString(dayOfMonth);
                                                      }
                                                      ad_indate = year + "-" + m + "-" + d + " ";
                                                      btn_indate.setText(ad_indate);
                                                  }
                                              }, Integer.parseInt(ad_indate.substring(0,4)), Integer.parseInt(ad_indate.substring(5,7)) - 1, Integer.parseInt(ad_indate.substring(8,10)));
                                              dialog.show();
                                          }
                                      });

//        btnRegister.setOnClickListener(onClickListener);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.v("ggg","라디오 그룹 시작");
                switch (checkedId){
                    case R.id.register_rbtn1_update:
                        new_price = "10000";
                        break;
                    case R.id.register_rbtn2_update:
                        new_price = "20000";
                        break;
                    case R.id.register_rbtn3_update:
                        new_price = "30000";
                        break;
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(new_price)>Integer.parseInt(ad_price)) {
                    String kakaopayPrice = Integer.toString(Integer.parseInt(new_price)-Integer.parseInt(ad_price));
                    PayActivity pay__activityActivity = new PayActivity(ad_title, kakaopayPrice);
                    Intent intent = new Intent(getApplicationContext(), pay__activityActivity.getClass());
                    intent.putExtra("ad_id", ad_id);
                    startActivity(intent);
                }else {
                    Toast.makeText(AdUpdateActivity.this, "기간을 줄이시는 경우에는 삭제 후 재 결제 부탁드립니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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
                "<img src=\""+ ShareVar.sUrl + img + "\" width =\"auto\" height=\"100%\">" +
                "</body></html>";
        return image;
    }

    View.OnClickListener imgLoad = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            //Photo App.으로 이동
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
        }
    };

    private void imageUpload(){
        ImageNetworkTask networkTask = new ImageNetworkTask(AdUpdateActivity.this, img, img_path, urlAddr);

        //              NetworkTask Class의 doInBackground Method의 결과값을 가져온다.

        try {
            Integer result = networkTask.execute(100).get();

            //              doInBackground의 결과값으로 Toast생성

            switch (result){
                case 1:
                    //              Device에 생성한 임시 파일 삭제
                    File file = new File(img_path);
                    file.delete();

                    break;

                case 0:
                    break;
            }
            //////////////////////////////////////////////////////////////////////////////////////////////
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //Photo App.에서 Image 선택후 작업내용
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("ggg", "Data :" + String.valueOf(data));

        if (requestCode == REQ_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                //이미지의 URI를 얻어 경로값으로 반환.
                img_path = getImagePathToUri(data.getData());
                Log.v("ggg", "image path :" + img_path);
                Log.v("ggg", "Data :" +String.valueOf(data.getData()));

                //이미지를 비트맵형식으로 반환
                Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                Bitmap image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);

                Log.v("ggg", "img glide 1");
                Glide.with(AdUpdateActivity.this)
                        .load(image_bitmap_copy)
                        .error(R.drawable.ad)
                        .into(img);
                Log.v("ggg", "img glide 2");

                // 파일 이름 및 경로 바꾸기(임시 저장, 경로는 임의로 지정 가능)
                String date = new SimpleDateFormat("yyyyMMddHm").format(new Date());
                imageName = date + "." + f_ext;
                tempSelectFile = new File(devicePath , imageName);
                OutputStream out = new FileOutputStream(tempSelectFile);
                image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                // 임시 파일 경로로 위의 img_path 재정의
                img_path = devicePath + imageName;


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //사용자가 선택한 이미지의 정보를 받아옴
    private String getImagePathToUri(Uri data) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        // 확장자 명 저장
        f_ext = imgPath.substring(imgPath.length()-3, imgPath.length());

        return imgPath;
    }

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

}