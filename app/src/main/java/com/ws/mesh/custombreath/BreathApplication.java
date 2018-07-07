package com.ws.mesh.custombreath;

import com.telink.TelinkApplication;
import com.we_smart.sqldao.DBHelper;
import com.ws.mesh.custombreath.constant.AppConstant;
import com.ws.mesh.custombreath.db.DBOpenHelper;
import com.ws.mesh.custombreath.service.TelinkLightService;

public class BreathApplication extends TelinkApplication {

    private static final String TAG = "BreathApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.getInstance().initDBHelper(new DBOpenHelper(this, AppConstant.SQL_NAME));
    }

    @Override
    public void doInit() {
        super.doInit();
        startLightService(TelinkLightService.class);
    }

    //获取到app对象
    private static BreathApplication mApplication;

    public static BreathApplication getInstance() {
        return mApplication;
    }
}
