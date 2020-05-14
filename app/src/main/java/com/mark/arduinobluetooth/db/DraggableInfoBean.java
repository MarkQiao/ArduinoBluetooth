package com.mark.arduinobluetooth.db;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * @Description:
 * @Author: weilu
 * @Time: 2018/2/24 0024 10:57.
 */
@RealmClass
public class DraggableInfoBean extends RealmObject {

    private int type;
    private String text;
    private int pic;
    private int idS;
    private int centerX;
    private int centerY;

    private int left;
    private int right;
    private int top;
    private int bottom;



    public int getType() {
        return type;
    }

    public int getIdS() {
        return idS;
    }

    public void setIdS(int idS) {
        this.idS = idS;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getId() {
        return idS;
    }

    public void setId(int id) {
        this.idS = id;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }
}
