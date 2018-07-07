package com.ws.mesh.custombreath.bean;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.we_smart.sqldao.Annotation.DBFiled;

import java.lang.reflect.Field;

public class CustomBreath {
    @DBFiled(isPrimary = true)
    public int index;
    @DBFiled
    public String name;
    @DBFiled
    public boolean isCysle;
    @DBFiled
    public String breathParams;

    public SparseArray<BreathParams> mParamsSparseArray;

    public static String paramsToString(SparseArray<BreathParams> paramsSparseArray) {
        if (paramsSparseArray == null || paramsSparseArray.size() == 0) return "";
        JSONArray jsonArray = new JSONArray();
        try {
        for (int x = 0; x < paramsSparseArray.size(); x++) {
            JSONObject jsonObject = new JSONObject();
            BreathParams params = paramsSparseArray.valueAt(x);
            Field fields[] = BreathParams.class.getFields();
            for (Field field : fields){
                jsonObject.put(field.getName(), field.get(params));
            }
            jsonArray.add(jsonObject);
        }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return jsonArray.toJSONString();
    }

    @SuppressLint("UseSparseArrays")
    public static SparseArray<BreathParams> stringToBreathParams(String BreathParamsText) {
        SparseArray<BreathParams> mDevIdSparseArray = new SparseArray<>();
        if (TextUtils.isEmpty(BreathParamsText)) return mDevIdSparseArray;
        JSONArray jsonArray = JSON.parseArray(BreathParamsText);
        try {
        for (int x = 0; x < jsonArray.size(); x++) {
            JSONObject jsonObject = jsonArray.getJSONObject(x);
            Field fields[] = BreathParams.class.getFields();
            BreathParams breathParams = new BreathParams();
            for (Field field : fields){
                field.set(breathParams, jsonObject.get(field.getName()));
            }
            mDevIdSparseArray.append(breathParams.index, breathParams);
        }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return mDevIdSparseArray;
    }
}
