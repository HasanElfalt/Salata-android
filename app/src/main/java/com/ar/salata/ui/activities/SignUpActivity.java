package com.ar.salata.ui.activities;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.ar.salata.R;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_sign_up);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_sign_up;
    }
}
