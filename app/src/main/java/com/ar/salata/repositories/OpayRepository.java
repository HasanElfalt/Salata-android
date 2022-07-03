package com.ar.salata.repositories;

import static com.ar.salata.SalataApplication.BASEURL;

import android.graphics.Path;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ar.salata.repositories.API.OpayAPI;
import com.ar.salata.repositories.model.OpaySetting;
import com.ar.salata.ui.fragments.ErrorDialogFragment;

import java.sql.PreparedStatement;
import java.util.List;

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

    public MutableLiveData<UserRepository.APIResponse> setOpayPaymentInput(int userId, String ref){
        MutableLiveData<UserRepository.APIResponse> result = new MutableLiveData<>();
        opayAPI.setOpayPaymentInput(userId, ref).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                Log.e("OpayRepository", "1-" + response.body().toString() + "2-" + response.code());
                if(response.isSuccessful()) {
                    result.setValue(UserRepository.APIResponse.SUCCESS);
//                    Log.e("OpayRepository2", "1-" + response.body().toString() + "2-" + response.code());
                }else{
                    Log.e("OpayRepository", "1-" + response.body().toString() + "2-" + response.code());
                    result.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                result.setValue(UserRepository.APIResponse.FAILED);
                Log.e("OpayRepository", "1-" + t.getMessage());
            }
        });
        return result;
    }
}
