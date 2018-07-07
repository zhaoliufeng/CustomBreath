package com.ws.mesh.custombreath;

import android.util.SparseArray;

import com.ws.mesh.custombreath.bean.BreathParams;
import com.ws.mesh.custombreath.bean.CustomBreath;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        List<BreathParams> paramsSparseArray = new ArrayList<>();
//        BreathParams breathParams = new BreathParams();
//        breathParams.index = 1;
//        breathParams.HoldStep = 1;
//        breathParams.ChangeStep = 2;
//        breathParams.R = 255;
//        breathParams.G = 0;
//        breathParams.B = 0;
//        breathParams.W = 0;
//        breathParams.C = 0;
//        paramsSparseArray.add(0, breathParams);
//        System.out.println(CustomBreath.paramsToString(paramsSparseArray));

        String s = "[{\"R\":255,\"B\":0,\"ChangeStep\":2,\"C\":0,\"HoldStep\":1,\"G\":0,\"W\":0,\"index\":1}]";
        SparseArray<BreathParams> sparseArray = CustomBreath.stringToBreathParams(s);
        System.out.println(sparseArray.toString());

    }
}