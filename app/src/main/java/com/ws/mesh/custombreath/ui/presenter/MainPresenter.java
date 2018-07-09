package com.ws.mesh.custombreath.ui.presenter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.SparseArray;

import com.telink.bluetooth.LeBluetooth;
import com.telink.bluetooth.event.DeviceEvent;
import com.telink.bluetooth.event.LeScanEvent;
import com.telink.bluetooth.event.MeshEvent;
import com.telink.bluetooth.event.NotificationEvent;
import com.telink.bluetooth.event.ServiceEvent;
import com.telink.bluetooth.light.DeviceInfo;
import com.telink.bluetooth.light.LeAutoConnectParameters;
import com.telink.bluetooth.light.LeRefreshNotifyParameters;
import com.telink.bluetooth.light.LightAdapter;
import com.telink.bluetooth.light.Parameters;
import com.telink.util.Event;
import com.telink.util.EventListener;
import com.ws.mesh.custombreath.BreathApplication;
import com.ws.mesh.custombreath.base.BaseActivity;
import com.ws.mesh.custombreath.bean.CustomBreath;
import com.ws.mesh.custombreath.constant.AppConstant;
import com.ws.mesh.custombreath.db.BreathDAO;
import com.ws.mesh.custombreath.service.TelinkLightService;
import com.ws.mesh.custombreath.ui.impl.IMainView;
import com.ws.mesh.custombreath.utils.CoreData;
import com.ws.mesh.custombreath.utils.TaskPool;

import static com.telink.bluetooth.light.LightAdapter.MODE_AUTO_CONNECT_MESH;

public class MainPresenter implements EventListener<String> {

    private IMainView mIMainView;

    private Activity mActivity;

    public MainPresenter(IMainView mIMainView, BaseActivity activity) {
        this.mIMainView = mIMainView;
        this.mActivity = activity;
        addListener();
        registerReceiver();
    }

    //监听广播
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY - 1);
        BreathApplication.getInstance().registerReceiver(mReceiver, filter);
    }

    //请求蓝牙开启
    public void checkBle() {
        if (!CoreData.core().getBLERequestStatus()) {
            BluetoothAdapter mBluetoothAdapter =
                    LeBluetooth.getInstance().getAdapter(mActivity);
            if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mActivity.startActivityForResult(enableBtIntent, 1);
                CoreData.core().setBLERequest(true);
            }
        }
        TaskPool.DefRandTaskPool().PushTask(new Runnable() {
            @Override
            public void run() {
                autoConnect();
            }
        }, 2000);
    }

    //广播监听
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (state) {
                    case BluetoothAdapter.STATE_ON:
                        autoConnect();
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        mIMainView.onLoginOut();
                        break;
                }
            }
        }
    };

    private void autoConnect() {
        if (!TelinkLightService.Instance().isLogin()) {
            onMeshOffline();
        }

        if (TelinkLightService.Instance() != null) {
            if (TelinkLightService.Instance().getMode() != MODE_AUTO_CONNECT_MESH) {
                //自动重连参数
                LeAutoConnectParameters connectParams = Parameters.createAutoConnectParameters();
                connectParams.setMeshName(AppConstant.MESH_DEFAULT_NAME);
                connectParams.setPassword(AppConstant.MESH_DEFAULT_PASSWORD);
                connectParams.setTimeoutSeconds(15);
                connectParams.autoEnableNotification(true);
                TelinkLightService.Instance().autoConnect(connectParams);
            }
            ReFreshNotify();
        }
    }

    private void ReFreshNotify() {
        //刷新Notify参数
        LeRefreshNotifyParameters refreshNotifyParams = Parameters.createRefreshNotifyParameters();
        refreshNotifyParams.setRefreshRepeatCount(2);
        refreshNotifyParams.setRefreshInterval(2000);
        //开启自动刷新Notify
        TelinkLightService.Instance().autoRefreshNotify(refreshNotifyParams);
    }

    private void addListener() {
        BreathApplication.getInstance().addEventListener(NotificationEvent.GET_DEVICE_TYPE, this);
        BreathApplication.getInstance().addEventListener(LeScanEvent.LE_SCAN, this);
        BreathApplication.getInstance().addEventListener(DeviceEvent.STATUS_CHANGED, this);
        BreathApplication.getInstance().addEventListener(NotificationEvent.ONLINE_STATUS, this);
        BreathApplication.getInstance().addEventListener(ServiceEvent.SERVICE_CONNECTED, this);
        BreathApplication.getInstance().addEventListener(MeshEvent.OFFLINE, this);
        BreathApplication.getInstance().addEventListener(MeshEvent.ERROR, this);
        BreathApplication.getInstance().addEventListener(LeScanEvent.LE_SCAN_TIMEOUT, this);
    }

    @Override
    public void performed(Event<String> event) {
        switch (event.getType()) {
            case NotificationEvent.ONLINE_STATUS:
                onOnlineStatusNotify((NotificationEvent) event);
                break;
            case DeviceEvent.STATUS_CHANGED:
                onDeviceStatusChanged((DeviceEvent) event);
                break;
            case MeshEvent.OFFLINE:
                onMeshOffline();
                break;
            case MeshEvent.ERROR:
                onMeshError((MeshEvent) event);
                break;
            case ServiceEvent.SERVICE_CONNECTED:
                onServiceConnected((ServiceEvent) event);
                break;
            case ServiceEvent.SERVICE_DISCONNECTED:
                onServiceDisconnected((ServiceEvent) event);
            case NotificationEvent.GET_DEVICE_TYPE:
                break;
            default:
                break;
        }
    }

    /**
     * 设备的状态变化
     */
    private void onDeviceStatusChanged(DeviceEvent event) { DeviceInfo deviceInfo = event.getArgs();
        switch (deviceInfo.status) {
            case LightAdapter.STATUS_LOGIN:
                mIMainView.onLoginSuccess();
                break;
            case LightAdapter.STATUS_CONNECTING:
                mIMainView.onLogging();
                break;
            case LightAdapter.STATUS_LOGOUT:
                mIMainView.onLoginOut();
                break;
            default:
                break;
        }
    }

    /**
     * 蓝牙状态数据上报
     */
    @SuppressWarnings("unchecked")
    private synchronized void onOnlineStatusNotify(NotificationEvent event) {

    }

    /**
     * 蓝牙连接服务连接
     */
    private void onServiceConnected(ServiceEvent event) {
        this.autoConnect();
    }

    /**
     * 蓝牙连接服务断开
     */
    private void onServiceDisconnected(ServiceEvent event) {

    }

    private void onMeshError(MeshEvent event) {

    }

    /**
     * Mesh网络离线
     */
    private void onMeshOffline() {
        mIMainView.onLoginOut();
    }
}
