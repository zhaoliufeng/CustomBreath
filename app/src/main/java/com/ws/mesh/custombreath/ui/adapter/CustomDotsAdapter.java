package com.ws.mesh.custombreath.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.bean.BreathParams;
import com.ws.mesh.custombreath.utils.Utils;

public class CustomDotsAdapter extends RecyclerView.Adapter {

    private SparseArray<BreathParams> mDatas;
    private Context mContext;
    private OnItemSelectListener onItemSelectListener;
    public CustomDotsAdapter(SparseArray<BreathParams> data, Context context) {
        mDatas = data;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.view_breath_dot_item, parent, false);
        return new DotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DotViewHolder viewHolder = (DotViewHolder) holder;
        BreathParams params = mDatas.valueAt(position);
        int color;
        if (!(params.R == 0 && params.G == 0 && params.B == 0)){
            color = Color.rgb(params.R, params.G, params.B);
        }else {
            color = Utils.interpolate(0xEBFFFFFF, 0xB9FEB800, params.W / 255f);
        }
        viewHolder.mColor.setBackgroundColor(color);
        viewHolder.mChangeStep.setText(params.ChangeStep + "ms");
        viewHolder.mHoldStep.setText(params.HoldStep + "ms");

        viewHolder.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectListener != null){
                    onItemSelectListener.onEdit(position);
                }
            }
        });

        viewHolder.mBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectListener != null){
                    onItemSelectListener.onDel(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener){
        this.onItemSelectListener = onItemSelectListener;
    }

    public interface OnItemSelectListener{
        void onEdit(int position);
        void onDel(int position);
    }

    private class DotViewHolder extends RecyclerView.ViewHolder {
        TextView mHoldStep;
        TextView mChangeStep;
        View mColor;
        AppCompatButton mBtnEdit;
        AppCompatButton mBtnDel;

        DotViewHolder(View itemView) {
            super(itemView);
            mHoldStep = itemView.findViewById(R.id.tv_hold_step);
            mChangeStep = itemView.findViewById(R.id.tv_change_step);
            mColor = itemView.findViewById(R.id.view_color);
            mBtnEdit = itemView.findViewById(R.id.btn_edit);
            mBtnDel = itemView.findViewById(R.id.btn_del);
        }
    }
}
