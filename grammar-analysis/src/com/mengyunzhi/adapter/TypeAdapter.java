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

    /**
     * 单词适配器
     */
    public static LegalSignType wordAdapter(Word word) {

        LegalSignType legalSignType = null;

        switch (word.getType()) {
            case INTEGER:
            case REAL:
            case ID:
                legalSignType = LegalSignType.IDENTITY;
                break;
            case PL:
                legalSignType = LegalSignType.PLUS;
                break;
            case MI:
                legalSignType = LegalSignType.MINUS;
                break;
            case MU:
                legalSignType = LegalSignType.MULTIPLY;
                break;
            case DI:
                legalSignType = LegalSignType.DIVIDE;
                break;
            case LEFT_BRACKET:
                legalSignType = LegalSignType.LEFT_BRACKET;
                break;
            case RIGHT_BRACKET:
                legalSignType = LegalSignType.RIGHT_BRACKET;
                break;
            case FINAL:
                legalSignType = LegalSignType.FINAL;
                break;
            default:
                System.out.println("对不起，当前类型不支持");
        }

        return legalSignType;
    }

    /**
     * 状态适配器
     */
    public static StateType stateAdapter(int state) {
        return stateTypes[state];
    }
}
