package com.ar.salata.ui.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
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

import com.ar.salata.R;
import com.ar.salata.model.SliderItem;
import com.ar.salata.ui.activities.ReceiptActivity;
import com.ar.salata.ui.adapters.ImageSliderAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class HomeFragmet extends Fragment {
    private ExtendedFloatingActionButton eFABWeigh;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;


    public static HomeFragmet newInstance() {

        Bundle args = new Bundle();

        HomeFragmet fragment = new HomeFragmet();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container);


        eFABWeigh = view.findViewById(R.id.efab_weigh);

        eFABWeigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReceiptActivity.class);
                startActivity(intent);
            }
        });

        toolbar = view.findViewById(R.id.toolbar_home);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        setDrawer();


        SliderView sliderView = view.findViewById(R.id.imageSlider);

        ImageSliderAdapter adapter = new ImageSliderAdapter(getContext());

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        adapter.addItem(new SliderItem("https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823_960_720.jpg",
                "road forest"));
        adapter.addItem(new SliderItem("https://cdn.pixabay.com/photo/2015/12/01/20/28/green-1072828_960_720.jpg",
                "green forest"));
        adapter.addItem(new SliderItem("https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510_960_720.jpg",
                "flower"));


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_main:
                Toast.makeText(getContext(), "Home page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_orders:
                Toast.makeText(getContext(), "My Orders page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_settings:
                Toast.makeText(getContext(), "Settings page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_address:
                Toast.makeText(getContext(), "Add Address requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_about:
                Toast.makeText(getContext(), "About page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_contact:
                Toast.makeText(getContext(), "Contact Us page requested...", Toast.LENGTH_LONG).show();
                // TODO: 3/30/2020
                break;
            case R.id.nav_facebook:
                Toast.makeText(getContext(), "Facebook page requested...", Toast.LENGTH_LONG).show();
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
*/

    /*@Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/


}
