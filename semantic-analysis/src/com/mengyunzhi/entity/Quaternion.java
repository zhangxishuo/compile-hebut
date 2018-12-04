package com.mengyunzhi.entity;

import com.mengyunzhi.enums.OperatorType;

/**
 * 四元式
 */
public class Quaternion {

    private OperatorType operatorType;             // 运算符类型

    private String firstOperand;                   // 第一操作数

    private String secondOperand;                  // 第二操作数

    private String result;                         // 结果

    public Quaternion() {
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorType operatorType) {
        this.operatorType = operatorType;
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        this.firstOperand = firstOperand;
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(String secondOperand) {
        this.secondOperand = secondOperand;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "< " +
                operatorType.getOperator() +
                ", " + firstOperand +
                ", " + secondOperand +
                ", " + result +
                " >";
    }
}
