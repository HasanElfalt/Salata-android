package com.ar.salata.ui.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.User;
import com.ar.salata.ui.activities.AddToCartActivity;
import com.ar.salata.viewmodels.AddressViewModel;
import com.ar.salata.viewmodels.UserViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private static final int DIALOGREQUESTCODE = 1;
    public static final String USER_ID = "UserId";
    private ExtendedFloatingActionButton eFABWeigh;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private boolean userLoggedIn = false;
    private UserViewModel userViewModel;
    private AddressViewModel addressViewModel;


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if (requestCode == DIALOGREQUESTCODE && resultCode == RESULT_OK) {
            Intent intent = new Intent(getContext(), AddToCartActivity.class);
            intent.putExtras(data);
            intent.putExtra(USER_ID, userViewModel.getUser().getId());
            startActivity(intent);
        }
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) return null;

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);

        View view = inflater.inflate(R.layout.fragment_home, container);

        eFABWeigh = view.findViewById(R.id.efab_weigh);

        eFABWeigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = userViewModel.getUser();
                if (user != null) {
                    LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
                    loadingDialogFragment.show(getActivity().getSupportFragmentManager(), null);

                    MutableLiveData<UserRepository.APIResponse> addressResponse = addressViewModel.loadAddresses(userViewModel.getToken());
                    addressResponse.observe(HomeFragment.this.getActivity(), new Observer<UserRepository.APIResponse>() {
                        @Override
                        public void onChanged(UserRepository.APIResponse apiResponse) {
                            switch (apiResponse) {
                                case ERROR: {
                                    loadingDialogFragment.dismiss();
                                    ErrorDialogFragment dialogFragment =
                                            new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل بيانات المستخدم", false);
                                    dialogFragment.show(HomeFragment.this.getActivity().getSupportFragmentManager(), null);
                                    break;
                                }
                                case FAILED: {
                                    loadingDialogFragment.dismiss();
                                    ErrorDialogFragment dialogFragment =
                                            new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), false);
                                    dialogFragment.show(HomeFragment.this.getActivity().getSupportFragmentManager(), null);
                                    break;
                                }
                                case SUCCESS: {
                                    loadingDialogFragment.dismiss();
                                    ChooseAddressDialogFragment dialogFragment = new ChooseAddressDialogFragment(userViewModel.getUser().getAddresses());
                                    dialogFragment.show(getActivity().getSupportFragmentManager(), null);
                                    dialogFragment.setTargetFragment(HomeFragment.this, DIALOGREQUESTCODE);
                                    break;
                                }
                            }
                        }
                    });
                } else {
                    ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment(
                            "انت لست مسجلا",
                            "من فضلك سجل الدخول او قم باضافة مستخدم جديد",
                            false);
                    errorDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                }
            }
		});
		
		toolbar = view.findViewById(R.id.toolbar_home);
		((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
		
		setDrawer();
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
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
	
	private void setDrawer() {
		navigationView = getActivity().findViewById(R.id.nav_view);
		
		navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getActivity());

        drawer = getActivity().findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));
        toggle.syncState();

    }

    public void setEFABVisibility(boolean isVisible) {
        if (isVisible)
            eFABWeigh.show();
        else
            eFABWeigh.hide();
    }
}
