package com.epresidential.quandoodstest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.epresidential.quandoodstest.R;

import java.util.List;

/**
 * Created by daniele on 11/07/16.
 */
public class GridItemAdapter extends BaseAdapter {

    private static final String LOG_TAG = GridItemAdapter.class.getSimpleName();

    private Context mContext;
    private int mGridSize;

    private int[][] mCurrentGridState;
    private LayoutInflater mLayoutInflater;


    public GridItemAdapter(Context context, GridView gridField, int[][] initialPattern) {
        mContext = context;
        mGridSize = mContext.getResources().getInteger(R.integer.grid_size);
        mCurrentGridState = initialPattern;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return getRowsNumber() * getColumnsNumber();
    }

    @Override
    public Object getItem(int i) {
        int column = i%mGridSize;
        int row = i/mGridSize;
        return mCurrentGridState[row][column];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int column = position%mGridSize;
        int row = position/mGridSize;

        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.grid_item_layout, parent, false);
            holder = new ViewHolder();
            holder.frame = (FrameLayout) convertView.findViewById(R.id.grid_frame);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        int item = mCurrentGridState[row][column];
        //int number = item.getId();

        if(item == 1) {
            if(android.os.Build.VERSION.SDK_INT > 23) {
                holder.frame.setBackgroundColor(mContext.getColor(R.color.black));
            }
            else {
                holder.frame.setBackgroundResource(R.color.black);
            }
        }
        else{
            if(android.os.Build.VERSION.SDK_INT > 23) {
                holder.frame.setBackgroundColor(mContext.getColor(R.color.white));
            }
            else {
                holder.frame.setBackgroundResource(R.color.white);
            }
        }


        return convertView;
    }

    public int getRowsNumber(){
        return mCurrentGridState[0].length;
    }

    public int getColumnsNumber(){
        return mCurrentGridState.length;
    }

    public void setPattern(int[][] pattern){
        mCurrentGridState = pattern;
    }

    class ViewHolder {
        FrameLayout frame;
    }
}
