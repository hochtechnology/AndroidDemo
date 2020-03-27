package com.hochtechnology.skeleton;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hochtechnology.skeleton.ViewModel.HeroesViewModel;
import com.hochtechnology.skeleton.adapter.HeroesAdapter;
import com.hochtechnology.skeleton.database.DBInfo;
import com.hochtechnology.skeleton.database.DatabaseHelper;
import com.hochtechnology.skeleton.model.User;
import com.hochtechnology.skeleton.utils.AndyConstants;
import com.hochtechnology.skeleton.utils.AndyUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    HeroesAdapter adapter;
    List<User> heroList;
    TextView error_diaplay;
    ProgressBar loading_state;
    DatabaseHelper databaseHelper;

    SwipeRefreshLayout refresh;

    public List<User> getAllXDVideos() {
        SQLiteDatabase db = this.databaseHelper.getReadableDatabase();
        List<User> lists = null;
        try {
            Cursor cursor = db.query(DBInfo.Table.TB_VIDEO_NAME, null, null, null, null, null, "");
            if (cursor.getCount() > 0) {
                lists = new ArrayList<>(cursor.getCount());
                User xdVideo = null;
                while (cursor.moveToNext()) {
                    String _id = cursor.getString(cursor.getColumnIndex(AndyConstants._ID));
                    String author = cursor.getString(cursor.getColumnIndex(AndyConstants._Author));
                    String width = cursor.getString(cursor.getColumnIndex(AndyConstants._Author));
                    String height = cursor.getString(cursor.getColumnIndex(AndyConstants._Author));
                    String url = cursor.getString(cursor.getColumnIndex(AndyConstants._Author));
                    String download_url = cursor.getString(cursor.getColumnIndex(AndyConstants._Author));
                    xdVideo = new User(_id, author, width, height, url, download_url);
                    lists.add(xdVideo);
                }
                cursor.close();
            }
        } catch (Exception e) {

        }
        db.close();
        return lists;
    }

    public void insert_(User video) {
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        String sql = "INSERT INTO " + DBInfo.Table.TB_VIDEO_NAME + " VALUES (" +
                //video.getId() + ",'" +
                video.getAuthor() + "','" +
                video.getHeight() + "','" +
                video.getWidth() + "',\"" +
                video.getUrl().toString() + "\",\"" +
                video.getDownload_url() +"\""+
                ");";
        db.execSQL(sql);
        db.close();
    }
    public void removeAllVideoInfo() {
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        String sql = "DELETE FROM " + DBInfo.Table.TB_VIDEO_NAME;
        db.execSQL(sql);
        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        refresh = findViewById(R.id.refresh);
        loading_state = findViewById(R.id.loading_state);
        error_diaplay = findViewById(R.id.error_diaplay);
        recyclerView = findViewById(R.id.recyclerview_listImage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        if (AndyUtils.isNetworkAvailable(MainActivity.this)) {
            setData();
//        } else {
//          refresh.setRefreshing(false);
//            heroList = getAllXDVideos();
//
//            if (heroList != null && heroList.size() > 0) {
//                adapter = new HeroesAdapter(MainActivity.this, heroList);
//                recyclerView.setAdapter(adapter);
//                recyclerView.setVisibility(View.VISIBLE);
//                error_diaplay.setVisibility(View.GONE);
//                refresh.setRefreshing(false);
//                Toast.makeText(MainActivity.this, "there is data to display", Toast.LENGTH_SHORT).show();
//                loading_state.setVisibility(View.GONE);
//            } else {
//                Toast.makeText(MainActivity.this, "there is data to display", Toast.LENGTH_SHORT).show();
//                loading_state.setVisibility(View.GONE);
//                recyclerView.setVisibility(View.GONE);
//                error_diaplay.setVisibility(View.VISIBLE);
//            }
//        }

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                if (AndyUtils.isNetworkAvailable(MainActivity.this)) {
                    setData();
//                } else {
//                    heroList = getAllXDVideos();
//
//                    if (heroList != null && heroList.size() > 0) {
//                        adapter = new HeroesAdapter(MainActivity.this, heroList);
//                        recyclerView.setAdapter(adapter);
//                        recyclerView.setVisibility(View.VISIBLE);
//                        error_diaplay.setVisibility(View.GONE);
//                        refresh.setRefreshing(false);
//                        Toast.makeText(MainActivity.this, "there is data to display", Toast.LENGTH_SHORT).show();
//                        loading_state.setVisibility(View.GONE);
//                    } else {
//                        Toast.makeText(MainActivity.this, "there is data to display", Toast.LENGTH_SHORT).show();
//                        loading_state.setVisibility(View.GONE);
//
//                        recyclerView.setVisibility(View.GONE);
//                        error_diaplay.setVisibility(View.VISIBLE);
//                    }
//                }
            }
        });


    }

    private void setData() {
        refresh.setRefreshing(true);
        HeroesViewModel model = ViewModelProviders.of(this).get(HeroesViewModel.class);
        model.getHeroes(MainActivity.this).observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> heroList) {
                Log.d("Hasmukh", "onChanged: " + heroList);
//                removeAllVideoInfo();
                if (heroList != null && heroList.size() > 0) {
//                    for (int i = 0; i < heroList.size(); i++) {
//                        insert_(heroList.get(i));
//                    }

                    adapter = new HeroesAdapter(MainActivity.this, heroList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    error_diaplay.setVisibility(View.GONE);
                    refresh.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "there is data to display", Toast.LENGTH_SHORT).show();
                    loading_state.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "there is data to display", Toast.LENGTH_SHORT).show();
                    loading_state.setVisibility(View.GONE);

                    recyclerView.setVisibility(View.GONE);
                    error_diaplay.setVisibility(View.VISIBLE);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        refresh.setRefreshing(false);
    }


}
