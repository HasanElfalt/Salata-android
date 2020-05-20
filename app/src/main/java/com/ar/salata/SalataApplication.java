package com.ar.salata;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SalataApplication extends Application {
	public final static String BASEURL = "http://192.168.1.200:8000/";
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Realm.init(this);
		RealmConfiguration defaultConfiguration = new RealmConfiguration.Builder()
				.deleteRealmIfMigrationNeeded()
				.name("SalataDB.realm")
				.build();
		Realm.setDefaultConfiguration(defaultConfiguration);
	}
}
