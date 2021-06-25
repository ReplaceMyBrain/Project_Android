package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.androidproject.Adapter.ClothesChoiceAdapter;
import com.aoslec.androidproject.Bean.ChoiceBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SQLite.ClothesSQLite;

import java.util.ArrayList;

public class ClothesChoiceActivity extends AppCompatActivity {

    ArrayList<ChoiceBean> ChoiceBean = new ArrayList<>();
    ArrayList<String> choice = new ArrayList<>();
    ClothesChoiceAdapter adapter = null;
    GridView gridView = null;

    TextView reset,ok;
    ClothesSQLite clothesSQLite;

    String cTemp,cItem1,cItem2,cItem3,cItem4,cItem5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_choice);

        clothesSQLite = new ClothesSQLite(ClothesChoiceActivity.this);

        reset = findViewById(R.id.ClothesChoice_reset);
        ok = findViewById(R.id.ClothesChoice_ok);

        //리스트뷰씀
        adapter = new ClothesChoiceAdapter(this,R.layout.gridview_clothes_choice,ChoiceBean);
        gridView = findViewById(R.id.ClothesChoice_gv);
        gridView.setAdapter(adapter);

        //클릭이벤트
        ok.setOnClickListener(clickOk);
        gridView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //새로 빈은 만들어서 모든 아이템을 저장.
        ChoiceBean.clear();
        for (int i = 1; i <= 15; i++) {
            ChoiceBean choiceBean = new ChoiceBean(i + ".png", false);
            ChoiceBean.add(choiceBean);
        }

        //기존에 있던 아이템을 임시 보관할 리스트에 넣어준다.
        Intent intent = getIntent();
        choice.clear();
        choice.add(intent.getStringExtra("temp"));
        choice.add(intent.getStringExtra("item1"));
        choice.add(intent.getStringExtra("item2"));
        choice.add(intent.getStringExtra("item3"));
        choice.add(intent.getStringExtra("item4"));
        choice.add(intent.getStringExtra("item5"));

        //null값 삭제함
        for (int i = 1; i < choice.size(); i++) {
            choice.remove("null");
            choice.remove(null);
        }

        //받아온 값과 불러온 값을 비교하여 select를 true로 바꿔준다.
        for (int i = 0; i < ChoiceBean.size(); i++) {
            for (int j = 0; j < choice.size(); j++) {
                if (ChoiceBean.get(i).getItem().equals(choice.get(j))) {
                    Log.v("ggg", ChoiceBean.get(i).getItem() + "비교할꺼야" + choice.get(j));
                    ChoiceBean.get(i).setSelect(true);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (ChoiceBean.get(position).isSelect() == false){
                ChoiceBean.get(position).setSelect(true);
                choice.add(ChoiceBean.get(position).getItem());
                Log.v("ggg", "true다!"+ChoiceBean.get(position).getItem());
                adapter.notifyDataSetChanged();

            }else {
                ChoiceBean.get(position).setSelect(false);
                choice.remove(ChoiceBean.get(position).getItem());
                adapter.notifyDataSetChanged();
                Log.v("ggg", "false다!");
            }
        }
    };

    View.OnClickListener clickOk = new View.OnClickListener() {
        SQLiteDatabase DB;

        @Override
        public void onClick(View v) {
            //앞에 null값은 다 삭제시킴
            for (int i = 1; i < choice.size(); i++) {
                choice.remove("null");
                choice.remove(null);
            }
            //뒤에 null값 채움
            if (choice.size()<=6){
                for(int i = choice.size(); i<=5; i++){
                    choice.add(null);
                }
            }
            cTemp = choice.get(0);
            cItem1 = choice.get(1);
            cItem2 = choice.get(2);
            cItem3 = choice.get(3);
            cItem4 = choice.get(4);
            cItem5 = choice.get(5);

            for(int i=0; i<choice.size(); i++) {

                Log.v("ggg", "사이즈는?" + choice.size() + "값은? " + i + "번째" + choice.get(i));

            }

                try {

                    if(choice.size()>6){
                        Toast.makeText(ClothesChoiceActivity.this,"기온별 옷차림은 최대 5개입니다.",Toast.LENGTH_SHORT).show();
                    }else {
                    DB = clothesSQLite.getWritableDatabase();
                    String query = "UPDATE clothes SET item1 = '" + cItem1 + "', item2 = '" + cItem2 + "', item3 = '" + cItem3 + "', item4 = '" + cItem4 + "', item5 = '" + cItem5 + "' WHERE Temperature = '" + cTemp + "';";
                    DB.execSQL(query);
                    clothesSQLite.close();

                    Intent intent = new Intent(ClothesChoiceActivity.this, ClothesActivity.class);
                    startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("ggg", "실패");
                }

        }
    };

}