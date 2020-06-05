package com.ar.salata.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ar.salata.repositories.API.AddressAPI;
import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.AddressList;
import com.ar.salata.repositories.model.City;
import com.ar.salata.repositories.model.CityList;
import com.ar.salata.repositories.model.ResponseMessage;
import com.ar.salata.repositories.model.Town;
import com.ar.salata.repositories.model.TownList;
import com.ar.salata.repositories.model.User;
import com.ar.salata.repositories.model.UserAddress;
import com.ar.salata.repositories.model.Zone;
import com.ar.salata.repositories.model.ZoneList;

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

public class AddressRepository {
	private static final String TAG = "AddressRepository";
	private AddressAPI addressAPI;
	
	public AddressRepository() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASEURL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();
		
		addressAPI = retrofit.create(AddressAPI.class);
	}
	
	public MutableLiveData<UserRepository.APIResponse> loadCities() {
		final MutableLiveData<UserRepository.APIResponse> apiResponse = new MutableLiveData<>();
		addressAPI.getAllCities().enqueue(new Callback<CityList>() {
			@Override
			public void onResponse(Call<CityList> call, Response<CityList> response) {
				if (response.isSuccessful()) {
					CityList cities = response.body();
					Realm realm = Realm.getDefaultInstance();
					realm.executeTransaction(new Realm.Transaction() {
						@Override
						public void execute(Realm realm) {
							realm.insertOrUpdate(cities.getCities());
						}
					});
					realm.close();
					apiResponse.setValue(UserRepository.APIResponse.SUCCESS);
				} else {
					apiResponse.setValue(UserRepository.APIResponse.ERROR);
				}
			}
			
			@Override
			public void onFailure(Call<CityList> call, Throwable t) {
				Log.d(TAG, "onFailure: " + t.getMessage());
				apiResponse.setValue(UserRepository.APIResponse.FAILED);
			}
		});
		/*
			Realm realm = Realm.getDefaultInstance();
			CityList cityList = new CityList(realm.copyFromRealm(realm.where(City.class).findAll()));
			cities.setValue(cityList);
			realm.close();*/
		
		return apiResponse;
	}
	
	public MutableLiveData<UserRepository.APIResponse> loadTowns() {
		final MutableLiveData<UserRepository.APIResponse> apiResponse = new MutableLiveData<>();
		addressAPI.getAllTowns().enqueue(new Callback<TownList>() {
			@Override
			public void onResponse(Call<TownList> call, Response<TownList> response) {
				if (response.isSuccessful()) {
					TownList towns = response.body();
					Realm realm = Realm.getDefaultInstance();
					realm.executeTransaction(new Realm.Transaction() {
						@Override
						public void execute(Realm realm) {
							realm.insertOrUpdate(towns.getTowns());
						}
					});
					realm.close();
					apiResponse.setValue(UserRepository.APIResponse.SUCCESS);
				} else {
					apiResponse.setValue(UserRepository.APIResponse.ERROR);
				}
			}
			
			@Override
			public void onFailure(Call<TownList> call, Throwable t) {
				Log.d(TAG, "onFailure: " + t.getMessage());
				apiResponse.setValue(UserRepository.APIResponse.FAILED);
			}
		});
/*
			Realm realm = Realm.getDefaultInstance();
			TownList townList = new TownList(realm.copyFromRealm(realm.where(Town.class).findAll()));
			towns.setValue(townList);
			realm.close();
*/
		return apiResponse;
	}
	
	public MutableLiveData<UserRepository.APIResponse> loadZones() {
		final MutableLiveData<UserRepository.APIResponse> apiResponse = new MutableLiveData<>();
		addressAPI.getAllZones().enqueue(new Callback<ZoneList>() {
			@Override
			public void onResponse(Call<ZoneList> call, Response<ZoneList> response) {
				if (response.isSuccessful()) {
					ZoneList zones = response.body();
					Realm realm = Realm.getDefaultInstance();
					realm.executeTransaction(new Realm.Transaction() {
						@Override
						public void execute(Realm realm) {
							realm.insertOrUpdate(zones.getZones());
						}
					});
					realm.close();
					apiResponse.setValue(UserRepository.APIResponse.SUCCESS);
				} else {
					apiResponse.setValue(UserRepository.APIResponse.ERROR);
				}
			}
			
			@Override
			public void onFailure(Call<ZoneList> call, Throwable t) {
				Log.d(TAG, "onFailure: " + t.getMessage());
				apiResponse.setValue(UserRepository.APIResponse.FAILED);
			}
		});
/*
			Realm realm = Realm.getDefaultInstance();
			ZoneList zoneList = new ZoneList(realm.copyFromRealm(realm.where(Zone.class).findAll()));
			zones.setValue(zoneList);
			realm.close();
*/
		return apiResponse;
	}
	
	public List<Town> getTowns(int cityId) {
		Realm realm = Realm.getDefaultInstance();
		List<Town> towns = realm.copyFromRealm(realm.where(Town.class).equalTo("cityId", cityId).findAll());
		realm.close();
		return towns;
	}
	
	public List<City> getCities() {
		Realm realm = Realm.getDefaultInstance();
		List<City> cities = realm.copyFromRealm(realm.where(City.class).findAll());
		realm.close();
		return cities;
	}
	
	public List<Zone> getZones(int townId) {
		Realm realm = Realm.getDefaultInstance();
		List<Zone> zones = realm.copyFromRealm(realm.where(Zone.class).equalTo("townId", townId).findAll());
		realm.close();
		return zones;
	}
	
	public MutableLiveData<UserRepository.APIResponse> addAddress(APIToken token, String address, int zoneId) {
		MutableLiveData<UserRepository.APIResponse> apiResponse = new MutableLiveData<>(UserRepository.APIResponse.NULL);
		addressAPI.addAddress(token.toString(), address, zoneId).enqueue(new Callback<ResponseMessage>() {
			@Override
			public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
				if (response.isSuccessful()) {
					Realm realm = Realm.getDefaultInstance();
					realm.executeTransaction(new Realm.Transaction() {
						@Override
						public void execute(Realm realm) {
                            User user = realm.where(User.class).findFirst();
                            user.getAddresses().add(new UserAddress(address, zoneId));
                        }
					});
					realm.close();
					apiResponse.setValue(UserRepository.APIResponse.SUCCESS);
				} else {
					apiResponse.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                apiResponse.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return apiResponse;
    }

    public MutableLiveData<UserRepository.APIResponse> loadAddresses(APIToken token) {
        MutableLiveData<UserRepository.APIResponse> apiResponse = new MutableLiveData<>(UserRepository.APIResponse.NULL);
        addressAPI.getAddresses(token.toString()).enqueue(new Callback<AddressList>() {
            @Override
            public void onResponse(Call<AddressList> call, Response<AddressList> response) {
                if (response.isSuccessful()) {
                    AddressList list = response.body();
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.where(User.class).findFirst().setAddresses(
                                    list.getAddresses());
                        }
                    });
                    realm.close();
                    apiResponse.setValue(UserRepository.APIResponse.SUCCESS);
                } else {
                    apiResponse.setValue(UserRepository.APIResponse.ERROR);
                }
            }

            @Override
            public void onFailure(Call<AddressList> call, Throwable t) {
                apiResponse.setValue(UserRepository.APIResponse.FAILED);
            }
        });
        return apiResponse;
    }
}
