package com.ws.mesh.custombreath.ui.impl;

import android.util.SparseArray;

public interface IMainView {
    //登陆成功
    void onLoginSuccess();

    //断开连接
    void onLoginOut();

    //连接中
    void onLogging();
}
