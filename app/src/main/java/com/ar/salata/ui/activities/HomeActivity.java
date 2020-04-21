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
import com.ar.salata.ui.fragments.HomeFragmet;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private int clickedNavItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        setDrawer();

        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragmet homeFragmet = HomeFragmet.newInstance();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_home, homeFragmet)
                .commit();

    }

    private void setDrawer() {
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getHeaderView(0).findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedNavItem = R.id.btn_sign_in;
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        drawer = findViewById(R.id.drawer);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Intent intent = null;
                switch (clickedNavItem) {
                    case R.id.nav_main:
                        // TODO: 3/30/2020
                        break;
                    case R.id.nav_orders:
                        // TODO: 3/30/2020
                        break;
                    case R.id.nav_settings:
                        // TODO: 3/30/2020
                        break;
                    case R.id.nav_address:
                        intent = new Intent(HomeActivity.this, AddAddressActivity.class);
                        break;
                    case R.id.nav_register:
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
                    default:
                        // TODO: 3/30/2020
                }
                if (intent != null) {
                    startActivity(intent);
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

                clickedNavItem = 0;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

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
        clickedNavItem = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
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
}
