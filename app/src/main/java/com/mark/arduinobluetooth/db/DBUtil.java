package com.mark.arduinobluetooth.db;

import android.util.Log;

import io.realm.RealmResults;

import static com.mark.arduinobluetooth.APP.mRealm;

/**
 * @ClassName: DBUtil
 * @Description: DBUtil java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/3/31 5:17 PM
 * @Version: 1.0
 */
public class DBUtil {


    public static void saveValueBean(ValueBean newMovie) {

        if (getIsValue(newMovie.getType())) {
            updateById(newMovie);
        } else {
            save(newMovie);
        }
    }


    public static void saveValueBean(int type, String value) {
        ValueBean vb = new ValueBean();
        vb.setType(type);
        vb.setValue(value);
        if (getIsValue(type)) {
            Log.d("------Realm-----", "saveValueBean->true");
            updateById(vb);
        } else {
            Log.d("------Realm-----", "saveValueBean->flase");
            save(vb);
        }
    }


    public static void save(ValueBean newMovie) {
        mRealm.executeTransactionAsync(realm -> {
            ValueBean user = realm.createObject(ValueBean.class);
            user.setType(newMovie.getType());
            user.setValue(newMovie.getValue());
        }, () -> {
            Log.d("------Realm-----", "Success");
            //            Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
            //成功回调
        }, error -> {
            //失败回调
            Log.d("------Realm-----", "Error");
            //            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
        });
    }


    public static ValueBean getValueBean(int type) {
        RealmResults<ValueBean> user3 = mRealm.where(ValueBean.class).equalTo("type", type).findAll();

        if (user3 == null || user3.size() == 0) {
            ValueBean vb = new ValueBean();
            vb.setType(type);
            vb.setValue("");
            return vb;
        } else {
            ValueBean user = user3.get(0);
            return user;
        }
    }

    public static boolean getIsValue(int type) {
        RealmResults<ValueBean> user3 = mRealm.where(ValueBean.class).equalTo("type", type).findAll();
        if (user3 == null || user3.size() == 0) {
            Log.d("------Realm-----", "null");
            return false;
        } else {
            Log.d("------Realm-----", "ValueBean.size()-->" + user3.size());
            return true;
        }

    }

    public static void updateById(ValueBean newMovie) {
        ValueBean dog = mRealm.where(ValueBean.class).equalTo("type", newMovie.getType()).findFirst();
        mRealm.beginTransaction();
        dog.setValue(newMovie.getValue());
        mRealm.commitTransaction();

    }
}
