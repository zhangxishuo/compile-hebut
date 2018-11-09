package com.mengyunzhi.entity;

import com.mengyunzhi.enums.SymbolType;

/**
 * 符号标识实体
 */
public class Symbol {

    private SymbolType symbolType;           // 类型

    private char value;                      // 值

    private Location location;               // 位置信息

    public Symbol() {
    }

    public SymbolType getSymbolType() {
        return symbolType;
    }

    public void setSymbolType(SymbolType symbolType) {
        this.symbolType = symbolType;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
