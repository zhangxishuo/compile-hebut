package com.mengyunzhi.enums;

/**
 * 语法分析 合法符号枚举
 */
public enum LegalSignType {

    LEFT_BRACKET(0),            // 左括号
    RIGHT_BRACKET(1),           // 右括号
    PLUS(2),                    // 加
    MINUS(3),                   // 减
    MULTIPLY(4),                // 乘
    DIVIDE(5),                  // 除
    IDENTITY(6),                // i
    FINAL(7),                   // #
    E(8),                       // E
    T(9),                       // T
    F(10),                      // F
    START(11);                  // E'

    private int value;

    LegalSignType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
