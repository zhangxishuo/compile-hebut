package com.mengyunzhi.adapter;

import com.mengyunzhi.entity.Word;
import com.mengyunzhi.enums.LegalSignType;
import com.mengyunzhi.enums.StateType;

/**
 * 类型适配器
 */
public class TypeAdapter {

    // 所有状态数组
    private static StateType[] stateTypes = {
            StateType.S0,
            StateType.S1,
            StateType.S2,
            StateType.S3,
            StateType.S4,
            StateType.S5,
            StateType.S6,
            StateType.S7,
            StateType.S8,
            StateType.S9,
            StateType.S10,
            StateType.S11,
            StateType.S12,
            StateType.S13,
            StateType.S14,
            StateType.S15,
        };

    public static LegalSignType wordAdapter(Word word) {

        switch (word.getType()) {
            case INTEGER:
            case REAL:
            case ID:
                return LegalSignType.IDENTITY;
            case PL:
                return LegalSignType.PLUS;
        }
    }

    public static StateType stateAdapter(int state) {
        return stateTypes[state];
    }
}
