package com.mengyunzhi;

import com.mengyunzhi.entity.Symbol;
import com.mengyunzhi.entity.Word;
import com.mengyunzhi.enums.SymbolType;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * 词法分析主入口
 */
public class Main {

    // 文件路径
    private static final String path = "src/com/mengyunzhi/";

    /**
     * 主入口
     */
    public static void main(String[] args) throws IOException {

        // 初始化输入输出文件
        File inputFile = new File( path + "in.txt");
        File outputFile = new File(path + "out.txt");

        // 如果输出文件不存在，则创建文件
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        // 初始化输入流
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));

        // 初始化字符串
        StringBuilder builder = new StringBuilder();

        // 读取第一行到追加到字符串并添加回车
        String readLine = bufferedReader.readLine();
        builder.append(readLine).append('\n');

        // 继续读取文件，追加到字符串，添加回车
        while (readLine != null) {
            readLine = bufferedReader.readLine();
            builder.append(readLine).append('\n');
        }

        // 关闭输入流
        bufferedReader.close();

        // 根据回车分割字符串，移除最后一个多余的元素
        String[] strings = builder.toString().split("\n");
        strings = Arrays.copyOf(strings, strings.length - 1);

        // 循环每行代码
        for (int row = 0; row < strings.length; row ++) {

            // 获取每行内容
            String string = strings[row];

            // 初始化结果为0
            int result = 0;

            // 分析词法
            for (int i = 0; i < string.length(); i ++) {

                // 分析符号
                Symbol symbol = LexicalAnalysis.getSymbol(string.charAt(i), row + 1, i + 1);

                if (symbol.getSymbolType().equals(SymbolType.INVALID)) {
                    // 非法符号，错误处理
                    printErrorInfo(symbol);
                    // 遇到非法字符，用空格填充
                    otherSymbolExe();
                } else {
                    // 判断当前符号是否需要空格
                    if (LexicalAnalysis.checkOperatorOrNot(symbol.getSymbolType()) && !LexicalAnalysis.currentOperatorState) {
                        otherSymbolExe();
                    }
                    // 获取符号分析结果
                    result = LexicalAnalysis.execute(symbol);
                }
            }

            // 如果没有中途结束，添加空格执行
            if (result != -1) {
                otherSymbolExe();
            }
        }

        // 初始化输出流
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

        // 获取分析单词列表
        List<Word> wordList = LexicalAnalysis.getWordList();

        // 写入结果到缓存
        for (Word word : wordList) {
            bufferedWriter.write(word.printInfo() + "\r\n");
        }

        // 压入到文件
        bufferedWriter.flush();

        // 关闭输出流
        bufferedWriter.close();
    }

    /**
     * 其余Other字符串执行
     */
    private static void otherSymbolExe() {
        // 实力化symbol同时设置为其他类型
        Symbol symbol = new Symbol();
        symbol.setSymbolType(SymbolType.OTHER);

        // 词法分析该类型
        LexicalAnalysis.execute(symbol);
    }

    /**
     * 错误处理
     */
    private static void printErrorInfo(Symbol symbol) {
        // 打印错误信息
        System.out.println("Error at: row " + symbol.getLocation().getRow() + " col " + symbol.getLocation().getCol() + " 非法字符 " + symbol.getValue());
    }
}
