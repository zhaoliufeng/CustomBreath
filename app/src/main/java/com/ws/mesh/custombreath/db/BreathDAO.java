package com.ws.mesh.custombreath.db;

import android.util.SparseArray;

import com.we_smart.sqldao.BaseDAO;
import com.ws.mesh.custombreath.bean.CustomBreath;

import java.util.List;

public class BreathDAO extends BaseDAO<CustomBreath> {

    private BreathDAO() {
        super(CustomBreath.class);
    }

    private static BreathDAO mBreathDAO;

    public static BreathDAO getBreathDaoInstance() {
        if (mBreathDAO == null) {
            synchronized (BreathDAO.class) {
                if (mBreathDAO == null) {
                    mBreathDAO = new BreathDAO();
                }
            }
        }
        return mBreathDAO;
    }

    public boolean insertBreath(CustomBreath breath) {
        return insert(breath);
    }

    public boolean deleteBreath(CustomBreath breath) {
        return delete(breath, String.valueOf(breath.index));
    }

    public SparseArray<CustomBreath> queryBreath() {
        List<CustomBreath> breaths = query(null);
        SparseArray<CustomBreath> breathSparseArray = new SparseArray<>();
        for (CustomBreath breath : breaths) {
            breathSparseArray.put(breath.index, breath);
        }
        return breathSparseArray;
    }

    public boolean updateBreath(CustomBreath breath) {
        return update(breath, "index");
    }
}
