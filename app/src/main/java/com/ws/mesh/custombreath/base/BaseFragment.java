package com.ws.mesh.custombreath.base;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ws.mesh.custombreath.BreathApplication;
import com.ws.mesh.custombreath.constant.IntentConstant;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * fragment 基类
 */

public abstract class BaseFragment extends Fragment {

    /**
     * 获取当前绑定的视图id
     **/
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     **/
    protected abstract void initData();

    /**
     * 添加视图监听
     */
    protected abstract void setListener();

    //保存 添加房间 场景情况需要保存信息
    public void onSave() {
    }

    //编辑 房间 设备管理有遍历界面
    public void onEdit() {
    }

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        setListener();
        return view;
    }

    public void pushActivity(Class<? extends BaseActivity> clazz, int pageId) {
        startActivity(new Intent(getActivity(), clazz)
                .putExtra(IntentConstant.PAGE_TYPE, pageId));
    }

    public void toast(int strId) {
        toast(getString(strId));
    }

    public void toast(final String str) {
        if (isAdded()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BreathApplication.getInstance(), str, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
