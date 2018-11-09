package com.mengyunzhi;

import com.mengyunzhi.adapter.TypeAdapter;
import com.mengyunzhi.config.GrammarConfig;
import com.mengyunzhi.entity.Grammar;
import com.mengyunzhi.entity.Word;
import com.mengyunzhi.enums.LegalSignType;
import com.mengyunzhi.enums.StateType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 语法分析程序
 */
public class GrammarAnalysis {

    // 状态栈
    private static Stack<StateType> stateTypeStack = new Stack<>();
    // 合法符号栈
    private static Stack<LegalSignType> legalSignStack = new Stack<>();
    // 单词串
    private static List<Word> wordList = new ArrayList<>();
    // 语法列表
    private static List<Grammar> grammarList;
    // 分析表
    private static String[][] analysisTable;
    // 当前索引
    private static int index = 0;
    // 最大索引
    private static int max = 0;
    // 可接受的消息
    private static final String ACCEPT_MESSAGE = "ACCEPT";

    /**
     * 初始化数据
     */
    public static void init(List<Word> words) {
        // 初始化状态
        stateTypeStack.add(StateType.S0);
        // 初始化符号
        legalSignStack.add(LegalSignType.START);
        // 初始化单词串
        wordList = words;
        // 设置最长长度
        max = words.size() - 1;

        // 获取语法配置
        grammarList = GrammarConfig.getConfig();
        // 获取分析表
        analysisTable = GrammarConfig.getAnalysisTable();
    }

    /**
     * 语法分析逻辑
     */
    public static void service() {
        // 查表
        String result = searchInTable(stateTypeStack.peek(), wordList.get(index));
        // 循环
        while (true) {
            if (result.equals("")) {
                // 错误处理
                break;
            } else if (result.charAt(0) == 'S') {
                // 如果当前为状态转换
                // 截取需要转换到的状态
                String subResult = result.substring(1);
                int state = Integer.getInteger(subResult);
                // 状态入栈
                stateTypeStack.add(TypeAdapter.stateAdapter(state));
                // 字符入栈
                legalSignStack.add(TypeAdapter.wordAdapter(wordList.get(index)));
                // 字符位置后移
                index ++;
            } else if (result.charAt(0) == 'R') {
                // 如果当前为规约
                // 截取需要规约的表达式索引
                String subResult = result.substring(1);
                int indexInGrammarList = Integer.getInteger(subResult);
                // 获取相应的语法
                Grammar grammar = grammarList.get(indexInGrammarList);
                // 获取需要出栈的元素数
                int length = grammar.getString().length();
                // 相应元素出栈
                for (int i = 0; i < length; i ++) {
                    stateTypeStack.pop();
                    legalSignStack.pop();
                }
                // 当前规约字符入栈
                legalSignStack.push(grammar.getLegalSign());
                // 从GOTO表查询下一个状态
                result = searchInTable(stateTypeStack.peek(), legalSignStack.peek());
            } else if (result.equals(ACCEPT_MESSAGE)) {
                // 成功
                break;
            }
        }
    }

    /**
     * 查表
     */
    private static String searchInTable(StateType stateType, Word word) {
        // 将单词类型由词法分析适配到语法分析
        LegalSignType legalSignType = TypeAdapter.wordAdapter(word);
        // 查表
        return analysisTable[stateType.getValue()][legalSignType.getValue()];
    }

    /**
     * 查表
     */
    private static String searchInTable(StateType stateType, LegalSignType legalSignType) {
        // 查表
        return analysisTable[stateType.getValue()][legalSignType.getValue()];
    }
}
