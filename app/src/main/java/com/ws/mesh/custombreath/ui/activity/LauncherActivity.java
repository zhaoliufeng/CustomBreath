package com.ws.mesh.custombreath.ui.activity;

import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.base.BaseActivity;
import com.ws.mesh.custombreath.constant.AppLifeStatusConstant;
import com.ws.mesh.custombreath.db.BreathDAO;
import com.ws.mesh.custombreath.ui.impl.ILauncherView;
import com.ws.mesh.custombreath.ui.presenter.LauncherPresenter;
import com.ws.mesh.custombreath.utils.CoreData;

public class LauncherActivity extends BaseActivity implements ILauncherView{

    LauncherPresenter mPresenter;
    @Override
    protected int getLayoutId() {
        CoreData.core().setCurrAppStatus(AppLifeStatusConstant.NORMAL_START);
        return R.layout.activity_launcher;
    }

    @Override
    protected void initData() {
        mPresenter = new LauncherPresenter(this);
        CoreData.customBreathSparseArray = BreathDAO.getBreathDaoInstance().queryBreath();
    }

    @Override
    public void enterMain() {
        pushActivity(MainActivity.class);
        finish();
    }
}
