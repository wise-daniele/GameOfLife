package com.epresidential.quandoodstest.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.epresidential.quandoodstest.R;

/**
 * Created by daniele on 14/07/16.
 */
public class BaseMainActivity extends AppCompatActivity implements MainFragmentManager {


    @Override
    public int replace(Fragment fragment, boolean addToBackStack) {
        String currentVisibleFragmentTag;
        currentVisibleFragmentTag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.game_container, fragment, currentVisibleFragmentTag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        return fragmentTransaction.commitAllowingStateLoss();
    }

}
