package com.ar.salata.repositories;

import androidx.lifecycle.MutableLiveData;

import com.ar.salata.repositories.API.GoodsAPI;
import com.ar.salata.repositories.model.Category;
import com.ar.salata.repositories.model.CategoryList;
import com.ar.salata.repositories.model.Product;
import com.ar.salata.repositories.model.ProductList;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ar.salata.SalataApplication.BASEURL;
import static com.ar.salata.repositories.UserRepository.APIResponse.ERROR;
import static com.ar.salata.repositories.UserRepository.APIResponse.FAILED;
import static com.ar.salata.repositories.UserRepository.APIResponse.NULL;
import static com.ar.salata.repositories.UserRepository.APIResponse.SUCCESS;

public class GoodsRepository {
    private GoodsAPI goodsAPI;

    public GoodsRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        goodsAPI = retrofit.create(GoodsAPI.class);
    }

    public MutableLiveData<UserRepository.APIResponse> loadCategories() {
        MutableLiveData<UserRepository.APIResponse> apiResponse = new MutableLiveData<>(NULL);
        goodsAPI.getCategories().enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                if (response.isSuccessful()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.insertOrUpdate(response.body().getCategoryList());
                        }
                    });
                    realm.close();
                    apiResponse.setValue(UserRepository.APIResponse.SUCCESS);
                } else {
                    apiResponse.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                apiResponse.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return apiResponse;
    }

    public MutableLiveData<CategoryList> getCategories() {
        MutableLiveData<CategoryList> categoryList = new MutableLiveData<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Category> results = realm.where(Category.class).findAllAsync();
        results.addChangeListener(new RealmChangeListener<RealmResults<Category>>() {
            @Override
            public void onChange(RealmResults<Category> categories) {
                List<Category> list = realm.copyFromRealm(categories);
                categoryList.setValue(new CategoryList(list));
            }
        });
        return categoryList;
    }

    public MutableLiveData<UserRepository.APIResponse> loadProducts() {
        MutableLiveData<UserRepository.APIResponse> apiResponse = new MutableLiveData<>(NULL);
        goodsAPI.getAllProducts().enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                if (response.isSuccessful()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.insertOrUpdate(response.body().getProductList());
                        }
                    });
                    realm.close();
                    apiResponse.setValue(SUCCESS);
                } else {
                    apiResponse.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                apiResponse.setValue(FAILED);
            }
        });
        return apiResponse;
    }

    public MutableLiveData<ProductList> getProducts(int categoryId) {
        MutableLiveData<ProductList> productList = new MutableLiveData<>();
        Realm realm = Realm.getDefaultInstance();
        realm.where(Product.class).equalTo("categoryId", categoryId).findAllAsync().addChangeListener(new RealmChangeListener<RealmResults<Product>>() {
            @Override
            public void onChange(RealmResults<Product> products) {
                productList.setValue(new ProductList(realm.copyFromRealm(products)));
            }
        });
        return productList;
    }
}
