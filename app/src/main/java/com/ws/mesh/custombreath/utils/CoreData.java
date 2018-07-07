package com.ws.mesh.custombreath.utils;

import android.os.Handler;
import android.os.Looper;

import com.ws.mesh.custombreath.base.BaseActivity;
import com.ws.mesh.custombreath.bean.Mesh;
import com.ws.mesh.custombreath.constant.AppLifeStatusConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by we_smart on 2017/11/20.
 */

public class CoreData {

    //单例
    private static CoreData instance = null;

    public static CoreData core() {
        if (instance == null) {
            synchronized (CoreData.class) {
                if (instance == null) {
                    instance = new CoreData();
                }
            }
        }
        return instance;
    }

    //是否是添加设备模式
    public static boolean mAddDeviceMode = false;

    //应用当前状态
    private int mCurrAppStatus = AppLifeStatusConstant.KILL_PROGRESS;

    //获取app状态
    public int getCurrAppStatus() {
        return mCurrAppStatus;
    }

    //设置app状态
    public void setCurrAppStatus(int status) {
        mCurrAppStatus = status;
    }

    /*
    * 当前操作的网络
    * */
    private Mesh mCurrMesh;

    public Mesh getCurrMesh() {
        return mCurrMesh;
    }

    public void setCurrMesh(Mesh mesh) {
        mCurrMesh = mesh;
    }


    /*
    * 全局数据
    * */
    //所有的网络
    public Map<String, Mesh> mMeshMap;

    public void initMeshData() {

    }

    public void switchMesh(Mesh mesh) {
        if (mesh != null) {
            setCurrMesh(mesh);
            initMeshData();
        }
    }

    private boolean mRequestBluetooth;

    public boolean getBLERequestStatus() {
        return mRequestBluetooth;
    }

    public void setBLERequest(boolean requestBluetooth) {
        this.mRequestBluetooth = requestBluetooth;
    }

    /**** Activity 管理 ****/
    public static Map<String, BaseActivity> mMangerActivity = new HashMap<>();

    public static void addActivity(BaseActivity baseActivity, String activityName) {
        mMangerActivity.put(activityName, baseActivity);
    }

    public static void removeActivity(String activityName) {
        if (mMangerActivity.get(activityName) == null)
            return;
        mMangerActivity.remove(activityName);
    }

    public static Handler mHandler = new Handler(Looper.getMainLooper());
}
