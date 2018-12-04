package com.mengyunzhi.enums;

/**
 * 运算符类型
 */
public enum OperatorType {

    PLUS("+"),
    MINUS("-"),
    MULTI("*"),
    DIV("/");

    private String operator;

    OperatorType(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
