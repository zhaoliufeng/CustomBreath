package com.ws.mesh.custombreath.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.base.BaseActivity;
import com.ws.mesh.custombreath.bean.CustomBreath;
import com.ws.mesh.custombreath.db.BreathDAO;
import com.ws.mesh.custombreath.ui.adapter.CustomBreathAdapter;
import com.ws.mesh.custombreath.ui.impl.IMainView;
import com.ws.mesh.custombreath.ui.presenter.MainPresenter;
import com.ws.mesh.custombreath.utils.BreathFactory;
import com.ws.mesh.custombreath.utils.CoreData;
import com.ws.mesh.custombreath.utils.SendMsg;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

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
                CustomBreath customBreath = CoreData.customBreathSparseArray.get(position);
                BreathFactory.create(position + 1,customBreath.isCysle, customBreath.mParamsSparseArray);
            }

            @Override
            public void OnCycle(int position, boolean isCycle) {
                CustomBreath customBreath = CoreData.customBreathSparseArray.valueAt(position);
                customBreath.isCysle = isCycle;
                BreathDAO.getBreathDaoInstance().updateBreath(customBreath);
            }
        });
    }

    private boolean isOpen = true;
    @OnClick(R.id.tv_title)
    public void switchDevice(){
        isOpen = !isOpen;
        SendMsg.switchDevice(0xffff, isOpen);
    }

    @OnLongClick(R.id.tv_title)
    public boolean loadBreath(){
        //加载呼吸
        byte[] params = new byte[]{ 0x0a, (byte) (0x0a + 1) };
        SendMsg.sendBreath(0xFFFF, (byte) 0xE2, params);
        return true;
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
