package com.mengyunzhi;

import com.mengyunzhi.entity.Location;
import com.mengyunzhi.entity.Word;
import com.mengyunzhi.entity.Symbol;
import com.mengyunzhi.enums.SymbolType;
import com.mengyunzhi.enums.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * 词法分析程序
 */
public class LexicalAnalysis {

    public static boolean currentOperatorState = false;

    private static int currentState = 0;
    private static int finalState   = -1;

    // 单词列表
    private static List<Word> wordList = new ArrayList<>();

    // 临时字符串存储
    private static StringBuilder string = new StringBuilder();

    // 临时单词存储
    private static Word word = null;

    // 分析数字时相关数据
    private static long w = 0;
    private static long e = 0;
    private static long n = 0;
    private static long p = 0;

    // 当前位置
    private static Location currentLocation;

    /**
     * 获取单词列表
     */
    public static List<Word> getWordList() {
        return wordList;
    }

    /**
     * 判断字符的类型
     */
    public static Symbol getSymbol(char i, int row, int col) {
        Symbol symbol = new Symbol();
        symbol.setValue(i);
        Location location = new Location(row, col);
        symbol.setLocation(location);
        if (i >= '0' && i <= '9') {
            symbol.setSymbolType(SymbolType.DIGIT);
        } else if ((i >= 'a' && i <= 'z') || (i >= 'A' && i <= 'Z')) {
            symbol.setSymbolType(SymbolType.LETTER);
        } else if (i == '<') {
            symbol.setSymbolType(SymbolType.LT);
        } else if (i == '>') {
            symbol.setSymbolType(SymbolType.GT);
        } else if (i == '=') {
            symbol.setSymbolType(SymbolType.EQ);
        } else if (i == '+') {
            symbol.setSymbolType(SymbolType.PLUS);
        } else if (i == '-') {
            symbol.setSymbolType(SymbolType.MINUS);
        } else if (i == '*') {
            symbol.setSymbolType(SymbolType.MULTIPLY);
        } else if (i == '/') {
            symbol.setSymbolType(SymbolType.DIVIDE);
        } else if (i == ':') {
            symbol.setSymbolType(SymbolType.COLON);
        } else if (i == '.') {
            symbol.setSymbolType(SymbolType.POINT);
        } else if (i == ' ') {
            symbol.setSymbolType(SymbolType.OTHER);
        } else {
            symbol.setSymbolType(SymbolType.INVALID);
        }
        return symbol;
    }

    /**
     * 判断当前类型是否是运算符类型
     */
    public static boolean checkOperatorOrNot(SymbolType symbolType) {
        return symbolType.equals(SymbolType.LT) || symbolType.equals(SymbolType.GT) || symbolType.equals(SymbolType.EQ) || symbolType.equals(SymbolType.COLON);
    }

    /**
     * 分析当前字符
     */
    public static int execute(Symbol symbol) {
        SymbolType symbolType = symbol.getSymbolType();
        switch (currentState) {
            case -1:
                currentState = 0;
                currentOperatorState = false;

            case 0:
                // 获取当前位置
                currentLocation = symbol.getLocation();
                switch (symbolType) {
                    case LETTER:
                        currentState = 1;
                        string.append(symbol.getValue());
                        break;

                    case DIGIT:
                        currentState = 2;
                        w = symbol.getValue() - '0';
                        e = 1;
                        n = 0;
                        p = 0;
                        break;

                    case LT:
                        currentState = 3;
                        currentOperatorState = true;
                        break;

                    case GT:
                        currentState = 4;
                        currentOperatorState = true;
                        break;

                    case COLON:
                        currentState = 5;
                        currentOperatorState = true;
                        break;

                    case EQ:
                        currentState = finalState;
                        word = new Word(currentLocation);
                        word.setType(Type.EQ);
                        break;

                    case PLUS:
                        currentState = finalState;
                        word = new Word(currentLocation);
                        word.setType(Type.PL);
                        wordList.add(word);
                        break;

                    case MINUS:
                        currentState = finalState;
                        word = new Word(currentLocation);
                        word.setType(Type.MI);
                        wordList.add(word);
                        break;

                    case MULTIPLY:
                        currentState = finalState;
                        word = new Word(currentLocation);
                        word.setType(Type.MU);
                        wordList.add(word);
                        break;

                    case DIVIDE:
                        currentState = finalState;
                        word = new Word(currentLocation);
                        word.setType(Type.DI);
                        wordList.add(word);
                        break;

                    case OTHER:
                        currentState = finalState;
                        break;

                    default:
                        currentState = finalState;
                        return -1;

                } break;

            case 1: switch (symbolType) {
                case LETTER:
                case DIGIT:
                    string.append(symbol.getValue());
                    break;

                default:
                    currentState = finalState;
                    word = new Word(currentLocation);
                    Type type = checkKeyword();
                    if (type.equals(Type.ID)) {
                        word.setType(Type.ID);
                        word.setValue(string);
                    } else {
                        word.setType(type);
                    }
                    wordList.add(word);
                    string = new StringBuilder();
                    break;

            } break;

            case 2: {
                switch (symbolType) {
                    case DIGIT:
                        w = 10 * w + symbol.getValue() - '0';
                        break;
                    case POINT:
                        currentState = 6;
                        break;
                    case LETTER:
                        if (symbol.getValue() == 'e' || symbol.getValue() == 'E') {
                            currentState = 7;
                            break;
                        }
                    default:
                        currentState = finalState;
                        word = new Word(currentLocation);
                        word.setType(Type.INTEGER);
                        word.setValue(w);
                        wordList.add(word);
                        break;
                }
            } break;

            case 3: {
                currentState = finalState;
                switch (symbolType) {
                    case EQ:
                        word = new Word(currentLocation);
                        word.setType(Type.LE);
                        wordList.add(word);
                        break;
                    case GT:
                        word = new Word(currentLocation);
                        word.setType(Type.NE);
                        wordList.add(word);
                        break;
                    default:
                        word = new Word(currentLocation);
                        word.setType(Type.LT);
                        wordList.add(word);
                        break;
                }
            } break;

            case 4: {
                currentState = finalState;
                switch (symbolType) {
                    case EQ:
                        word = new Word(currentLocation);
                        word.setType(Type.GE);
                        wordList.add(word);
                        break;
                    default:
                        word = new Word(currentLocation);
                        word.setType(Type.GT);
                        wordList.add(word);
                        break;
                }
            } break;

            case 5: {
                switch (symbolType) {
                    case EQ:
                        currentState = finalState;
                        word = new Word(currentLocation);
                        word.setType(Type.IS);
                        wordList.add(word);
                        break;
                    default:
                        currentState = finalState;
                }
            } break;

            case 6: {
                switch (symbolType) {
                    case DIGIT:
                        w = 10 * w + symbol.getValue() - '0';
                        n ++;
                        break;
                    case LETTER:
                        if (symbol.getValue() == 'e' || symbol.getValue() == 'E') {
                            currentState = 7;
                            break;
                        }
                    default:
                        currentState = finalState;
                        word = new Word(currentLocation);
                        word.setType(Type.REAL);
                        word.setValue(w * Math.pow(10, e * p - n));
                        wordList.add(word);
                        break;
                }
            } break;

            case 7: {
                switch (symbolType) {
                    case PLUS:
                        currentState = 8;
                        break;
                    case MINUS:
                        currentState = 8;
                        e = -1;
                        break;
                    case DIGIT:
                        currentState = 9;
                        p = 10 * p + symbol.getValue() - '0';
                        break;
                }
            } break;

            case 8: {
                switch (symbolType) {
                    case DIGIT:
                        currentState = 9;
                        p = 10 * p + symbol.getValue() - '0';
                        break;
                }
            } break;

            case 9: {
                switch (symbolType) {
                    case DIGIT:
                        p = 10 * p + symbol.getValue() - '0';
                        break;
                    default:
                        currentState = finalState;
                        word = new Word(currentLocation);
                        word.setType(Type.REAL);
                        word.setValue(w * Math.pow(10, e * p - n));
                        wordList.add(word);
                        break;
                }
            } break;
        }
        return 0;
    }

    /**
     * 判断当前字符串是否是关键字
     */
    private static Type checkKeyword() {
        String value = string.toString().toUpperCase();
        Type type = null;
        switch (value) {
            case "BEGIN":
                type = Type.BEGIN;
                break;
            case "END":
                type = Type.END;
                break;
            case "FOR":
                type = Type.FOR;
                break;
            case "IF":
                type = Type.IF;
                break;
            case "THEN":
                type = Type.THEN;
                break;
            case "ELSE":
                type = Type.ELSE;
                break;
            default:
                type = Type.ID;
        }
        return type;
    }
}
