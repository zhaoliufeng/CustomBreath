package com.ws.mesh.custombreath.ui;

import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.base.BaseActivity;
import com.ws.mesh.custombreath.ui.impl.ILauncherView;
import com.ws.mesh.custombreath.ui.presenter.LauncherPresenter;

public class LauncherActivity extends BaseActivity implements ILauncherView{

    LauncherPresenter mPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected int initData() {
        mPresenter = new LauncherPresenter(this);
        return 0;
    }

    @Override
    public void enterMain() {
        pushActivity(MainActivity.class);
    }
}
