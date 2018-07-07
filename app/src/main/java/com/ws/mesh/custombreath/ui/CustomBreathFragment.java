package com.ws.mesh.custombreath.ui;

import android.support.v7.widget.RecyclerView;

import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.base.BaseFragment;

import butterknife.BindView;

public class CustomBreathFragment extends BaseFragment {

    @BindView(R.id.lv_breath)
    RecyclerView mRlBreath;

    private CustomBreathAdapter customBreathAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_custom_breath;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
