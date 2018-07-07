package com.ws.mesh.custombreath.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ws.mesh.custombreath.BreathApplication;

/**
 * Created by we_smart on 2017/11/20.
 */

public class SPUtils {

    private static SharedPreferences mSharePreferences;

    private static final String MESH = "mesh";

    //初始化
    public static void init(Context context) {
        mSharePreferences = context.getSharedPreferences
                (MESH, Context.MODE_PRIVATE);
    }

    //存string类型的值
    public static void saveString(String key, String values) {
        checkNull();
        SharedPreferences.Editor editor = mSharePreferences.edit();
        editor.putString(key, values);
        editor.apply();
    }

    //存boolean类型的值
    public static void saveBoolean(String key, boolean values) {
        checkNull();
        SharedPreferences.Editor editor = mSharePreferences.edit();
        editor.putBoolean(key, values);
        editor.apply();
    }

    //存int类型的值
    public static void saveInteger(String key, int values) {
        checkNull();
        SharedPreferences.Editor editor = mSharePreferences.edit();
        editor.putInt(key, values);
        editor.apply();
    }

    private static void checkNull() {
        if (mSharePreferences == null) init(BreathApplication.getInstance());
    }

    public static String getString(String key) {
        checkNull();
        return mSharePreferences.getString(key, "");
    }

    public static boolean getBoolean(String key, boolean defValue) {
        checkNull();
        return mSharePreferences.getBoolean(key, defValue);
    }

    public static int getInteger(String key, int defValue) {
        checkNull();
        return mSharePreferences.getInt(key, defValue);
    }

    public static void setLatelyMesh(String meshName) {
        saveString(MESH, meshName);
    }

    public static String getLatelyMesh() {
        return getString(MESH);
    }
}
