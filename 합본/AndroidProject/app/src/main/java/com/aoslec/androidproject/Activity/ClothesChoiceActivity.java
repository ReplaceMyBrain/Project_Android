package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

    MenuItem reset, ok;
    ClothesSQLite clothesSQLite;

    String cTemp, cItem1, cItem2, cItem3, cItem4, cItem5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_choice);

        clothesSQLite = new ClothesSQLite(ClothesChoiceActivity.this);

        reset = findViewById(R.id.menu_reset);
        ok = findViewById(R.id.menu_update);

        //그리드뷰
        adapter = new ClothesChoiceAdapter(this, R.layout.gridview_clothes_choice, ChoiceBean);
        gridView = findViewById(R.id.ClothesChoice_gv);
        gridView.setAdapter(adapter);

        //클릭이벤트
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
        setTitle(choice.get(0) + getResources().getString(R.string.ClothesChoice));
        adapter.notifyDataSetChanged();
    }

    //메뉴 만들기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.clothes_choice_menu, menu);
        return true;
    }


    //메뉴클릭시
    public boolean onOptionsItemSelected(MenuItem item) {

        SQLiteDatabase DB;
        String query = "";

        switch (item.getItemId()) {

            case R.id.menu_reset:

                try {
                    DB = clothesSQLite.getWritableDatabase();
                    Log.v("ggg", "초기화 +" + choice.get(0));

                    switch (choice.get(0)) {
                        case "-5º ▼":
                            query = "UPDATE clothes SET item1 ='10.png',item2 ='11.png',item3 ='14.png',item4 ='15.png',item5 ='null' WHERE Temperature = '"+choice.get(0)+"';";
                            break;
                        case "-5º ~ 0º":
                            query = "UPDATE clothes SET item1 ='9.png',item2 ='10.png',item3 ='11.png',item4 ='14.png',item5 ='15.png' WHERE Temperature ='"+choice.get(0)+"';";
                            break;
                        case "0º ~ 5º":
                            query = "UPDATE clothes SET item1 ='7.png',item2 ='9.png',item3 ='10.png',item4 ='12.png',item5 ='14.png' WHERE Temperature ='"+choice.get(0)+"';";
                            break;
                        case "5º ~ 10º":
                            query = "UPDATE clothes SET item1 ='5.png',item2 ='6.png',item3 ='8.png',item4 ='7.png',item5 ='12.png' WHERE Temperature ='"+choice.get(0)+"';";
                            break;
                        case "10º ~ 15º":
                            query = "UPDATE clothes SET item1 ='5.png',item2 ='6.png',item3 ='8.png',item4 ='12.png',item5 ='null' WHERE Temperature ='"+choice.get(0)+"';";
                            break;
                        case "15º ~ 20º":
                            query = "UPDATE clothes SET item1 ='3.png',item2 ='4.png',item3 ='12.png',item4 ='null',item5 ='null' WHERE Temperature ='"+choice.get(0)+"';";
                            break;
                        case "20º ~ 25º":
                            query = "UPDATE clothes SET item1 ='2.png',item2 ='3.png',item3 ='4.png',item4 ='12.png',item5 ='13.png' WHERE Temperature ='"+choice.get(0)+"';";
                            break;
                        case "25º ~ 30º":
                            query = "UPDATE clothes SET item1 ='2.png',item2 ='12.png',item3 ='13.png',item4 ='null',item5 ='null' WHERE Temperature ='"+choice.get(0)+"';";
                            break;
                        case "30º ▲":
                            query = "UPDATE clothes SET item1 ='1.png',item2 ='2.png',item3 ='13.png',item4 ='null',item5 ='null' WHERE Temperature ='"+choice.get(0)+"';";
                            break;
                    }
                    DB.execSQL(query);
                    clothesSQLite.close();

                    Intent intent = new Intent(ClothesChoiceActivity.this, ClothesActivity.class);
                    startActivity(intent);


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("ggg", "실패");
                }
                break;

            case R.id.menu_update:

                //전체 null값은 다 삭제시킴
                for (int i = 1; i < choice.size(); i++) {
                    choice.remove("null");
                    choice.remove(null);
                }

                //뒤에 null값 채움 (오류때문에)
                if (choice.size() <= 6) {
                    for (int i = choice.size(); i <= 5; i++) {
                        choice.add(null);
                    }
                }
                cTemp = choice.get(0);
                cItem1 = choice.get(1);
                cItem2 = choice.get(2);
                cItem3 = choice.get(3);
                cItem4 = choice.get(4);
                cItem5 = choice.get(5);

                for (int i = 0; i < choice.size(); i++) {

                    Log.v("ggg", "사이즈는?" + choice.size() + "값은? " + i + "번째" + choice.get(i));

                }

                try {

                    if (choice.size() > 6) {
                        Toast.makeText(ClothesChoiceActivity.this, getResources().getString(R.string.ClothesChoiceMax), Toast.LENGTH_SHORT).show();
                    } else if (choice.get(1) == null || choice.get(1) == "null") {
                        Toast.makeText(ClothesChoiceActivity.this, getResources().getString(R.string.ClothesChoiceMin), Toast.LENGTH_SHORT).show();
                    } else {
                        DB = clothesSQLite.getWritableDatabase();
                        query = "UPDATE clothes SET item1 = '" + cItem1 + "', item2 = '" + cItem2 + "', item3 = '" + cItem3 + "', item4 = '" + cItem4 + "', item5 = '" + cItem5 + "' WHERE Temperature = '" + cTemp + "';";
                        DB.execSQL(query);
                        clothesSQLite.close();

                        Intent intent = new Intent(ClothesChoiceActivity.this, ClothesActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("ggg", "실패");
                }
                break;
        }

        return true;
    }

    //아이템 체크
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (ChoiceBean.get(position).isSelect() == false) {
                ChoiceBean.get(position).setSelect(true);
                choice.add(ChoiceBean.get(position).getItem());
                Log.v("ggg", "true다!" + ChoiceBean.get(position).getItem());
                adapter.notifyDataSetChanged();

            } else {
                ChoiceBean.get(position).setSelect(false);
                choice.remove(ChoiceBean.get(position).getItem());
                adapter.notifyDataSetChanged();
                Log.v("ggg", "false다!");
            }
        }
    };
}