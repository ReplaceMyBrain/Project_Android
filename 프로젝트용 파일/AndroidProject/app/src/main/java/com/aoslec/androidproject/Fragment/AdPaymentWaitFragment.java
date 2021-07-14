package com.aoslec.androidproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.aoslec.androidproject.Activity.AdViewActivity;
import com.aoslec.androidproject.Adapter.Ad_PaymentAdapter;
import com.aoslec.androidproject.Bean.Ad_PaymentBean;
import com.aoslec.androidproject.NetworkTask.AdPayment_NetworkTask;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;

import java.util.ArrayList;


public class AdPaymentWaitFragment extends Fragment {

    String urlAddr = null;
    Ad_PaymentAdapter adapter;
    ListView listView;
    ArrayList<Ad_PaymentBean> ad;

    public AdPaymentWaitFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ad_payment_wait, container, false);

        listView = v.findViewById(R.id.lv_wait_ad_payment);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), AdViewActivity.class);
                intent.putExtra("type", "wait");
                intent.putExtra("ad_id", ad.get(position).getAd_id());
                intent.putExtra("payment_id", ad.get(position).getPayment_id());
                intent.putExtra("ad_image", ad.get(position).getAd_image());
                intent.putExtra("ad_location", ad.get(position).getAd_location());
                intent.putExtra("ad_title", ad.get(position).getAd_title());
                intent.putExtra("ad_url", ad.get(position).getAd_url());
                intent.putExtra("ad_indate", ad.get(position).getAd_indate());
                intent.putExtra("ad_outdate", ad.get(position).getAd_outdate());
                intent.putExtra("ad_price", ad.get(position).getAd_price());
                startActivity(intent);
            }
        });

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        selectListAction();
    }

    private void selectListAction() {
        try{
            urlAddr = ShareVar.sUrl+"select_user_ad_payment_wait.jsp?email="+ SaveSharedPreferences.getPrefEmail(getContext());
            AdPayment_NetworkTask networkTask = new AdPayment_NetworkTask(getActivity(), urlAddr, "select");
            Object obj = networkTask.execute().get();
//            Log.v("Message", (String) obj);
            ad = (ArrayList<Ad_PaymentBean>) obj;

            adapter = new Ad_PaymentAdapter(getContext(), R.layout.custom_layout_ad_list, ad, "wait");
            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(onItemClickListener);
//            listView.setOnItemLongClickListener(onItemLongClickListener);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}