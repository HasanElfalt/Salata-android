package com.ar.salata.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.User;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.ui.fragments.HomeFragment;
import com.ar.salata.viewmodels.AddressViewModel;
import com.ar.salata.viewmodels.UserViewModel;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	private DrawerLayout drawer;
	private ActionBarDrawerToggle toggle;
	private Toolbar toolbar;
	private NavigationView navigationView;
	private FragmentManager fragmentManager;
	private HomeFragment homeFragment;
	private UserViewModel userViewModel;
	private AddressViewModel addressViewModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
		addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
		
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
				signOut();
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
	
	private void signOut() {
		MutableLiveData<UserRepository.APIResponse> response = userViewModel.signOut(userViewModel.getToken());
		response.observe(this, new Observer<UserRepository.APIResponse>() {
			@Override
			public void onChanged(UserRepository.APIResponse apiResponse) {
				switch (apiResponse) {
					case SUCCESS:
						finish();
						startActivity(getIntent());
						break;
					case FAILED: {
						ErrorDialogFragment dialogFragment =
								new ErrorDialogFragment("حدث خطأ", "يوجد خطا فى الاتصال بالانترنت");
						dialogFragment.show(getSupportFragmentManager(), null);
						break;
					}
					case ERROR: {
						ErrorDialogFragment dialogFragment =
								new ErrorDialogFragment("حدث خطأ", "فشلت عملية تسجيل الخروج");
						dialogFragment.show(getSupportFragmentManager(), null);
						break;
					}
					case NULL:
						// TODO: 5/19/2020
						break;
				}
			}
		});
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
			
			if (userViewModel.getUser() == null && userViewModel.getToken() == null) {
				showLoginHeader();
			} else if ((userViewModel.getUser() == null) ||
					!(userViewModel.getUser().getToken()).equals(userViewModel.getToken().toString())) {
				MutableLiveData<UserRepository.APIResponse> userResponse = userViewModel.getUser(userViewModel.getToken());
				//MutableLiveData<UserRepository.APIResponse> addressResponse = AddressViewModel.getAddress(userViewModel.getToken());
				
				userResponse.observe(this, new Observer<UserRepository.APIResponse>() {
					@Override
					public void onChanged(UserRepository.APIResponse apiResponse) {
						switch (apiResponse) {
							case NULL:
								// TODO: 5/18/2020
								break;
							case ERROR: {
								ErrorDialogFragment dialogFragment =
										new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل بيانات المستخدم");
								dialogFragment.show(getSupportFragmentManager(), null);
								break;
							}
							case FAILED: {
								ErrorDialogFragment dialogFragment =
										new ErrorDialogFragment("حدث خطأ", "يوجد خطا فى الاتصال بالانترنت");
								dialogFragment.show(getSupportFragmentManager(), null);
								break;
							}
							case SUCCESS:
								showProfileHeader();
								break;
						}
					}
				});
				
			} else {
				showProfileHeader();
			}
		}
	}
	
	private void showLoginHeader() {
		navigationView.getMenu().findItem(R.id.nav_sign_out).setVisible(false);
		navigationView.getMenu().findItem(R.id.nav_orders).setVisible(false);
		navigationView.getMenu().findItem(R.id.nav_settings).setVisible(false);
		navigationView.getMenu().findItem(R.id.nav_address).setVisible(false);
		navigationView.getHeaderView(0).findViewById(R.id.btn_sign_in).setVisibility(View.VISIBLE);
		navigationView.getHeaderView(0).findViewById(R.id.nav_profile).setVisibility(View.GONE);
	}
	
	private void showProfileHeader() {
		User user = userViewModel.getUser();
		navigationView.getHeaderView(0).findViewById(R.id.btn_sign_in).setVisibility(View.GONE);
		View view = navigationView.getHeaderView(0).findViewById(R.id.nav_profile);
		view.setVisibility(View.VISIBLE);
		((TextView) view.findViewById(R.id.header_nav_username)).setText(user.getName());
		((TextView) view.findViewById(R.id.header_nav_phone_number)).setText(user.getPhoneNumber());
		navigationView.getMenu().findItem(R.id.nav_sign_out).setVisible(true);
		navigationView.getMenu().findItem(R.id.nav_orders).setVisible(true);
		navigationView.getMenu().findItem(R.id.nav_settings).setVisible(true);
		navigationView.getMenu().findItem(R.id.nav_address).setVisible(true);
	}
	
	public void setEFABVisiblity(boolean isVisible) {
		homeFragment.setEFABVisiblity(isVisible);
	}
}
