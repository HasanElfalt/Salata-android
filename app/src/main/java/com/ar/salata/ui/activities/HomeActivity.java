package com.ar.salata.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.ar.salata.R;
import com.ar.salata.ui.fragments.HomeFragmet;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ExtendedFloatingActionButton eFABWeigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        setDrawer();

        eFABWeigh = findViewById(R.id.efab_weigh);

        eFABWeigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReceiptActivity.class);
                startActivity(intent);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragmet homeFragmet = HomeFragmet.newInstance();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_home, homeFragmet)
                .commit();

    }

    private void setDrawer() {
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

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
        switch (item.getItemId()) {
            case R.id.nav_main:
                Toast.makeText(getApplicationContext(), "Home page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_orders:
                Toast.makeText(getApplicationContext(), "My Orders page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_settings:
                Toast.makeText(getApplicationContext(), "Settings page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_address:
                Toast.makeText(getApplicationContext(), "Add Address requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_about:
                Toast.makeText(getApplicationContext(), "About page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_contact:
                Toast.makeText(getApplicationContext(), "Contact Us page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_facebook:
                Toast.makeText(getApplicationContext(), "Facebook page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_twitter:
                Toast.makeText(getApplicationContext(), "Twitter page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_instagram:
                Toast.makeText(getApplicationContext(), "Instagram page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            default:
                Toast.makeText(getApplicationContext(), "No page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
        }

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
