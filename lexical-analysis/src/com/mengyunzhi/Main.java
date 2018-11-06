package com.mengyunzhi;

import com.mengyunzhi.entity.Symbol;
import com.mengyunzhi.entity.Word;
import com.mengyunzhi.enums.SymbolType;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String path = "src/com/mengyunzhi/";

    public static void main(String[] args) throws IOException {
        // 输入文件
        File inputFile = new File( path + "in.txt");
        // 输出文件
        File outputFile = new File(path + "out.txt");
        // 如果文件不存在, 创建文件
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        // 初始化输入流
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));

        // 初始化
        StringBuilder builder = new StringBuilder();
        String readLine = bufferedReader.readLine();
        builder.append(readLine).append('\n');

        // 读取文件
        while (readLine != null) {
            readLine = bufferedReader.readLine();
            builder.append(readLine).append('\n');
        }

        // 关闭输入流
        bufferedReader.close();

        // 词法分析
        String[] strings = builder.toString().split("\n");
        strings = Arrays.copyOf(strings, strings.length - 1);
        // 循环每行代码
        for (int row = 0; row < strings.length; row ++) {

            // 获取每行内容
            String string = strings[row];
            int result = 0;

            // 分析词法
            for (int i = 0; i < string.length(); i ++) {
                // 判断符号类型
                Symbol symbol = LexicalAnalysis.getSymbol(string.charAt(i));

                // 非法符号，错误处理
                if (symbol.getSymbolType().equals(SymbolType.INVALID)) {
                    printErrorInfo(row + 1, i + 1, symbol.getValue());
                } else {
                    // 分析符号
                    if (LexicalAnalysis.checkOperatorOrNot(symbol.getSymbolType()) && !LexicalAnalysis.currentOperatorState) {
                        otherSymbolExe();
                    }
                    result = LexicalAnalysis.execute(symbol);
                }
            }
            if (result != -1) {
                otherSymbolExe();
            }
        }

        // 初始化输出流
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

        // 写入结果到缓存
        List<Word> wordList = LexicalAnalysis.getWordList();
        for (Word word : wordList) {
            bufferedWriter.write(word.toString() + "\r\n");
        }

        // 压入到文件
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * 其余Other字符串执行
     */
    private static void otherSymbolExe() {
        Symbol symbol = new Symbol();
        symbol.setSymbolType(SymbolType.OTHER);
        LexicalAnalysis.execute(symbol);
    }

    /**
     * 错误处理
     */
    private static void printErrorInfo(int row, int col, char error) {
        System.out.println("Error at: row " + row + " col " + col + " 非法字符 " + error);
    }
}
