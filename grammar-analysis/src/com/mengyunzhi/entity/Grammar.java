package com.mengyunzhi.entity;

import com.mengyunzhi.enums.LegalSignType;

/**
 * 语法实体
 */
public class Grammar {

    // 左侧合法符号
    private LegalSignType legalSign;

    // 待规约字符串
    private String string;

    public Grammar(LegalSignType legalSign, String string) {
        this.legalSign = legalSign;
        this.string = string;
    }

    public LegalSignType getLegalSign() {
        return legalSign;
    }

    public void setLegalSign(LegalSignType legalSign) {
        this.legalSign = legalSign;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
