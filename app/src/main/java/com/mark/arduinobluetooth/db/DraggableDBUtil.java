package com.mark.arduinobluetooth.db;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.mark.arduinobluetooth.APP.mRealm;

/**
 * @ClassName: DBUtil
 * @Description: DBUtil java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/3/31 5:17 PM
 * @Version: 1.0
 */
public class DraggableDBUtil {

    public static void save(DraggableInfoBean newMovie) {
        mRealm.executeTransactionAsync(realm -> {
            DraggableInfoBean user = realm.createObject(DraggableInfoBean.class);
            user.setType(newMovie.getType());
            user.setText(newMovie.getText());
            user.setCenterY(newMovie.getCenterY());
            user.setCenterX(newMovie.getCenterX());
            user.setId(newMovie.getId());
            user.setPic(newMovie.getPic());
            user.setLeft(newMovie.getLeft());
            user.setRight(newMovie.getRight());
            user.setTop(newMovie.getTop());
            user.setBottom(newMovie.getBottom());

        }, () -> {
            Log.d("------Realm-----", "Success");
            //成功回调
        }, error -> {
            //失败回调
            Log.d("------Realm-----", "Error");
        });
    }


    public static List<DraggableInfoBean> getValueBean() {
        RealmResults<DraggableInfoBean> user3 = mRealm.where(DraggableInfoBean.class).findAll();
        if (user3 == null || user3.size() == 0) {
            List<DraggableInfoBean> user = new ArrayList();
            return user;
        } else {
            return user3;
        }
    }


    public static void DelValue() {
        final RealmResults<DraggableInfoBean> students3 = mRealm.where(DraggableInfoBean.class).findAll();
        mRealm.executeTransaction(realm -> students3.deleteAllFromRealm());
    }


}
