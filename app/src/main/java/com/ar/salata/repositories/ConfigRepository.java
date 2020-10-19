package com.ar.salata.repositories;

import androidx.lifecycle.MutableLiveData;

import com.ar.salata.repositories.API.ConfigAPI;
import com.ar.salata.repositories.model.AppConfig;
import com.ar.salata.repositories.model.ResponseDouble;
import com.ar.salata.repositories.model.ResponseMessage;
import com.ar.salata.repositories.model.phonesList;

import java.util.List;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ar.salata.SalataApplication.BASEURL;

public class ConfigRepository {
    private ConfigAPI configAPI;

    public ConfigRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        configAPI = retrofit.create(ConfigAPI.class);
    }


    public MutableLiveData<UserRepository.APIResponse> loadPhones() {
        MutableLiveData<UserRepository.APIResponse> results = new MutableLiveData<>();
        configAPI.getCompanyPhones().enqueue(new Callback<phonesList>() {
            @Override
            public void onResponse(Call<phonesList> call, Response<phonesList> response) {
                if (response.isSuccessful()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    AppConfig appConfig = realm.where(AppConfig.class).findFirst();
                    if (appConfig == null) {
                        appConfig = new AppConfig();
                        appConfig.setPhones(response.body().getPhones());
                        realm.copyToRealmOrUpdate(appConfig);
                    } else {
                        appConfig.setPhones(response.body().getPhones());
                    }
                    realm.commitTransaction();
                    realm.close();
                    results.setValue(UserRepository.APIResponse.SUCCESS);
                } else {
                    results.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<phonesList> call, Throwable t) {
                results.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return results;
    }

    public List<String> getPhones() {
        List<String> results;
        Realm realm = Realm.getDefaultInstance();
        results = realm.where(AppConfig.class).findFirst().getPhones();
        realm.close();
        return results;
    }

    public MutableLiveData<UserRepository.APIResponse> loadMinimumPurchases() {
        MutableLiveData<UserRepository.APIResponse> results = new MutableLiveData<>();
        configAPI.getMinimumPurchases().enqueue(new Callback<ResponseDouble>() {
            @Override
            public void onResponse(Call<ResponseDouble> call, Response<ResponseDouble> response) {
                if (response.isSuccessful()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    AppConfig appConfig = realm.where(AppConfig.class).findFirst();
                    if (appConfig == null) {
                        appConfig = new AppConfig();
                        appConfig.setMinimumPurchases(response.body().getValue());
                        realm.copyToRealmOrUpdate(appConfig);
                    } else {
                        appConfig.setMinimumPurchases(response.body().getValue());
                    }
                    realm.commitTransaction();
                    realm.close();
                    results.setValue(UserRepository.APIResponse.SUCCESS);
                } else {
                    results.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResponseDouble> call, Throwable t) {
                results.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return results;
    }

    public double getMinimumPurchases() {
        Realm realm = Realm.getDefaultInstance();
        double results = realm.where(AppConfig.class).findFirst().getMinimumPurchases();
        realm.close();
        return results;
    }

    public MutableLiveData<UserRepository.APIResponse> loadFacebook() {
        MutableLiveData<UserRepository.APIResponse> results = new MutableLiveData<>();
        configAPI.getFacebookAccount().enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    AppConfig appConfig = realm.where(AppConfig.class).findFirst();
                    if (appConfig == null) {
                        appConfig = new AppConfig();
                        appConfig.setFacebookAccount(response.body().toString());
                        appConfig.setFacebookAccountWeb(response.body().getMessage2());
                        realm.copyToRealmOrUpdate(appConfig);
                    } else {
                        appConfig.setFacebookAccountWeb(response.body().getMessage2());
                        appConfig.setFacebookAccount(response.body().toString());
                    }
                    realm.commitTransaction();
                    realm.close();
                    results.setValue(UserRepository.APIResponse.SUCCESS);
                } else {
                    results.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                results.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return results;
    }

    public String[] getFacebook() {
        Realm realm = Realm.getDefaultInstance();
        String results[] = new String[2];
        results[0] = realm.where(AppConfig.class).findFirst().getFacebookAccount();
        results[1] = realm.where(AppConfig.class).findFirst().getFacebookAccountWeb();
        realm.close();
        return results;
    }

    public MutableLiveData<UserRepository.APIResponse> loadTwitter() {
        MutableLiveData<UserRepository.APIResponse> results = new MutableLiveData<>();
        configAPI.getTwitterAccount().enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    AppConfig appConfig = realm.where(AppConfig.class).findFirst();
                    if (appConfig == null) {
                        appConfig = new AppConfig();
                        appConfig.setTwitterAccount(response.body().toString());
                        appConfig.setTwitterAccountWeb(response.body().getMessage2());
                        realm.copyToRealmOrUpdate(appConfig);
                    } else {
                        appConfig.setTwitterAccount(response.body().toString());
                        appConfig.setTwitterAccountWeb(response.body().getMessage2());
                    }
                    realm.commitTransaction();
                    realm.close();
                    results.setValue(UserRepository.APIResponse.SUCCESS);
                } else {
                    results.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                results.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return results;
    }

    public String[] getTwitter() {
        Realm realm = Realm.getDefaultInstance();
        String results[] = new String[2];
        results[0] = realm.where(AppConfig.class).findFirst().getTwitterAccount();
        results[1] = realm.where(AppConfig.class).findFirst().getTwitterAccountWeb();
        realm.close();
        return results;
    }

    public MutableLiveData<UserRepository.APIResponse> loadInstagram() {
        MutableLiveData<UserRepository.APIResponse> results = new MutableLiveData<>();
        configAPI.getInstagramAccount().enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    AppConfig appConfig = realm.where(AppConfig.class).findFirst();
                    if (appConfig == null) {
                        appConfig = new AppConfig();
                        appConfig.setInstagramAccount(response.body().toString());
                        appConfig.setInstagramAccountWeb(response.body().getMessage2());
                        realm.copyToRealmOrUpdate(appConfig);
                    } else {
                        appConfig.setInstagramAccount(response.body().toString());
                        appConfig.setInstagramAccountWeb(response.body().getMessage2());
                    }
                    realm.commitTransaction();
                    realm.close();
                    results.setValue(UserRepository.APIResponse.SUCCESS);
                } else {
                    results.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                results.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return results;
    }

    public String[] getInstagram() {
        Realm realm = Realm.getDefaultInstance();
        String results[] = new String[2];
        results[0] = realm.where(AppConfig.class).findFirst().getInstagramAccount();
        results[1] = realm.where(AppConfig.class).findFirst().getInstagramAccountWeb();
        realm.close();
        return results;
    }

    public MutableLiveData<UserRepository.APIResponse> loadAboutUs() {
        MutableLiveData<UserRepository.APIResponse> results = new MutableLiveData<>();
        configAPI.getCompanyAbout().enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    AppConfig appConfig = realm.where(AppConfig.class).findFirst();
                    if (appConfig == null) {
                        appConfig = new AppConfig();
                        appConfig.setAboutUs(response.body().toString());
                        realm.copyToRealmOrUpdate(appConfig);
                    } else {
                        appConfig.setAboutUs(response.body().toString());
                    }
                    realm.commitTransaction();
                    realm.close();
                    results.setValue(UserRepository.APIResponse.SUCCESS);
                } else {
                    results.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                results.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return results;
    }

    public String getAboutUs() {
        Realm realm = Realm.getDefaultInstance();
        String results = realm.where(AppConfig.class).findFirst().getAboutUs();
        realm.close();
        return results;
    }

    public MutableLiveData<UserRepository.APIResponse> loadContactUs() {
        MutableLiveData<UserRepository.APIResponse> results = new MutableLiveData<>();
        configAPI.getCompanyContact().enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    AppConfig appConfig = realm.where(AppConfig.class).findFirst();
                    if (appConfig == null) {
                        appConfig = new AppConfig();
                        appConfig.setContactUs(response.body().toString());
                        realm.copyToRealmOrUpdate(appConfig);
                    } else {
                        appConfig.setContactUs(response.body().toString());
                    }
                    realm.commitTransaction();
                    realm.close();
                    results.setValue(UserRepository.APIResponse.SUCCESS);
                } else {
                    results.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                results.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return results;
    }

    public String getContactUs() {
        Realm realm = Realm.getDefaultInstance();
        String results = realm.where(AppConfig.class).findFirst().getContactUs();
        realm.close();
        return results;
    }

}
