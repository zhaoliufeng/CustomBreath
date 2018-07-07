package com.ws.mesh.custombreath.ui.presenter;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.ws.mesh.custombreath.BreathApplication;
import com.ws.mesh.custombreath.service.TelinkLightService;
import com.ws.mesh.custombreath.ui.impl.ILauncherView;
import com.ws.mesh.custombreath.utils.SPUtils;

public class LauncherPresenter {
    //视图对象
    private ILauncherView mLauncherView;

    public LauncherPresenter(ILauncherView launcherView) {
        mLauncherView = launcherView;
        if (TelinkLightService.Instance() == null) {
            BreathApplication.getInstance().doInit();
        }
        initData();
    }

    private void initData() {
        //如果没有任何网络就进行相关的操作。
        if (!TextUtils.isEmpty(SPUtils.getLatelyMesh())) {
            mLauncherView.enterMain();
        } else {
            initMeshData();
        }
    }

    //创建mesh网络 fulife
    private void initMeshData() {
        SPUtils.setLatelyMesh("Fulife");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mLauncherView.enterMain();
            }
        }, 1500);
    }
}
