package com.ar.salata.ui.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.salata.R;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.ui.adapters.AddressRecyclerAdapter;
import com.ar.salata.ui.fragments.ErrorDialogFragment;
import com.ar.salata.ui.fragments.LoadingDialogFragment;
import com.ar.salata.ui.utils.ArabicString;
import com.ar.salata.viewmodels.AddressViewModel;
import com.ar.salata.viewmodels.UserViewModel;

public class UserProfileActivity extends BaseActivity {

    private TextView usernameTextView;
    private TextView phoneTextView;
    private ImageView userEditImageView;
    private ImageView passwordEditImageView;
    private UserViewModel userViewModel;
    private RecyclerView addressRecyclerView;
    private AddressViewModel addressViewModel;
    private MutableLiveData<UserRepository.APIResponse> addressDeleteResponse;
    private AddressRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usernameTextView = findViewById(R.id.userNameTextView);
        phoneTextView = findViewById(R.id.userPhoneNumberTextView);
        userEditImageView = findViewById(R.id.userEditIcon);
        passwordEditImageView = findViewById(R.id.userPasswordIcon);
        addressRecyclerView = findViewById(R.id.rv_address);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);

        usernameTextView.setText(userViewModel.getUser().getName());
        phoneTextView.setText(ArabicString.toArabic(userViewModel.getUser().getPhoneNumber()));

        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressRecyclerAdapter(this);
        addressRecyclerView.setAdapter(adapter);

        userEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UpdateUserActivity.class);
                startActivity(intent);
            }
        });
        passwordEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.show(getSupportFragmentManager(), null);

        MutableLiveData<UserRepository.APIResponse> addressResponse = addressViewModel.loadAddresses(userViewModel.getToken());
        addressResponse.observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse apiResponse) {
                switch (apiResponse) {
                    case ERROR: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل بيانات المستخدم", true);
                        dialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                    case FAILED: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), true);
                        dialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                    case SUCCESS: {
                        loadingDialogFragment.dismiss();
                        adapter.setData(userViewModel.getUser().getAddresses());
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });

    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_user_profile);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_user_profile;
    }

    public void deleteAddress(int addressId) {
        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.show(getSupportFragmentManager(), null);
        addressDeleteResponse = addressViewModel.deleteAddress(userViewModel.getToken(), addressId);
        addressDeleteResponse.observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse response) {
                switch (response) {
                    case SUCCESS:
                        loadingDialogFragment.dismiss();
                        adapter.setData(userViewModel.getUser().getAddresses());
                        adapter.notifyDataSetChanged();
                        break;
                    case ERROR:
                    case FAILED:
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment("خطأ", "لم يتم حذف العنوان", false);
                        errorDialogFragment.show(getSupportFragmentManager(), null);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (userViewModel.getToken() == null) {
            finish();
            return;
        }


        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.show(getSupportFragmentManager(), null);

        MutableLiveData<UserRepository.APIResponse> userResponse = userViewModel.getUser(userViewModel.getToken());

        userResponse.observe(this, new Observer<UserRepository.APIResponse>() {
            @Override
            public void onChanged(UserRepository.APIResponse apiResponse) {
                switch (apiResponse) {
                    case ERROR: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", "فشلت عملية تحميل بيانات المستخدم", false);
                        dialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                    case FAILED: {
                        loadingDialogFragment.dismiss();
                        ErrorDialogFragment dialogFragment =
                                new ErrorDialogFragment("حدث خطأ", getResources().getString(R.string.server_connection_error), false);
                        dialogFragment.show(getSupportFragmentManager(), null);
                        break;
                    }
                    case SUCCESS:
                        if (usernameTextView != null && phoneTextView != null) {
                            usernameTextView.setText(userViewModel.getUser().getName());
                            phoneTextView.setText(ArabicString.toArabic(userViewModel.getUser().getPhoneNumber()));
                        }
                        loadingDialogFragment.dismiss();
                        break;
                }
            }
        });
    }

}
