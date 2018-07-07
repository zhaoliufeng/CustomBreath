package com.ws.mesh.custombreath.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.bean.CustomBreath;

public class CustomBreathAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private SparseArray<CustomBreath> mDatas;
    public CustomBreathAdapter(SparseArray<CustomBreath> data, Context context) {
        mContext = context;
        mDatas = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new BreathViewHolder(LayoutInflater.
                from(parent.getContext()).inflate(R.layout.view_custon_breath_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class BreathViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout mRlAddNewDot;
        TextView mBreathName;
        TextView mIsRecy;
        TextView mDotNum;

        public BreathViewHolder(View itemView) {
            super(itemView);

        }
    }
}
