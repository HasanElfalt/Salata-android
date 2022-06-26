package com.ar.salata.repositories;

import static com.ar.salata.SalataApplication.BASEURL;

import android.graphics.Path;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ar.salata.repositories.API.OpayAPI;
import com.ar.salata.repositories.model.OpaySetting;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpayRepository {

    private OpayAPI opayAPI;

    public OpayRepository(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        opayAPI = retrofit.create(OpayAPI.class);
    }

    public MutableLiveData<OpaySetting> getOpaySettings(){
        MutableLiveData<OpaySetting> res = new MutableLiveData<OpaySetting>();
        opayAPI.getOpaySetting().enqueue(new Callback<OpaySetting>() {
            @Override
            public void onResponse(Call<OpaySetting> call, Response<OpaySetting> response) {
                res.setValue(response.body());
            }

            @Override
            public void onFailure(Call<OpaySetting> call, Throwable t) {
                Log.e("OpayRepository", t.toString());
            }
        });
        return res;
    }
}
