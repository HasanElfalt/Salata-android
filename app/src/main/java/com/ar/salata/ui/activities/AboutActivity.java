package com.ar.salata.ui.activities;

import androidx.appcompat.widget.Toolbar;

import com.ar.salata.R;

public class AboutActivity extends BaseActivity {

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_about);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_about;
    }
}
