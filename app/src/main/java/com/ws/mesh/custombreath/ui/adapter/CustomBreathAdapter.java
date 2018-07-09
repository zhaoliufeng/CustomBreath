package com.ws.mesh.custombreath.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.bean.CustomBreath;

public class CustomBreathAdapter extends RecyclerView.Adapter {

    private SparseArray<CustomBreath> mDatas;
    private Context mContext;

    public CustomBreathAdapter(SparseArray<CustomBreath> data, Context context) {
        mDatas = data;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.view_breath_item, parent, false);
        return new BreathViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BreathViewHolder viewHolder = (BreathViewHolder) holder;
        CustomBreath customBreath = mDatas.valueAt(position);
        viewHolder.mBreathName.setText(customBreath.name);
        viewHolder.mCbIsCycle.setChecked(customBreath.isCysle);
        viewHolder.mDotNum.setText(customBreath.mParamsSparseArray.size() + "");
        viewHolder.mRlFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectListener != null){
                    onItemSelectListener.OnItemSelect(position);
                }
            }
        });
        viewHolder.mBreathName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectListener != null){
                    onItemSelectListener.OnExecute(position);
                }
            }
        });
        viewHolder.mCbIsCycle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onItemSelectListener != null){
                    onItemSelectListener.OnCycle(position, isChecked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class BreathViewHolder extends RecyclerView.ViewHolder {
        TextView mBreathName;
        TextView mDotNum;
        RelativeLayout mRlFrame;
        AppCompatCheckBox mCbIsCycle;
        AppCompatButton mBtnExecute;

        BreathViewHolder(View itemView) {
            super(itemView);
            mBreathName = itemView.findViewById(R.id.tv_breath_name);
            mDotNum = itemView.findViewById(R.id.tv_dot_num);
            mRlFrame = itemView.findViewById(R.id.rl_frame);
            mCbIsCycle = itemView.findViewById(R.id.cb_is_cycle);
            mBtnExecute = itemView.findViewById(R.id.btn_execute);
        }
    }

    private OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener){
        this.onItemSelectListener = onItemSelectListener;
    }
    public interface OnItemSelectListener{
        void OnItemSelect(int position);
        void OnExecute(int position);
        void OnCycle(int position, boolean isCycle);
    }
}
