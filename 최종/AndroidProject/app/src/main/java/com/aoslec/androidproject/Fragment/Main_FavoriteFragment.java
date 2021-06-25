package com.aoslec.androidproject.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aoslec.androidproject.Activity.MainActivity;
import com.aoslec.androidproject.Adapter.FavoriteAdapter;
import com.aoslec.androidproject.Adapter.FavoriteHistoryAdapter;
import com.aoslec.androidproject.Adapter.MainTabAdapter;
import com.aoslec.androidproject.Bean.CurrentWeatherBean;
import com.aoslec.androidproject.Bean.FavoriteHistoryBean;
import com.aoslec.androidproject.Bean.FavoriteLatLongBean;
import com.aoslec.androidproject.Bean.FavoriteLocationBean;
import com.aoslec.androidproject.NetworkTask.NetworkTask;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SQLite.FavoriteInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main_FavoriteFragment extends Fragment {
    public static Context context;

    RecyclerView rvHistory;
    RecyclerView rvFavorite;
    RecyclerView.Adapter favoriteHistoryAdapter, favoriteAdapter;
    RecyclerView.LayoutManager HistoryManager, FavoriteManager;

    ArrayList<FavoriteHistoryBean> favoriteLocations;
    ArrayList<FavoriteLocationBean> favoriteLocationBeans;
    ArrayList<CurrentWeatherBean> current_weathers;
    ArrayList<FavoriteLatLongBean> favoriteLatLongBeans;

    private String location, longitude, latitude, heart;
    private int id;

    SQLiteDatabase DB;
    FavoriteInfo favoriteInfo;

    EditText editText;
    ImageView search;

    Geocoder geocoder;

    ViewPager viewPager;
    Main_WeatherFragment main_weatherFragment;
    MainTabAdapter mainTabAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_favorite, container, false);
        context = this.getContext();

        favoriteInfo = new FavoriteInfo(getContext());
        favoriteLocations = new ArrayList<FavoriteHistoryBean>();
        favoriteLocationBeans = new ArrayList<FavoriteLocationBean>();
        favoriteLatLongBeans = new ArrayList<FavoriteLatLongBean>();

        rvHistory = view.findViewById(R.id.favorite_rvHistory);
        rvFavorite = view.findViewById(R.id.favorite_rvFavorite);

        HistoryManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        FavoriteManager = new LinearLayoutManager(getContext());
        geocoder = new Geocoder(getContext());

        editText = view.findViewById(R.id.favorite_edit);
        search = view.findViewById(R.id.favorite_search);

        HistoryManager.canScrollHorizontally();

        rvHistory.setLayoutManager(HistoryManager);
        rvFavorite.setLayoutManager(FavoriteManager);

        viewPager=view.findViewById(R.id.Main_viewPager2);
        main_weatherFragment=new Main_WeatherFragment();

//        FragmentManager fm = getSupportFragmentManager();
//        mainTabAdapter = new MainTabAdapter(fm, getLifecycle());
//        viewPager.setAdapter(mainTabAdapter);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = editText.getText().toString().trim();

                searchLocation(location);



//                transaction.replace(R.id.main_fragment,main_weatherFragment);

            }
        });

        GetHistoryData();
        GetFavoriteData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        favoriteHistoryAdapter.notifyDataSetChanged();
        favoriteAdapter.notifyDataSetChanged();

        GetHistoryData();
        GetFavoriteData();

    }

    private void GetHistoryData() {
        try {
            DB = favoriteInfo.getWritableDatabase();
            String query = "select * from favorite order by id desc;";
            Cursor cursor = DB.rawQuery(query, null);
            while (cursor.moveToNext()) {
                id = cursor.getInt(0);
                location = cursor.getString(1);
                latitude = cursor.getString(2);
                longitude = cursor.getString(3);
                heart = cursor.getString(4);


                FavoriteHistoryBean favoriteHistoryBean = new FavoriteHistoryBean(id, location, latitude, longitude, heart);
                favoriteLocations.add(favoriteHistoryBean);
            }

            cursor.close();
            favoriteInfo.close();

            favoriteHistoryAdapter = new FavoriteHistoryAdapter(getActivity(), R.layout.favorite_history_location, favoriteLocations);
            rvHistory.setAdapter(favoriteHistoryAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GetHistoryData()

    private void GetFavoriteData() {
        try {
            favoriteLocationBeans.clear();
            favoriteLatLongBeans.clear();
            DB = favoriteInfo.getWritableDatabase();
            String query = "select * from favorite where heart='Y' order by id desc;";
            Cursor cursor = DB.rawQuery(query, null);
            Log.d("Favorite", cursor.getCount() + "rows found");
            while (cursor.moveToNext()) {
                id = cursor.getInt(0);
                location = cursor.getString(1);
                latitude = cursor.getString(2);
                longitude = cursor.getString(3);
                heart = cursor.getString(4);


                FavoriteLocationBean favoriteLocationBean = new FavoriteLocationBean(id, location, latitude, longitude, heart);
                favoriteLocationBeans.add(favoriteLocationBean);
            }

            cursor.close();
            favoriteInfo.close();

            Log.d("Favorite", favoriteLocationBeans.size() + "번 돌아");

            for (int i = 0; i < favoriteLocationBeans.size(); i++) {
                String favoriteLat = favoriteLocationBeans.get(i).getLatitude();
                String favoriteLong = favoriteLocationBeans.get(i).getLongitude();
                String favoriteLocation = favoriteLocationBeans.get(i).getLocation();

                String urlAddr = "https://api.openweathermap.org/data/2.5/onecall?lat=" + favoriteLat + "&lon=" + favoriteLong + "&exclude=minutely&appid=5a19414be68e50e321e070dbbd70b3cf&units=metric ";

                NetworkTask networkTask = new NetworkTask(getActivity(), urlAddr, "current");
                Object obj = networkTask.execute().get();
                current_weathers = (ArrayList<CurrentWeatherBean>) obj;

                int favoriteCurrentTemp = current_weathers.get(0).getCurrent_temp();
                int favoriteCurrentId = current_weathers.get(0).getCurrent_id();

                FavoriteLatLongBean favoriteLatLongBean = new FavoriteLatLongBean(favoriteLat, favoriteLong, favoriteLocation, favoriteCurrentTemp, favoriteCurrentId);
                favoriteLatLongBeans.add(favoriteLatLongBean);
            }

            favoriteAdapter = new FavoriteAdapter(getActivity(), R.layout.recycler_current_weather, favoriteLatLongBeans);
            rvFavorite.setAdapter(favoriteAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchLocation(String query) {
        List<Address> addressList = null;
        if (query.length() > 0) {
            try {
                // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                addressList = geocoder.getFromLocationName(
                        query, // 주소
                        10); // 최대 검색 결과 개수
            } catch (IOException e) {
                e.printStackTrace();
            }


            // 콤마를 기준으로 split
            String[] splitStr = addressList.get(0).toString().split(",");
            String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소

            Log.d("searchLocation", "splitStr : " + splitStr);
            Log.d("searchLocation", "addressList : " + addressList.get(0).toString());
            Log.d("searchLocation", "address : " + address);

            String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
            String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도

            Log.d("searchLocation", "latitude : " + latitude);
            Log.d("searchLocation", "longitude : " + longitude);


            try{
                DB=favoriteInfo.getWritableDatabase();
                String query2="INSERT INTO favorite(location,latitude,longitude,heart) VALUES('"+address+"','"+latitude+"','"+longitude+"','N');";
                DB.execSQL(query2);

                favoriteInfo.close();

                Toast.makeText(getContext(),"Insert OK!",Toast.LENGTH_SHORT).show();

                //새로고침
                ((MainActivity)getActivity()).refresh();
            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getContext(),"Insert Error!",Toast.LENGTH_SHORT).show();
            }


        }



    }
}