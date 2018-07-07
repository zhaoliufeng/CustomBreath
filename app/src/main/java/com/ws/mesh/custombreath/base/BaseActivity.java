package com.ws.mesh.custombreath.base;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ws.mesh.custombreath.BreathApplication;
import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.constant.AppLifeStatusConstant;
import com.ws.mesh.custombreath.constant.IntentConstant;
import com.ws.mesh.custombreath.utils.CoreData;
import com.ws.mesh.custombreath.utils.StatusBarUpper;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {

    protected abstract int getLayoutId();

    protected abstract int initData();
    /**
     * 由上层界面传递过来的pageId
     */
    protected int mPageId;

    protected BaseFragment mCurrFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (CoreData.core().getCurrAppStatus() == AppLifeStatusConstant.KILL_PROGRESS){
            //初始化状态
            Intent intent = new Intent(getApplicationContext(), LauncherActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        ButterKnife.bind(this);
        initData();
        CoreData.addActivity(this, getClass().getSimpleName());
    }

    public void pushActivity(Class<? extends BaseActivity> activityClass){
        startActivity(new Intent(this, activityClass));
    }

    public void pushActivity(Class<? extends BaseActivity> activityClass, int pageId){
        startActivity(new Intent(this, activityClass)
                .putExtra(IntentConstant.PAGE_TYPE, pageId));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CoreData.removeActivity(getClass().getSimpleName());
    }

    protected void toast(final String context) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BreathApplication.getInstance(), context, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 设置状态栏 高度
     */
    protected void setStatusBar(View statusBar){
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = StatusBarUpper.getStatusBarHeight(this);
        statusBar.setLayoutParams(layoutParams);
    }

    public int getResourcesColor(int colorId){
        return getResources().getColor(colorId);
    }

    /**
     * 渲染fragment
     */
    public void setPage(BaseFragment fragment) {
        mCurrFragment = fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_frame, mCurrFragment);
        fragmentTransaction.commit();
    }

    public int getPageId(){
        mPageId = getIntent().getIntExtra(IntentConstant.PAGE_TYPE, -1);
        return mPageId;
    }
}
