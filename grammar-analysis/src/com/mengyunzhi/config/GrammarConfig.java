package com.mengyunzhi.config;

import com.mengyunzhi.entity.Grammar;
import com.mengyunzhi.enums.LegalSignType;

import java.util.ArrayList;
import java.util.List;

/**
 * 语法配置
 */
public class GrammarConfig {

    // 语法配置
    private static List<Grammar> config = new ArrayList<>();

    // 分析表
    private static String[][] analysisTable = {
            {"S4", "", "", "", "", "", "S5", "", "1", "2", "3"},              // 0
            {"", "", "S6", "S7", "", "", "", "ACCEPT", "", "", ""},           // 1
            {"", "R3", "R3", "R3", "S8", "S9", "", "R3", "", "", ""},         // 2
            {"", "R6", "R6", "R6", "R6", "R6", "", "R6", "", "", ""},         // 3
            {"S4", "", "", "", "", "", "S5", "", "10", "2", "3"},             // 4
            {"", "R8", "R8", "R8", "R8", "R8", "", "R8", "", "", ""},         // 5
            {"S4", "", "", "", "", "", "S5", "", "", "11", "3"},              // 6
            {"S4", "", "", "", "", "", "S5", "", "", "12", "3"},              // 7
            {"S4", "", "", "", "", "", "S5", "", "", "", "13"},               // 8
            {"S4", "", "", "", "", "", "S5", "", "", "", "14"},               // 9
            {"", "S15", "S6", "S7", "", "", "", "", "", "", ""},              // 10
            {"", "R1", "R1", "R1", "S8", "S9", "", "R1", "", "", ""},         // 11
            {"", "R2", "R2", "R2", "S8", "S9", "", "R2", "", "", ""},         // 12
            {"", "R4", "R4", "R4", "R4", "R4", "", "R4", "", "", ""},         // 13
            {"", "R5", "R5", "R5", "R5", "R5", "", "R5", "", "", ""},         // 14
            {"", "R7", "R7", "R7", "R7", "R7", "", "R7", "", "", ""}          // 15
        };

    // 静态代码块初始化
    static {

        // 初始化语法配置
        config.add(new Grammar(LegalSignType.START, "E"));
        config.add(new Grammar(LegalSignType.E, "E+T"));
        config.add(new Grammar(LegalSignType.E, "E-T"));
        config.add(new Grammar(LegalSignType.E, "T"));
        config.add(new Grammar(LegalSignType.T, "T*F"));
        config.add(new Grammar(LegalSignType.T, "T/F"));
        config.add(new Grammar(LegalSignType.T, "F"));
        config.add(new Grammar(LegalSignType.F, "(E)"));
        config.add(new Grammar(LegalSignType.F, "i"));
    }

    /**
     * 获取配置
     */
    public static List<Grammar> getConfig() {
        return config;
    }

    /**
     * 获取分析表
     */
    public static String[][] getAnalysisTable() {
        return analysisTable;
    }
}
