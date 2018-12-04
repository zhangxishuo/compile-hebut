package com.mengyunzhi;

import com.mengyunzhi.adapter.TypeAdapter;
import com.mengyunzhi.config.GrammarConfig;
import com.mengyunzhi.entity.Grammar;
import com.mengyunzhi.entity.Location;
import com.mengyunzhi.entity.Word;
import com.mengyunzhi.enums.LegalSignType;
import com.mengyunzhi.enums.StateType;
import com.mengyunzhi.enums.Type;

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
        max = words.size();

        // 添加终止符号
        Word word = new Word(new Location(0, 0));
        word.setType(Type.FINAL);
        wordList.add(word);

        // 获取语法配置
        grammarList = GrammarConfig.getConfig();
        // 获取分析表
        analysisTable = GrammarConfig.getAnalysisTable();
    }

    /**
     * 语法分析逻辑
     */
    public static void service() {
        // 声明结果
        String result;
        // 循环
        while (true) {
            if (index > max) {
                // 到末尾还未成功，报错
                System.out.println("ERROR");
                // 终止循环
                break;
            } else {
                // 查表
                result = searchInTable(stateTypeStack.peek(), wordList.get(index));
            }
            if (result.equals("")) {
                // 错误处理
                System.out.println("分析失败");
                // 提示错误位置
                printErrorInfo(wordList.get(index));
                // 终止循环
                break;
            } else if (result.charAt(0) == 'S') {
                // 如果当前为状态转换
                // 截取需要转换到的状态
                String subResult = result.substring(1);
                int state = Integer.valueOf(subResult);
                // 状态入栈
                stateTypeStack.add(TypeAdapter.stateAdapter(state));
                // 字符入栈
                legalSignStack.add(TypeAdapter.wordAdapter(wordList.get(index)));

                // 打印状态转换信息
                printStateChange(TypeAdapter.stateAdapter(state), wordList.get(index));
                printStackInfo();

                // 字符位置后移
                index ++;
            } else if (result.charAt(0) == 'R') {
                // 如果当前为规约
                // 截取需要规约的表达式索引
                String subResult = result.substring(1);
                System.out.println(subResult);
                int indexInGrammarList = Integer.valueOf(subResult);

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
                int next = Integer.valueOf(searchInTable(stateTypeStack.peek(), legalSignStack.peek()));
                // 新状态入栈
                stateTypeStack.push(TypeAdapter.stateAdapter(next));

                // 输出归约信息
                printReduction(indexInGrammarList);
                printStackInfo();
            } else if (result.equals(ACCEPT_MESSAGE)) {
                // 语法分析成功
                System.out.println("分析成功");
                // 终止循环
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

    /**
     * 错误处理
     */
    private static void printErrorInfo(Word word) {
        // 打印错误信息
        System.out.println("Error at: row " + word.getLocation().getRow() + " col " + word.getLocation().getCol() + " 错误语法 " + word.getValue());
    }

    /**
     * 状态转移信息
     */
    private static void printStateChange(StateType newStateType, Word word) {
        System.out.println("状态转移: " + newStateType.name() + "入栈");
        System.out.println("单词入栈: " + word.getType());
    }

    /**
     * 归约信息
     */
    private static void printReduction(int index) {
        System.out.println("使用产生式" + index + ": " + grammarList.get(index) + "进行归约");
    }

    /**
     * 打印相关栈信息
     */
    private static void printStackInfo() {
        System.out.print("状态栈: ");
        stateTypeStack.forEach((value) -> System.out.print(value + " "));
        System.out.println();
        System.out.print("符号栈: ");
        legalSignStack.forEach((value) -> System.out.print(value + " "));
        System.out.println();
    }
}
