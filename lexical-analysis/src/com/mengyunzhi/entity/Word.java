package com.mengyunzhi.entity;

import com.mengyunzhi.enums.Type;

public class Word {

    private Type type;

    private Object value;

    public Word() {
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

    @Override
    public String toString() {
        String string = null;
        if (value == null) {
            string = "( " + type + ", )";
        } else {
            string = "( " + type + ", " + value + " )";
        }
        return string;
    }
}
