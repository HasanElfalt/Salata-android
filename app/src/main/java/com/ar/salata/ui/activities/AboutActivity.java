package com.ar.salata.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.ar.salata.R;
import com.ar.salata.viewmodels.AppConfigViewModel;

public class AboutActivity extends BaseActivity {
    private AppCompatTextView textView;
    private AppConfigViewModel appConfigViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = findViewById(R.id.tv_about_text);

        appConfigViewModel = new AppConfigViewModel();
        textView.setText(appConfigViewModel.getAboutUs());
    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_about);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_about;
    }
}
