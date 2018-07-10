package com.ws.mesh.custombreath.ui.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ws.mesh.custombreath.R;
import com.ws.mesh.custombreath.base.BaseActivity;
import com.ws.mesh.custombreath.bean.BreathParams;
import com.ws.mesh.custombreath.bean.CustomBreath;
import com.ws.mesh.custombreath.constant.IntentConstant;
import com.ws.mesh.custombreath.db.BreathDAO;
import com.ws.mesh.custombreath.ui.adapter.CustomDotsAdapter;
import com.ws.mesh.custombreath.ui.control.CustomSeekBar;
import com.ws.mesh.custombreath.ui.control.OnSeekBarDragListener;
import com.ws.mesh.custombreath.ui.impl.ICustomBreathView;
import com.ws.mesh.custombreath.ui.presenter.CustomBreathPresenter;
import com.ws.mesh.custombreath.utils.CoreData;

import java.time.Instant;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomBreathActivity extends BaseActivity implements ICustomBreathView {

    @BindView(R.id.rl_dots)
    RecyclerView mRlDots;

    int[] mColoursColors = new int[]{
            0xFFFF0000, 0xFFFF7F00, 0xFFFFFF00, 0xFF7FFF00,
            0xFF00FF00, 0xFF00FF7F, 0xFF00FFFF, 0xFF007FFF,
            0xFF0000FF, 0xFF7F00FF, 0xFFFF00FF};
    int[] mWarmColors = new int[]{
            0xFFFFFFFF, 0xFFFEB800
    };

    private CustomBreathPresenter mPresenter;
    private CustomBreath mCustomBreath;
    private SparseArray<BreathParams> mParamsSparseArray;
    private CustomDotsAdapter customDotsAdapter;
    private int breathId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_breath;
    }

    @Override
    protected void initData() {
        breathId = getIntent().getIntExtra(IntentConstant.NEED_ID, 0);
        mCustomBreath = CoreData.customBreathSparseArray.get(breathId);
        mParamsSparseArray = mCustomBreath.mParamsSparseArray;
        mPresenter = new CustomBreathPresenter(this, mParamsSparseArray);
        customDotsAdapter = new CustomDotsAdapter(mParamsSparseArray, this);
        mRlDots.setAdapter(customDotsAdapter);
        mRlDots.setLayoutManager(new LinearLayoutManager(this));

        customDotsAdapter.setOnItemSelectListener(new CustomDotsAdapter.OnItemSelectListener() {
            @Override
            public void onEdit(int position) {
                popDotDialog(position);
            }

            @Override
            public void onDel(int position) {
                SparseArray<BreathParams> sparseArray = mParamsSparseArray.clone();
                sparseArray.remove(position);
                mParamsSparseArray.clear();
                for (int i = 0; i < sparseArray.size(); i++){
                    sparseArray.valueAt(i).index = i;
                    mParamsSparseArray.append(i, sparseArray.valueAt(i));
                }
                customDotsAdapter.notifyDataSetChanged();
                BreathDAO.getBreathDaoInstance().updateBreath(mCustomBreath);
            }
        });
    }

    @OnClick(R.id.iv_menu_right)
    public void onAddDot() {
        popDotDialog(-1);
    }

    @OnClick(R.id.img_back)
    public void onBack(){
        finish();
    }

    private void popDotDialog(final int position) {
        final AlertDialog mAlertDialog = new AlertDialog.Builder(this, R.style.CustomDialog).create();
        mAlertDialog.show();
        Window window = mAlertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_edit_breath_dot);
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            final CustomSeekBar csbColor = window.findViewById(R.id.csb_color);
            final CustomSeekBar csbWarm = window.findViewById(R.id.csb_warm);
            CustomSeekBar csbHoldStep = window.findViewById(R.id.csb_hold_step);
            CustomSeekBar csbChangeStep = window.findViewById(R.id.csb_change_step);
            Button btnCancel = window.findViewById(R.id.btn_cancel);
            Button btnConfirm = window.findViewById(R.id.btn_confirm);
            final TextView tvChangeStep = window.findViewById(R.id.tv_change_step);
            final TextView tvHoldStep = window.findViewById(R.id.tv_hold_step);

            csbColor.setProcessLinearBgShader(mColoursColors);
            csbWarm.setProcessLinearBgShader(mWarmColors);

            csbColor.setOnSeekBarDragListener(new OnSeekBarDragListener() {
                @Override
                public void startDrag() {
                    csbWarm.setProcessWithAnimation(0, false);
                }

                @Override
                public void dragging(int process) {
                    mPresenter.setColor(process);
                }

                @Override
                public void stopDragging(int endProcess) {

                }
            });

            csbWarm.setOnSeekBarDragListener(new OnSeekBarDragListener() {
                @Override
                public void startDrag() {
                    csbColor.setProcessWithAnimation(0, false);
                }

                @Override
                public void dragging(int process) {
                    mPresenter.setWarm(process);
                }

                @Override
                public void stopDragging(int endProcess) {

                }
            });


            csbHoldStep.setOnSeekBarDragListener(new OnSeekBarDragListener() {
                @Override
                public void startDrag() {

                }

                @Override
                public void dragging(int process) {
                    mPresenter.setHoldStep(process);
                    tvHoldStep.setText((int) ((process/100f) * 255) + "ms");
                }

                @Override
                public void stopDragging(int endProcess) {

                }
            });

            csbChangeStep.setOnSeekBarDragListener(new OnSeekBarDragListener() {
                @Override
                public void startDrag() {

                }

                @Override
                public void dragging(int process) {
                    mPresenter.setChangeStep(process);
                    tvChangeStep.setText((int) ((process/100f) * 255) + "ms");
                }

                @Override
                public void stopDragging(int endProcess) {

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.addDot(position);
                    mAlertDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void addDot() {
        BreathDAO.getBreathDaoInstance().updateBreath(mCustomBreath);
        customDotsAdapter.notifyDataSetChanged();
    }
}
