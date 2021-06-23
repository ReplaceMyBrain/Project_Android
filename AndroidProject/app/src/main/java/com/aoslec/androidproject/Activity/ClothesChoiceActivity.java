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

        //새로 빈은 만들어서 모든 아이템을 보여준다.
        ChoiceBean.clear();
        for(int i = 1; i<=15; i++){
            ChoiceBean choiceBean = new ChoiceBean(i + ".png");
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
        for(int i=1; i<choice.size(); i++){
            choice.remove(null);
        }


        reset = findViewById(R.id.ClothesChoice_reset);
        ok = findViewById(R.id.ClothesChoice_ok);

        ok.setOnClickListener(clickOk);

        adapter = new ClothesChoiceAdapter(this,R.layout.gridview_clothes_choice,ChoiceBean);
        gridView = findViewById(R.id.ClothesChoice_gv);
        gridView.setAdapter(adapter);
 //       gridView.setOnItemClickListener(onItemClickListener);



    }
//    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            if(gridView.isItemChecked(position)) {
//                gridView.setItemChecked(position,false);
//                Toast.makeText(ClothesChoiceActivity.this, ChoiceBean.get(position).getItem() + "취소합니다", Toast.LENGTH_SHORT).show();
//            }else {
//                gridView.setItemChecked(position,true);
//                Toast.makeText(ClothesChoiceActivity.this, ChoiceBean.get(position).getItem() + "선택되었습니다", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    };

    View.OnClickListener clickOk = new View.OnClickListener() {
        SQLiteDatabase DB;

        @Override
        public void onClick(View v) {
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

            if(choice.size()>6){
                Toast.makeText(ClothesChoiceActivity.this,"기온별 옷차림은 최대 5개입니다.",Toast.LENGTH_SHORT).show();
            }else {
                try {
                    Log.v("ggg", "try들어옴?");
                    DB = clothesSQLite.getWritableDatabase();
                    Log.v("ggg", "여기도함??");
                    String query = "UPDATE clothes SET item1 = '" + cItem1 + "', item2 = '" + cItem2 + "', item3 = '" + cItem3 + "', item4 = '" + cItem4 + "', item5 = '" + cItem5 + "' WHERE Temperature = '" + cTemp + "';";
                    Log.v("ggg", "업데이트완료  " + query);
                    DB.execSQL(query);
                    clothesSQLite.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("ggg", "실패");
                }
            }

                Intent intent = new Intent(ClothesChoiceActivity.this, ClothesActivity.class);
                startActivity(intent);

        }
    };

}