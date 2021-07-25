package com.aoslec.androidproject.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.aoslec.androidproject.Bean.Ad_requestBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTaskAdmin extends AsyncTask<Integer, String, Object> {


    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<Ad_requestBean> ads;

    // Network Task를 검색, 입력, 수정, 삭제 구분없이 하나로 사용키 위해 생성자 변수 추가.
    String where = null;

    //다이얼로그는 생성자로 만들 필요가 압ㅂ서다
    public NetworkTaskAdmin(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.ads = ads;
        this.ads = new ArrayList<Ad_requestBean>();
        this.where = where;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get....");
//        progressDialog.show();

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String result = null;


        //----------------------------기본 양식----------------
        try{
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);  //10초
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }  //----------------------------------------------------------------

                if (where.equals("selectRequest")){
                    parserSelectRequest(stringBuffer.toString());
                }
                else if (where.equals("insertAd")){
                    result = parserAction(stringBuffer.toString());
                }
                else if (where.equals("adUpdateOK")){
                    result = parserActionOK(stringBuffer.toString());
                }
                else if (where.equals("adUpdateStop")){
                    result = parserActionStop(stringBuffer.toString());
                }
                else if (where.equals("adUpdateStopAD")){
                    result = parserActionStopAD(stringBuffer.toString());
                }
                else if (where.equals("selectApprove")){
                    parserSelectApprove(stringBuffer.toString());
                }
                else {
                    result = null;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            if (where.equals("selectRequest")){
                return ads;


            }else if (where.equals("insertAd")){
                return  result;

            }else if (where.equals("adUpdateOK")){
                return  result;

            }else if (where.equals("adUpdateStop")){
                return  result;

            }else if (where.equals("adUpdateStopAD")){
                return  result;

            }

            else if (where.equals("selectApprove")){
                return ads;
            }
            else {
                return result;
            }
        }
    }

    private String parserAction(String str){
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
            Log.v("ggg","returnValue" + returnValue);
        }catch (Exception e){
            e.printStackTrace();
            Log.v("ggg","returnValue 실패" + returnValue);
        }

        return  returnValue;
    }

    private String parserActionOK(String str){
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
            Log.v("ggg","returnValue" + returnValue);
        }catch (Exception e){
            e.printStackTrace();
            Log.v("ggg","returnValue 실패" + returnValue);
        }

        return  returnValue;
    }

    private String parserActionStop(String str){
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
            Log.v("ggg","returnValue" + returnValue);
        }catch (Exception e){
            e.printStackTrace();
            Log.v("ggg","returnValue 실패" + returnValue);
        }

        return  returnValue;
    }

    private String parserActionStopAD(String str){
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
            Log.v("ggg","returnValue" + returnValue);
        }catch (Exception e){
            e.printStackTrace();
            Log.v("ggg","returnValue 실패" + returnValue);
        }

        return  returnValue;
    }

    private void parserSelectRequest(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("ad_info"));
            ads.clear();

            for (int i = 0; i<jsonArray.length();i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String title = jsonObject1.getString("title");
                Log.v("ggg","타이틀 : " + title);
                String url = jsonObject1.getString("url");
                Log.v("ggg","유알엘 : " + url);
                String price = jsonObject1.getString("price");
                Log.v("ggg","가격 : " + price);
                String email = jsonObject1.getString("email");
                Log.v("ggg","이메일 : " + email);
                String image = jsonObject1.getString("image");

                String adid = jsonObject1.getString("adid");

                Ad_requestBean member = new Ad_requestBean(title, url, price, email,image, adid);
                ads.add(member);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parserSelectApprove(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("ad_info"));
            ads.clear();

            for (int i = 0; i<jsonArray.length();i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String title = jsonObject1.getString("title");
                Log.v("ggg","타이틀 : " + title);
                String url = jsonObject1.getString("url");
                Log.v("ggg","유알엘 : " + url);
                String price = jsonObject1.getString("price");
                Log.v("ggg","가격 : " + price);
                String email = jsonObject1.getString("email");
                Log.v("ggg","이메일 : " + email);
                String image = jsonObject1.getString("image");

                String adid = jsonObject1.getString("adid");

                Ad_requestBean member = new Ad_requestBean(title, url, price, email,image, adid);
                ads.add(member);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} // NetworkTask
