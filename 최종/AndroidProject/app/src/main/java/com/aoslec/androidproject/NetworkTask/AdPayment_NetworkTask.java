package com.aoslec.androidproject.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.aoslec.androidproject.Bean.Ad_PaymentBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AdPayment_NetworkTask extends AsyncTask<Integer, String, Object> {

    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    Ad_PaymentBean ad_paymentBean;
    ArrayList<Ad_PaymentBean> adlist;
    //NetworkTask를 검색, 입력, 수정, 삭제 구분 없이 하나로 사용하기 위해 생성자 변수 추가
    String where = null;

    public AdPayment_NetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.adlist =  new ArrayList<Ad_PaymentBean>();
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get.....");
        progressDialog.show();
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

        String result = null; //DB 연결이 잘 되었는지 받는 용도

        try {
            URL url = new URL(mAddr);
            Log.v("Message", url.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000); //10초 동안 연결 시도하다가 실패시 에러

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                while (true) {
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }
                if (where.equals("select")) {
                    parserSelect(stringBuffer.toString());
                }else{
                    parserCount(stringBuffer.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
            return adlist;

    }

    private void parserSelect(String str) {
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("adpayment_info"));
            adlist.clear(); //기존에 쌓일 수 있는 데이터를 삭제함
           for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String id_ad = jsonObject1.getString("id_ad");
                String ad_title = jsonObject1.getString("ad_title");
               String ad_url = jsonObject1.getString("ad_url");
                String ad_image = jsonObject1.getString("ad_image");
               String ad_price = jsonObject1.getString("ad_price");
               String ad_location = jsonObject1.getString("ad_location");
                String ad_indate = jsonObject1.getString("ad_indate");
                String ad_outdate = jsonObject1.getString("ad_outdate");
                String payment_indate = jsonObject1.getString("payment_indate");
                String payment_outdate = jsonObject1.getString("payment_outdate");
                String id_payment = jsonObject1.getString("id_payment");
                Ad_PaymentBean ad_paymentBean = new Ad_PaymentBean(id_ad, ad_title, ad_url, ad_image, ad_price, ad_location, ad_indate, ad_outdate, id_payment, payment_indate, payment_outdate);
               adlist.add(ad_paymentBean);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void parserCount(String str) {
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("adcount_info"));
            adlist.clear(); //기존에 쌓일 수 있는 데이터를 삭제함
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String count = jsonObject1.getString("count");
                Ad_PaymentBean ad_paymentBean = new Ad_PaymentBean(count);
                adlist.add(ad_paymentBean);
                Log.v("Count", count);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
