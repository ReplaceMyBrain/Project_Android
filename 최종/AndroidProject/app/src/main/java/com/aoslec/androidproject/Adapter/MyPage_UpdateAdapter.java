package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.aoslec.androidproject.Bean.MyPayge_UpdateBean;
import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;

import java.util.ArrayList;

public class MyPage_UpdateAdapter extends BaseAdapter {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<MyPayge_UpdateBean> data=null;
    private LayoutInflater inflater=null;

    public MyPage_UpdateAdapter(Context mcontext, int layout, ArrayList<MyPayge_UpdateBean> data) {

        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(this.layout, parent, false);

        TextView title = convertView.findViewById(R.id.tv_title_custom_b);
        TextView miniTitle = convertView.findViewById(R.id.tv_title2_custom_b);
        TextView content = convertView.findViewById(R.id.tv_content_custom_b);
        TextView text1 = convertView.findViewById(R.id.tv_1_custom_b);
        TextView text2 = convertView.findViewById(R.id.tv_2_custom_b);
        Button btnOpen = convertView.findViewById(R.id.btn_open_custom_b);
        Button btnUpdate = convertView.findViewById(R.id.btn_change_custom_b);

        title.setVisibility(View.GONE);
        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);

        title.setText(data.get(position).getTitle());
        miniTitle.setText(data.get(position).getMiniTitle());
        content.setText(data.get(position).getContent());
        text1.setText(data.get(position).getText1());
        text2.setText(data.get(position).getText2());
        btnOpen.setText(data.get(position).getBtnOpen());
        btnUpdate.setText(data.get(position).getBtnUpdate());

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "클릭", Toast.LENGTH_SHORT).show();
                if(v.getId()== R.id.btn_open_custom_b) {
                    if (btnOpen.getText().equals("취소")) {
                        btnUpdate.setVisibility(View.GONE);
                        text1.setVisibility(View.GONE);
                        text2.setVisibility(View.GONE);
                        btnOpen.setText("수정");
                    }else {
                        btnUpdate.setVisibility(View.VISIBLE);
                        text1.setVisibility(View.VISIBLE);
                        text2.setVisibility(View.VISIBLE);
                        btnOpen.setText("취소");
                    }
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog(position, data.get(position).getMiniTitle());
            }
        });

        return convertView;
    }

    private AlertDialog makeDialog(int position, String title) {
        final EditText et = new EditText(mcontext);
//        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        et.setHint("변경하실 내용을 입력해주세요!");
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle(title+" 변경");
        builder.setMessage(title+"을 변경하시겠습니까?");
        builder.setView(et);
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = et.getText().toString();
                updateUserData(position, value);
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("아니요", new DialogInterface.OnClickListener() {
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

    private void updateUserData(int position, String value) {
        String title = "";
        switch (position) {
            case 0 :
                title = "name";
                break;
            case 1 :
                title = "nEmail";
                break;
            case 2 :
                title = "phone";
                break;
        }
        String urlAddr = ShareVar.sUrl + "update_user.jsp?"+"email="+ SaveSharedPreferences.getPrefEmail(mcontext)+"&"+title+"="+value;

        Log.v("Message",urlAddr);
        String result = null;
        try {
            User_NT networkTask = new User_NT(mcontext, urlAddr, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(result.equals("1")) {
            Toast.makeText(mcontext, value+"로 수정 되었습니다.", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
            switch (position) {
                case 0:
                    SaveSharedPreferences.setPrefName(mcontext, value);
                    break;
                case 1:
                    SaveSharedPreferences.setPrefEmail(mcontext, value);
                    break;
                case 2:
                    SaveSharedPreferences.setPrefPhone(mcontext, value);
                    break;
            }
        }else {
            Toast.makeText(mcontext, "수정 실패되었습니다.", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        }
    }

}
