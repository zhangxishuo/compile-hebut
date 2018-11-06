package com.mengyunzhi.entity;

import com.mengyunzhi.enums.SymbolType;

public class Symbol {

    private SymbolType symbolType;

    private char value;

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
}
