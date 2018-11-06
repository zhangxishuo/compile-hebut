package com.mengyunzhi.entity;

import com.mengyunzhi.enums.Type;

/**
 * 单词实体
 */
public class Word {

    private Type type;                   // 类型

    private Object value;                // 值

    private Location location;           // 位置

    public Word(Location location) {
        this.location = location;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 打印词法分析信息
     */
    public String printInfo() {
        String string;
        if (value == null) {
            string = "( " + type + ", )";
        } else {
            string = "( " + type + ", " + value + " )";
        }
        return string;
    }

    @Override
    public String toString() {
        return "Word{" +
                "type=" + type +
                ", value=" + value +
                ", location=" + location +
                '}';
    }
}
