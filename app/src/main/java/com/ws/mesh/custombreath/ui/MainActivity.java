package com.ws.mesh.custombreath.ui;

import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.base.BaseActivity;
import com.ws.mesh.custombreath.ui.impl.IMainView;
import com.ws.mesh.custombreath.ui.presenter.MainPresenter;

public class MainActivity extends BaseActivity implements IMainView{

    private MainPresenter mPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int initData() {
        mPresenter = new MainPresenter(this, this);
        return 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.checkBle();
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
