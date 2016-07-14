package com.epresidential.quandoodstest.ui;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.epresidential.quandoodstest.adapter.GridItemAdapter;
import com.epresidential.quandoodstest.R;
import com.epresidential.quandoodstest.utils.Constants;
import com.epresidential.quandoodstest.utils.PatternUtils;

public class MainActivity extends BaseMainActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replace(new GolFragment(), false);
    }
}