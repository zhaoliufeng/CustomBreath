package com.ws.mesh.custombreath.ui.presenter;

import android.util.SparseArray;

import com.ws.mesh.custombreath.bean.BreathParams;
import com.ws.mesh.custombreath.bean.RGBColor;
import com.ws.mesh.custombreath.ui.impl.ICustomBreathView;

public class CustomBreathPresenter {

    private ICustomBreathView mICustomBreathView;
    private RGBColor color;
    private int warm;
    private int holdStep;
    private int changeStep;
    private SparseArray<BreathParams> mParamsSparseArray;

    public CustomBreathPresenter(ICustomBreathView customBreathView, SparseArray<BreathParams> paramsSparseArray) {
        mICustomBreathView = customBreathView;
        mParamsSparseArray = paramsSparseArray;
    }

    public void addDot(int index) {
        if (color != null) {
            //添加彩色节点
            BreathParams breathParams = new BreathParams();
            breathParams.index =
                    index == -1 ? mParamsSparseArray.size() : index;
            breathParams.R = color.r;
            breathParams.B = color.b;
            breathParams.G = color.g;
            breathParams.W = 0;
            breathParams.C = 0;
            breathParams.ChangeStep = changeStep;
            breathParams.HoldStep = holdStep;
            mParamsSparseArray.put(breathParams.index, breathParams);
            mICustomBreathView.addDot();
        } else {
            //添加色温节点
            BreathParams breathParams = new BreathParams();
            breathParams.index =
                    index == -1 ? mParamsSparseArray.size() : index;
            breathParams.W = warm;
            breathParams.C = 255 - warm;
            breathParams.ChangeStep = changeStep;
            breathParams.HoldStep = holdStep;
            mParamsSparseArray.put(breathParams.index, breathParams);
            mICustomBreathView.addDot();
        }
    }

    public void setHoldStep(int step) {
        holdStep = (int) ((step / 100f) * 255);
    }

    public void setChangeStep(int step) {
        changeStep = (int) ((step / 100f) * 255);
    }

    public void setWarm(int process) {
        color = null;
        warm = (int) ((process / 100f) * 255);
    }

    public void setColor(int process) {
        warm = 0;
        if (color == null)
            color = new RGBColor();
        if (process <= 20) {
            //ff0
            color.r = 255;
            color.g = (int) (12.75 * process);
            color.b = 0;
        } else if (process <= 40) {
            //0f0
            color.r = (int) (255 - (12.75 * (process - 20)));
            color.g = 255;
            color.b = 0;
        } else if (process <= 60) {
            //0ff
            color.r = 0;
            color.g = 255;
            color.b = (int) (12.75 * (process - 40));
        } else if (process <= 80) {
            //00f
            color.r = 0;
            color.g = (int) (255 - 12.75 * (process - 60));
            color.b = 255;
        } else if (process <= 100) {
            //f0f
            color.r = (int) Math.round(12.75 * (process - 80));
            color.g = 0;
            color.b = 255;
        }
    }
}
