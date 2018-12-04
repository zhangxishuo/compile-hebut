package com.mengyunzhi;

import com.mengyunzhi.entity.Quaternion;
import com.mengyunzhi.entity.Word;
import com.mengyunzhi.enums.OperatorType;

import java.util.ArrayList;
import java.util.List;

/**
 * 语义分析程序
 */
public class SemanticAnalysis {

    // 四元式列表
    private static List<Quaternion> quaternionList = new ArrayList<>();

    private static Quaternion currentQuaternion;

    private static String E_VALUE;
    private static String F_VALUE;
    private static String T_VALUE;

    /**
     * 添加四元式
     * @param operatorType   运算符类型
     * @param firstOperand   第一操作数
     * @param secondOperand  第二操作数
     */
    private static Quaternion addQuaternion(OperatorType operatorType, String firstOperand, String secondOperand) {
        Quaternion quaternion = new Quaternion();
        quaternion.setOperatorType(operatorType);
        quaternion.setFirstOperand(firstOperand);
        quaternion.setSecondOperand(secondOperand);
        quaternion.setResult("TEMP_" + quaternionList.size());
        quaternionList.add(quaternion);
        return quaternion;
    }

    /**
     * 打印四元式列表
     */
    public static void printQuaternionList() {
        for (Quaternion quaternion : quaternionList) {
            System.out.println(quaternion);
        }
    }

    public static void service(int index, Word word) {
        switch (index) {
            case 1:
                currentQuaternion = addQuaternion(OperatorType.PLUS, E_VALUE, T_VALUE);
                E_VALUE = currentQuaternion.getResult();
                break;
            case 2:
                currentQuaternion = addQuaternion(OperatorType.MINUS, E_VALUE, T_VALUE);
                E_VALUE = currentQuaternion.getResult();
                break;
            case 3:
                E_VALUE = T_VALUE;
                break;
            case 4:
                currentQuaternion = addQuaternion(OperatorType.MULTI, T_VALUE, F_VALUE);
                T_VALUE = currentQuaternion.getResult();
                break;
            case 5:
                currentQuaternion = addQuaternion(OperatorType.DIV, T_VALUE, F_VALUE);
                T_VALUE = currentQuaternion.getResult();
                break;
            case 6:
                T_VALUE = F_VALUE;
                break;
            case 7:
                F_VALUE = E_VALUE;
                break;
            case 8:
                F_VALUE = word.getValue().toString();
                break;
            default:
                break;
        }
    }
}
