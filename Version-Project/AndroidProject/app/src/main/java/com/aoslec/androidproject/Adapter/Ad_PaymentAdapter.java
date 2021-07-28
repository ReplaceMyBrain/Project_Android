package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoslec.androidproject.Bean.Ad_PaymentBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.ShareVar;

import java.util.ArrayList;

/**
 * Created by biso on 2021/06/24.
 */
public class Ad_PaymentAdapter extends BaseAdapter {

    String urlAddr;

    private ArrayList<Ad_PaymentBean> data = null;
    private Context mContext = null;
    private int layout = 0;
    private LayoutInflater inflater = null;
    private String type;

    public Ad_PaymentAdapter(Context mContext, int layout, ArrayList<Ad_PaymentBean> data, String type) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.type = type;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.v("Message", String.valueOf(data.size()));
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getAd_id();
    }

    @Override
    public long getItemId(int position) {
        //getItemId -> getItem
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(this.layout, parent, false);

        WebView wv_image;
        ImageView iv_image;
        TextView tv_title;
        TextView tv_link;
        TextView tv_indate;
        TextView tv_outdate;

        wv_image = convertView.findViewById(R.id.wv_image_custom_ad);
        iv_image = convertView.findViewById(R.id.iv_image_custom_ad);
        tv_title = convertView.findViewById(R.id.tv_title_custom_ad);
        tv_link = convertView.findViewById(R.id.tv_link_custom_ad);
        tv_indate = convertView.findViewById(R.id.tv_indate_custom_ad);
        tv_outdate = convertView.findViewById(R.id.tv_outdate_custom_ad);

        WebSettings webSettings = wv_image.getSettings();
        webSettings.setJavaScriptEnabled(true); // JavaScript 사용 가능
        webSettings.setBuiltInZoomControls(true); // 확대 축소 가능
        webSettings.setDisplayZoomControls(false); // 돋보기 없애기


        tv_title.setText(data.get(position).getAd_title());
        wv_image.setFocusable(false);
        wv_image.loadData(htmlData(data.get(position).getAd_image()), "text/html", "UTF-8");
//        wv_image.loadUrl("http://192.168.2.41:8080/contact/cat1.jsp");
        tv_link.setText(data.get(position).getAd_url());
        wv_image.setVisibility(View.VISIBLE);

        if (type.equals("now")) {
            tv_indate.setText("D-Day");
            tv_outdate.setText(data.get(position).getAd_outdate());
        } else {
            tv_indate.setText(data.get(position).getAd_indate());
            tv_outdate.setText("~" + data.get(position).getAd_outdate());
        }
        return convertView;

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
                    "<img src=\""+ ShareVar.sUrl +"adImage/"+ img + "\" width =\"auto\" height=\"100%\">" +
                    "</body></html>";
            return image;
        }
}
