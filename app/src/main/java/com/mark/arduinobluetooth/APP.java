package com.mark.arduinobluetooth;

import android.app.Application;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 *
 * @explain: 方法名
 * @Description: java类作用描述
 *
 */
public class APP extends Application {

    public static BluetoothSocket bluetoothSocket;
    public static Context context;
    public static Realm mRealm;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        Realm.init(context);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("arduino.realm")
                .schemaVersion(3)
                //.migration(new CustomMigration())//升级数据库
                .deleteRealmIfMigrationNeeded()
                .build();

        mRealm = Realm.getInstance(configuration);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
