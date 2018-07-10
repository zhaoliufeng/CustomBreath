package com.ws.mesh.custombreath.utils;

import android.util.Log;
import android.util.SparseArray;

import com.ws.mesh.custombreath.bean.BreathParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 封装生成呼吸数据包
 */
public class BreathFactory {


    public static void create(final int breathId, boolean cycle, SparseArray<BreathParams> sparseArray) {
        final List<Byte> list = new ArrayList<>();
        //数据总长
        final int totalLen = 3 + 7 * sparseArray.size() + 1;
        //判断是否循环
        int isCycle = cycle ? 0x80 : 0x00;
        //节点数量
        int dotsNum = sparseArray.size();
        list.add((byte) (0x0a + breathId));
        list.add((byte) isCycle);
        list.add((byte) dotsNum);
        //填装呼吸节点数据
        for (int i = 0; i < sparseArray.size(); i++) {
            BreathParams params = sparseArray.valueAt(i);
            list.add((byte) params.ChangeStep);
            list.add((byte) params.HoldStep);
            list.add((byte) params.R);
            list.add((byte) params.G);
            list.add((byte) params.B);
            list.add((byte) params.W);
            list.add((byte) params.C);
        }
        int sum = 0;
        //计算末尾checkSum值
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        list.add((byte) sum);
        list.add(0, (byte) totalLen);

        TaskPool.DefTaskPool().PushTask(new Runnable() {
            @Override
            public void run() {
                try {
                    byte data[] = new byte[10];
                    //包数量
                    int packageNum = (totalLen + 1) / 9;
                    if ((totalLen + 1) % 9 != 0) {
                        packageNum = packageNum + 1;
                    }

                    for (int j = 0; j < packageNum; j++) {
                        //分配包id
                        data[0] = (byte) (0x10 + j);
                        if (j == packageNum - 1 && (totalLen + 1) % 9 != 0) {
                            //判断是不是最后一帧数组
                            for (int x = 0; x < 10; x++) {
                                data[x] = 0;
                            }
                            //末帧数据高位0至1
                            data[0] = (byte) (0x90 + j);
                            for (int i = 1; i <= (totalLen + 1) % 9; i++) {
                                data[i] = (byte) list.get((i - 1) + j * 9).intValue();
                            }
                            SendMsg.sendCustomBreath(0xFFFF, data);
                            Log.i("SendCustomBreath", "data: " + Arrays.toString(data));
                        } else {
                            //分包数据
                            for (int i = 1; i < 10; i++) {
                                data[i] = (byte) list.get((i - 1) + j * 9).intValue();
                            }
                            SendMsg.sendCustomBreath(0xFFFF, data);
                            Log.i("SendCustomBreath", "data: " + Arrays.toString(data));
                        }
                        Thread.sleep(300);
                    }
                    Thread.sleep(600);
                    //加载呼吸
                    byte[] params = new byte[]{0x0a, (byte) (0x0a + breathId)};
                    SendMsg.sendBreath(0xFFFF, (byte) 0xE2, params);
                    Log.i("SendCustomBreath", "load data: " + Arrays.toString(params));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static SparseArray<BreathParams> breathParamsSparseArray = new SparseArray<>();

    private static void init() {
        BreathParams breathParams = new BreathParams();
        breathParams.index = 0;
        breathParams.ChangeStep = 4;
        breathParams.HoldStep = 3;
        breathParams.R = 0xff;
        breathParams.G = 0;
        breathParams.B = 0;
        breathParams.W = 0;
        breathParams.C = 0;

        BreathParams breathParams1 = new BreathParams();
        breathParams1.index = 1;
        breathParams1.ChangeStep = 4;
        breathParams1.HoldStep = 3;
        breathParams1.R = 0xff;
        breathParams1.G = 0x7d;
        breathParams1.B = 0;
        breathParams1.W = 0;
        breathParams1.C = 0;

        BreathParams breathParams2 = new BreathParams();
        breathParams2.index = 2;
        breathParams2.ChangeStep = 4;
        breathParams2.HoldStep = 3;
        breathParams2.R = 0xff;
        breathParams2.G = 0xff;
        breathParams2.B = 0;
        breathParams2.W = 0;
        breathParams2.C = 0;

        BreathParams breathParams3 = new BreathParams();
        breathParams3.index = 3;
        breathParams3.ChangeStep = 4;
        breathParams3.HoldStep = 3;
        breathParams3.R = 0;
        breathParams3.G = 0xff;
        breathParams3.B = 0;
        breathParams3.W = 0;
        breathParams3.C = 0;

        BreathParams breathParams4 = new BreathParams();
        breathParams4.index = 4;
        breathParams4.ChangeStep = 4;
        breathParams4.HoldStep = 3;
        breathParams4.R = 0;
        breathParams4.G = 0xff;
        breathParams4.B = 0xff;
        breathParams4.W = 0;
        breathParams4.C = 0;

        BreathParams breathParams5 = new BreathParams();
        breathParams5.index = 5;
        breathParams5.ChangeStep = 4;
        breathParams5.HoldStep = 3;
        breathParams5.R = 0;
        breathParams5.G = 0;
        breathParams5.B = 0xff;
        breathParams5.W = 0;
        breathParams5.C = 0;

        BreathParams breathParams6 = new BreathParams();
        breathParams6.index = 6;
        breathParams6.ChangeStep = 4;
        breathParams6.HoldStep = 3;
        breathParams6.R = 0xfe;
        breathParams6.G = 0;
        breathParams6.B = 0xff;
        breathParams6.W = 0;
        breathParams6.C = 0;

        BreathParams breathParams7 = new BreathParams();
        breathParams7.index = 7;
        breathParams7.ChangeStep = 4;
        breathParams7.HoldStep = 3;
        breathParams7.R = 0;
        breathParams7.G = 0;
        breathParams7.B = 0;
        breathParams7.W = 0xff;
        breathParams7.C = 0;

        BreathParams breathParams8 = new BreathParams();
        breathParams8.index = 8;
        breathParams8.ChangeStep = 4;
        breathParams8.HoldStep = 3;
        breathParams8.R = 0;
        breathParams8.G = 0;
        breathParams8.B = 0;
        breathParams8.W = 0x7f;
        breathParams8.C = 0x7f;

        BreathParams breathParams9 = new BreathParams();
        breathParams9.index = 9;
        breathParams9.ChangeStep = 4;
        breathParams9.HoldStep = 3;
        breathParams9.R = 0;
        breathParams9.G = 0;
        breathParams9.B = 0;
        breathParams9.W = 0;
        breathParams9.C = 0xff;

        breathParamsSparseArray.append(breathParams.index, breathParams);
        breathParamsSparseArray.append(breathParams1.index, breathParams1);
        breathParamsSparseArray.append(breathParams2.index, breathParams2);
        breathParamsSparseArray.append(breathParams3.index, breathParams3);
        breathParamsSparseArray.append(breathParams4.index, breathParams4);
        breathParamsSparseArray.append(breathParams5.index, breathParams5);
        breathParamsSparseArray.append(breathParams6.index, breathParams6);
        breathParamsSparseArray.append(breathParams7.index, breathParams7);
        breathParamsSparseArray.append(breathParams8.index, breathParams8);
        breathParamsSparseArray.append(breathParams9.index, breathParams9);
    }
}
