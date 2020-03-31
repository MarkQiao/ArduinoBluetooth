package com.mark.arduinobluetooth.db;

import android.content.Context;
import android.widget.Toast;

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
public class DBUtil {
    public static void save(final Context mContext, final String str) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ValueBean user = realm.createObject(ValueBean.class);
                user.setType(1);
                user.setValue(str);
                //user.setAge(33);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                //成功回调
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //失败回调
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static ValueBean getValueBean() {
        RealmResults<ValueBean> user3 = mRealm.where(ValueBean.class).equalTo("type", 1).findAll();
        return user3.get(0);
    }
}
