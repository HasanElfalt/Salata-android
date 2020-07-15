package com.ar.salata.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.AuthenticationUser;
import com.ar.salata.repositories.model.User;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository = new UserRepository();
    private MutableLiveData<UserRepository.Authentication> authenticationStateObservable;

    public UserViewModel() {

    }

    public void registerUser(AuthenticationUser user) {
        authenticationStateObservable = userRepository.registerUser(user);
    }

    public void loginUser(AuthenticationUser user) {
        authenticationStateObservable = userRepository.loginUser(user);
    }

    public MutableLiveData<UserRepository.Authentication> getAuthenticationStateObservable() {
        return authenticationStateObservable;
    }

    public User getUser() {
        return userRepository.getUser();
    }

    public MutableLiveData<UserRepository.APIResponse> getUser(APIToken token) {
        return userRepository.getUser(token);
    }

    public APIToken getToken() {
        return userRepository.getToken();
    }

    public MutableLiveData<UserRepository.APIResponse> signOut(APIToken token) {
        return userRepository.signOut(token);
    }

    public MutableLiveData<UserRepository.APIResponse> updateUser(APIToken token, String name, String phone) {
        return userRepository.updateUser(token, name, phone);
    }

    public MutableLiveData<UserRepository.APIResponse> updatePassword(APIToken token, String currentPassword, String password, String passwordConfirmation) {
        return userRepository.updatePassword(token, currentPassword, password, passwordConfirmation);
    }

    public void clearUser() {
        userRepository.clearUserDate();
    }
}
