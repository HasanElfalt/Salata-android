package com.ar.salata.ui.activities;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.ar.salata.R;
import com.ar.salata.viewmodels.AppConfigViewModel;

public class ContactUsActivity extends BaseActivity {
    private AppCompatTextView textView;
    private AppConfigViewModel appConfigViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = findViewById(R.id.tv_contactUs_text);

        appConfigViewModel = new AppConfigViewModel();
        textView.setText(appConfigViewModel.getContactUs());
    }

    @Override
    Toolbar deliverToolBar() {
        return findViewById(R.id.toolbar_contact_us);
    }

    @Override
    int deliverLayout() {
        return R.layout.activity_contact_us;
    }

}
