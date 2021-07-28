package com.aoslec.androidproject.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.aoslec.androidproject.NetworkTask.ImageNetworkTask;
import com.aoslec.androidproject.NetworkTask.NetworkTaskAdmin;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdActivity extends Activity {

    String urlAddr, sTitle, sUrl, price, email, imageName, simg, imgurlAddr;
    String imgUrl = ShareVar.sUrl;

    RadioGroup radioGroup;
    RadioButton rbtn1, rbtn2, rbtn3;
    ImageView img;
    EditText etTitle, etUrl;
    TextView tvTemp;
    Button btnRegister;

    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();

    //이미지 업로드에 쓰일 것
    String devicePath = Environment.getDataDirectory().getAbsolutePath() + "/data/com.aoslec.androidproject/";
    private final int REQ_CODE_SELECT_IMAGE = 300; // Gallery Return Code
    private String img_path = null; // 최종 file name
    private String f_ext = null;    // 최종 file extension
    File tempSelectFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("ggg", "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        setTitle(getResources().getString(R.string.ad_title));

        urlAddr = ShareVar.sUrl + "adInsert.jsp?";

        radioGroup = findViewById(R.id.register_radio);
        img = findViewById(R.id.register_image);
        etTitle = findViewById(R.id.register_etTitle);
        etUrl = findViewById(R.id.register_etUrl);
        btnRegister = findViewById(R.id.register_btnRegister);
        rbtn1 = findViewById(R.id.register_rbtn1);
        rbtn2 = findViewById(R.id.register_rbtn2);
        rbtn3 = findViewById(R.id.register_rbtn3);


        btnRegister.setOnClickListener(onClickListener);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.v("ggg","라디오 그룹 시작");
                switch (checkedId){
                    case R.id.register_rbtn1:
                        price = "10000";
                        break;
                    case R.id.register_rbtn2:
                        price = "20000";
                        break;
                    case R.id.register_rbtn3:
                        price = "30000";
                        break;
                }
            }
        });

        img.setOnClickListener(imgLoad);


        //          사용자에게 사진(Media) 사용 권한 받기
        ActivityCompat.requestPermissions(AdActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v("ggg","클릭실행");


            sTitle = etTitle.getText().toString();
            sUrl = etUrl.getText().toString();


            //이미지업로드 메소스 실행
            simg = imageName;
            Log.v("ggg","이미지 명 : " + simg);

            if(simg == null){

            }else {
                imgurlAddr = imgUrl + "multipartRequest.jsp";
                imageUpload();
            }


            urlAddr = urlAddr + "email=" + SaveSharedPreferences.getPrefEmail(AdActivity.this) + "&title=" + sTitle + "&url=" + sUrl + "&price=" + price + "&image=" + simg;
            Log.v("ggg","유알엘 = " + urlAddr);

            String result = connectInsertData();
            Log.v("ggg","result = " + result);
            if (result.equals("1")){
                String kakaopayPrice = Integer.toString(Integer.parseInt(price));
                PayActivity payActivity = new PayActivity(sTitle, kakaopayPrice);
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(AdActivity.this, "신청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
            finish();  // back버튼과 같은 원리 입력다 하면 메인화면으로
            Log.v("ggg","finish");

        }
    };

    private  String connectInsertData(){
        String result = null;
        Log.v("ggg","result111 = " + result);
        try {
            NetworkTaskAdmin networkTask = new NetworkTaskAdmin(AdActivity.this, urlAddr,"insertAd");
            Object obj = networkTask.execute().get();
            result = (String)obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v("ggg","result2 = " + result);
        return result;
    }

    //이미지업로드 시작!!

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
        ImageNetworkTask networkTask = new ImageNetworkTask(AdActivity.this, img, img_path, imgurlAddr);

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

                Glide.with(AdActivity.this)
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
                Log.v("ggg","경로1 : " + img_path);


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

}