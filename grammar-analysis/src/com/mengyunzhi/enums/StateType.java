package com.mengyunzhi.enums;

public enum StateType {
    S0(0),
    S1(1),
    S2(2),
    S3(3),
    S4(4),
    S5(5),
    S6(6),
    S7(7),
    S8(8),
    S9(9),
    S10(10),
    S11(11),
    S12(12),
    S13(13),
    S14(14),
    S15(15);

    private int value;

    StateType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
