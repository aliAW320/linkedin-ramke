package com.example.test;

import android.os.Bundle;
import android.widget.FrameLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class mainScreenActivity2 extends AppCompatActivity {

    private FrameLayout frameLayout;
    private TabLayout tabLayout;
    private static FragmentManager staticFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        frameLayout = findViewById(R.id.frameLayout);
        tabLayout = findViewById(R.id.table);

        staticFragmentManager = getSupportFragmentManager();

        // Set default fragment
        setFragment(new HomeFragment());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        selectedFragment = new HomeFragment();
                        break;
                    case 1:
                        selectedFragment = new connectionFragment();
                        break;
                    case 2:
                        selectedFragment = new SearchFragment();
                        break;
                    case 3:
                        selectedFragment = new MessageUserListFragment();
                        break;
                    case 4:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                setFragment(selectedFragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = staticFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public static void changeFragment(Fragment fragment) {
        if (staticFragmentManager != null) {
            FragmentTransaction fragmentTransaction = staticFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.commit();
        }
    }
}
