package com.ar.salata.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.ar.salata.R;
import com.ar.salata.ui.fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	private DrawerLayout drawer;
	private ActionBarDrawerToggle toggle;
	private Toolbar toolbar;
	private NavigationView navigationView;
	private FragmentManager fragmentManager;
	private HomeFragment homeFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home);
		getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
		
		toolbar = findViewById(R.id.toolbar_home);
		setSupportActionBar(toolbar);
		
		setDrawer();
		
		fragmentManager = getSupportFragmentManager();
		homeFragment = HomeFragment.newInstance();
		
		fragmentManager.beginTransaction()
				.add(R.id.fragment_container_home, homeFragment)
				.commit();
		
	}
	
	private void setDrawer() {
		navigationView = findViewById(R.id.nav_view);
		
		navigationView.setNavigationItemSelectedListener(this);
		
		navigationView.getHeaderView(0).findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
				startActivity(intent);
			}
		});
		
		drawer = findViewById(R.id.drawer);
		
		toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		
		toggle.setDrawerIndicatorEnabled(true);
		toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));
		toggle.syncState();
		
	}
	
	@Override
	public void onConfigurationChanged(@NonNull Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		toggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (toggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
			case R.id.nav_main:
				drawer.closeDrawer(GravityCompat.START);
				break;
			case R.id.nav_orders:
				intent = new Intent(HomeActivity.this, OrdersActivity.class);
				break;
			case R.id.nav_settings:
				// TODO: 3/30/2020
				break;
			case R.id.nav_address:
				intent = new Intent(HomeActivity.this, AddAddressActivity.class);
				break;
			case R.id.nav_sign_up:
				intent = new Intent(HomeActivity.this, SignUpActivity.class);
				break;
			case R.id.nav_about:
				intent = new Intent(HomeActivity.this, AboutActivity.class);
				break;
			case R.id.nav_contact:
				intent = new Intent(HomeActivity.this, ContactUsActivity.class);
				break;
			case R.id.nav_facebook:
				// TODO: 3/30/2020
				break;
			case R.id.nav_twitter:
				// TODO: 3/30/2020
				break;
			case R.id.nav_instagram:
				// TODO: 3/30/2020
				break;
			case R.id.btn_sign_in:
				intent = new Intent(HomeActivity.this, SignInActivity.class);
				break;
			case R.id.nav_sign_out:
				drawer.closeDrawer(GravityCompat.START);
				break;
			default:
				// TODO: 3/30/2020
		}
		if (intent != null) {
			startActivity(intent);
		}
		navigationView.setCheckedItem(item);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (drawer != null) {
			navigationView.setCheckedItem(R.id.nav_main);
			drawer.closeDrawer(GravityCompat.START);
		}
	}
	
	public void setEFABVisiblity(boolean isVisible) {
		homeFragment.setEFABVisiblity(isVisible);
	}
}
