package com.hochtechnology.skeleton.ViewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hochtechnology.skeleton.model.User;
import com.hochtechnology.skeleton.reteofit.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HeroesViewModel extends ViewModel {

    private MutableLiveData<List<User>> heroList;

    public LiveData<List<User>> getHeroes(Context c) {
        if (heroList == null) {
            heroList = new MutableLiveData<List<User>>();
            loadHeroes(c);
        }
        return heroList;
    }

    private void loadHeroes(final Context c) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<List<User>> call = api.getHeroes();
        Toast.makeText(c, "api call is taking place", Toast.LENGTH_LONG).show();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                heroList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(c, "api call failed", Toast.LENGTH_LONG).show();
                Toast.makeText(c, "api call is taking place", Toast.LENGTH_LONG).show();

            }
        });
    }
}