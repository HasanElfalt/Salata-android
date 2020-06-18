package com.ar.salata.repositories;

import androidx.lifecycle.MutableLiveData;

import com.ar.salata.repositories.API.OrderAPI;
import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.Order;
import com.ar.salata.repositories.model.OrderAssociative;
import com.ar.salata.repositories.model.OrderUnit;

import java.util.HashMap;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ar.salata.SalataApplication.BASEURL;

public class OrderRepository {
    private static final String TAG = "OrderRepository";
    OrderAPI orderAPI;

    public OrderRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        orderAPI = retrofit.create(OrderAPI.class);
    }

    public MutableLiveData<UserRepository.APIResponse> submitOrder(APIToken token, Order order) {
        MutableLiveData<UserRepository.APIResponse> apiResponse =
                new MutableLiveData<>(UserRepository.APIResponse.NULL);

        HashMap<String, Double> units = new HashMap<String, Double>();
        for (OrderUnit unit : order.getUnits()) {
            units.put(String.valueOf(unit.getProductId()), unit.getCount());
        }

        OrderAssociative orderAssociative = new OrderAssociative(units,
                token.toString(),
                order.getOrderId(),
                order.getOrderPrice(),
                order.getShiftId(),
                order.getAddressId(),
                order.getUserId(),
                order.getDeliveryDate());

        orderAPI.submitOrder(orderAssociative).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    apiResponse.setValue(UserRepository.APIResponse.SUCCESS);
                } else {
                    apiResponse.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                apiResponse.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return apiResponse;
    }

    public String createOrder(Order order) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Order temp = realm.copyToRealmOrUpdate(order);
        realm.commitTransaction();

        return temp.getOrderLocalId();
    }

    public Order getOrder(String Id) {
        Realm realm = Realm.getDefaultInstance();
        Order order = realm.where(Order.class).equalTo("orderLocalId", Id).findFirst();
        order = realm.copyFromRealm(order);

        return order;
    }
}
