package com.utp.testinnp.app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.utp.testinnp.R;
import com.utp.testinnp.adapter.AdapterViewPager;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager2 pagerMain;
    ArrayList<Fragment> fragmentArrayList =new ArrayList<>();
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        pagerMain=findViewById(R.id.pagerMain);
        bottomNav=findViewById(R.id.bottomNav);
        fragmentArrayList.add(new FragmentHome());
        fragmentArrayList.add(new FragmentFavoritos());
        fragmentArrayList.add(new FragmentUsuario());

        AdapterViewPager adapterViewPager=new AdapterViewPager(this,fragmentArrayList);
        //setAdapter
        pagerMain.setAdapter(adapterViewPager);
        pagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNav.setSelectedItemId(R.id.itHome);
                        break;
                    case 1:
                        bottomNav.setSelectedItemId(R.id.itFavoritos);
                        break;
                    case 2:
                        bottomNav.setSelectedItemId(R.id.ituSUARIO);
                        break;
                }
                super.onPageSelected(position);
            }
        });
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.itHome:
                        pagerMain.setCurrentItem(0);
                        break;
                    case  R.id.itFavoritos:
                        pagerMain.setCurrentItem(1);
                        break;
                    case  R.id.ituSUARIO:
                        pagerMain.setCurrentItem(2);
                        break;

                }
                return  false;
            }
        });

    }
}

