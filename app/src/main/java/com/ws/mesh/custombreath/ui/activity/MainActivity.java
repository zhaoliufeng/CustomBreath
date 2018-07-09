package com.ws.mesh.custombreath.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.base.BaseActivity;
import com.ws.mesh.custombreath.bean.CustomBreath;
import com.ws.mesh.custombreath.db.BreathDAO;
import com.ws.mesh.custombreath.ui.adapter.CustomBreathAdapter;
import com.ws.mesh.custombreath.ui.impl.IMainView;
import com.ws.mesh.custombreath.ui.presenter.MainPresenter;
import com.ws.mesh.custombreath.utils.CoreData;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainView{

    @BindView(R.id.rl_breath)
    RecyclerView mRlBreath;

    private MainPresenter mPresenter;
    private CustomBreathAdapter customBreathAdapter;
    private SparseArray<CustomBreath> customBreathSparseArray;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mPresenter = new MainPresenter(this, this);

        customBreathSparseArray = CoreData.customBreathSparseArray;
        customBreathAdapter = new CustomBreathAdapter(customBreathSparseArray, this);
        mRlBreath.setAdapter(customBreathAdapter);
        mRlBreath.setLayoutManager(new LinearLayoutManager(this));

        customBreathAdapter.setOnItemSelectListener(new CustomBreathAdapter.OnItemSelectListener() {
            @Override
            public void OnItemSelect(int position) {
                pushActivity(CustomBreathActivity.class, position);
            }

            @Override
            public void OnExecute(int position) {

            }

            @Override
            public void OnCycle(int position, boolean isCycle) {
                CustomBreath customBreath = CoreData.customBreathSparseArray.valueAt(position);
                customBreath.isCysle = isCycle;
                BreathDAO.getBreathDaoInstance().updateBreath(customBreath);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.checkBle();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        customBreathAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoginSuccess() {
        toast("连接成功");
    }

    @Override
    public void onLoginOut() {
        toast("断开连接");
    }

    @Override
    public void onLogging() {
        toast("连接中");
    }
}
