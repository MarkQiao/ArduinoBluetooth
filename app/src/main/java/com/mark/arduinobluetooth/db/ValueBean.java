package com.mark.arduinobluetooth.db;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * @ClassName: ValueBean
 * @Description: ValueBean java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/3/31 5:15 PM
 * @Version: 1.0
 */
@RealmClass
public class ValueBean extends RealmObject {

    private int type;
    private String value;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
