package com.epresidential.quandoodstest.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.epresidential.quandoodstest.R;
import com.epresidential.quandoodstest.adapter.GridItemAdapter;
import com.epresidential.quandoodstest.utils.Constants;
import com.epresidential.quandoodstest.utils.PatternUtils;

/**
 * Created by daniele on 14/07/16.
 */
public class GolFragment extends Fragment implements GoLFragmentManager {

    private static final String LOG_TAG = GolFragment.class.getSimpleName();

    private GridView mGridField;
    private GridItemAdapter mGridAdapter;
    private int[][] mCurrentPattern;
    private Button mGeneratePatternButton;
    private Button mStartStopButton;
    private TextView mTickStep;
    private UpdateGridTask mUpdateGridTask;
    private boolean mIsPatternActive;
    private MenuItem switchMenuItem;
    private int mGridSize;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGridSize = getActivity().getResources().getInteger(R.integer.grid_size);
        mIsPatternActive = false;
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gol, container, false);
        mGeneratePatternButton = (Button) view.findViewById(R.id.generate_pattern_button);
        mStartStopButton = (Button) view.findViewById(R.id.start_stop_button);
        mTickStep = (TextView) view.findViewById(R.id.tick_step);
        mGridField = (GridView) view.findViewById(R.id.grid_field);
        mTickStep.setText("1");
        mCurrentPattern = changePattern(Constants.NO_PATTERN);
        mGridAdapter = new GridItemAdapter(getActivity(), mGridField, mCurrentPattern);
        mGridField.setAdapter(mGridAdapter);
        mGridAdapter.notifyDataSetChanged();

        mGeneratePatternButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUpdateGridTask!= null && !mUpdateGridTask.isCancelled()){
                    mUpdateGridTask.cancel(true);
                    mIsPatternActive = false;
                }
                mCurrentPattern = changePattern(Constants.RANDOM_PATTERN);
                mGridAdapter.setPattern(mCurrentPattern);
                mGridAdapter.notifyDataSetChanged();
                mTickStep.setText("1");
                activateGrid(true);
                switchMenuItem.setVisible(true);
            }
        });

        mStartStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIsPatternActive){
                    mUpdateGridTask.cancel(true);
                    mIsPatternActive = false;
                    activateGrid(true);
                    switchMenuItem.setVisible(true);
                }
                else if(!isPatternDead(mCurrentPattern)){
                    activateGrid(false);
                    mUpdateGridTask = new UpdateGridTask(Integer.parseInt(mTickStep.getText().toString()));
                    mUpdateGridTask.execute();
                    mIsPatternActive = true;
                    switchMenuItem.setVisible(false);
                }
                else{
                    mTickStep.setText("1");
                    switchMenuItem.setVisible(true);
                }
            }
        });

        mGridField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int column = position%mGridSize;
                int row = position/mGridSize;
                FrameLayout myCell = (FrameLayout) view;
                int state = changeState(row, column);
                if(state == 1) {
                    if(android.os.Build.VERSION.SDK_INT > 23) {
                        myCell.setBackgroundColor(getActivity().getColor(R.color.black));
                    }
                    else {
                        myCell.setBackgroundResource(R.color.black);
                    }
                }
                else{
                    if(android.os.Build.VERSION.SDK_INT > 23) {
                        myCell.setBackgroundColor(getActivity().getColor(R.color.white));
                    }
                    else {
                        myCell.setBackgroundResource(R.color.white);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gol, menu);
        switchMenuItem = menu.findItem(R.id.action_switch_negative);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_clear_grid){
            if(mUpdateGridTask!= null && !mUpdateGridTask.isCancelled()){
                mUpdateGridTask.cancel(true);
                activateGrid(true);
            }
            mCurrentPattern = changePattern(Constants.NO_PATTERN);
            mGridAdapter.setPattern(mCurrentPattern);
            mGridAdapter.notifyDataSetChanged();
            mIsPatternActive = false;
            mTickStep.setText("1");
            switchMenuItem.setVisible(true);
            return true;
        }
        else if(id == R.id.action_random_pattern){
            if(mUpdateGridTask!= null && !mUpdateGridTask.isCancelled()){
                mUpdateGridTask.cancel(true);
                activateGrid(true);
            }
            mCurrentPattern = changePattern(Constants.RANDOM_PATTERN);
            mGridAdapter.setPattern(mCurrentPattern);
            mGridAdapter.notifyDataSetChanged();
            mIsPatternActive = false;
            mTickStep.setText("1");
            switchMenuItem.setVisible(true);
            return true;
        }
        else if(id == R.id.action_pulsar_pattern){
            if(mUpdateGridTask!= null && !mUpdateGridTask.isCancelled()){
                mUpdateGridTask.cancel(true);
                activateGrid(true);
            }
            mCurrentPattern = changePattern(Constants.PULSAR_PATTERN);
            mGridAdapter.setPattern(mCurrentPattern);
            mGridAdapter.notifyDataSetChanged();
            mIsPatternActive = false;
            mTickStep.setText("1");
            switchMenuItem.setVisible(true);
            return true;
        }
        else if(id == R.id.action_switch_negative){
            mCurrentPattern = PatternUtils.switchNegative(mCurrentPattern);
            mGridAdapter.setPattern(mCurrentPattern);
            mGridAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int changeState(int row, int column) {
        if(mCurrentPattern[row][column] == 0){
            mCurrentPattern[row][column] = 1;
            return 1;
        }
        else{
            mCurrentPattern[row][column] = 0;
            return 0;
        }
    }

    @Override
    public int[][] generateNextStep(int[][] currentStep) {
        return PatternUtils.nextStep(currentStep, getActivity());
    }

    @Override
    public boolean isPatternDead(int[][] currentStep) {
        return PatternUtils.isPatternDead(currentStep, getActivity());
    }

    @Override
    public void activateGrid(boolean active) {
        mGridField.setEnabled(active);
    }

    @Override
    public int[][] changePattern(int pattern) {
        int[][] newPattern = new int[mGridSize][mGridSize];
        switch (pattern){
            case Constants.NO_PATTERN:
                return PatternUtils.noPattern(getActivity());
            case Constants.RANDOM_PATTERN:
                return PatternUtils.randomPattern(getActivity());
            case Constants.PULSAR_PATTERN:
                return PatternUtils.pulsarPattern(getActivity());
        }
        return newPattern;
    }

    private class UpdateGridTask extends AsyncTask<Void, Object, Object[]> {
        private int mStep;

        public UpdateGridTask(int currentStep) {
            super();
            mStep = currentStep;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object[] doInBackground(Void... params) {
            boolean isPatternConstant = false;
            Object[] progress = new Object[2];
            progress[0] = mCurrentPattern;
            progress[1] = mStep;
            int[][] nextStep = generateNextStep(mCurrentPattern);
            if(nextStep == null){
                isPatternConstant = true;
            }
            if(isPatternDead(nextStep)){
                progress[0] = nextStep;
                progress[1] = ++mStep;
            }
            while (!isPatternConstant && !isPatternDead(nextStep)){
                if (isCancelled()) {
                    break;
                }
                Log.d(LOG_TAG, "Tick Step: " + mStep);
                SystemClock.sleep(Constants.SLEEP_INTERVAL);
                nextStep = generateNextStep(mCurrentPattern);
                if(nextStep == null){
                    isPatternConstant = true;
                }
                else{
                    mStep++;
                    progress[0] = nextStep;
                    progress[1] = mStep;
                    publishProgress(progress);
                }
            }
            return progress;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            mCurrentPattern = (int[][])values[0];
            mGridAdapter.setPattern(mCurrentPattern);
            mGridAdapter.notifyDataSetChanged();
            mTickStep.setText(Integer.toString((int)values[1]));
        }

        @Override
        protected void onPostExecute(Object... values) {
            super.onPostExecute(values);
            if(values[0] != null){
                mCurrentPattern = (int[][])values[0];
            }
            mGridAdapter.setPattern(mCurrentPattern);
            mGridAdapter.notifyDataSetChanged();
            mTickStep.setText(Integer.toString((int)values[1]));
            activateGrid(true);
            switchMenuItem.setVisible(true);
        }
    }
}
