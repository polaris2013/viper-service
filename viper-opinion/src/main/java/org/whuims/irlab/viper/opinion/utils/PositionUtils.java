package org.whuims.irlab.viper.opinion.utils;

import org.whuims.irlab.viper.common.analyzer.AnalyzerHelper;



/**
 * 处理词语特殊位置的类
 * @title PositionUtils
 * @description  处理词语特殊位置的类
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class PositionUtils {
    /**
     * 判断某个索引是否在给定的两个左右对称成对出现(如 “ 和 ” )标记之间
     * 
     * @param str
     *            整个句子
     * @param left
     *            左标记
     * @param right
     *            右标记
     * @param index
     *            要判断的索引位置
     * @return  如果在对称标记之间，则返回true，否则返回false
     */
    public static boolean inside(String str, String left, String right, int index) {
        boolean boo = false;
        String leftStr = str.substring(0, index);
        /* 向前找到一个left和right，如果left的索引在right的索引后面，则证明index在两个标记之间 */
        int leftIndex = leftStr.lastIndexOf(left);
        int leftIndex2 = leftStr.lastIndexOf(right);
        if (leftIndex > leftIndex2) {
            boo = true;
        }
        return boo;
    }

    /**
     * 确定引号里的内容是否以给定的词性标记组的任意标记结尾
     * 
     * @param src
     *            已经标记词性的句子
     * @param yhTag
     *            词性标记组
     * @return 只要有一个标记符合，则返回true,否则返回false
     */
    public static boolean isEnd(String src, String[] yhTag) {
        boolean boo = false;
        String[] srcArray = src.split(" ");
        /* 由于外边有引号，因此此处的结束位是倒数第二组字符 */
        if (isPosPart(srcArray[srcArray.length - 2], yhTag)) {
            boo = true;
        }
        return boo;
    }

    /**
     * 判断一个标记属性的词的词性是否属于一个词性组的范畴
     * 
     * @param src
     *            标记词性的词 , 如
     * @param posArray
     *            词性组
     * @return 如果属于返回true，否则返回false
     */
    public static boolean isPosPart(String src, String[] posArray) {
        String posTag = AnalyzerHelper.getWordPos(src);
        return isPart(posTag, posArray);
    }

    public static boolean isPart(String src, String[] array) {
        boolean boo = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(src)) {
                boo = true;
                break;
            }
        }
        return boo;
    }

}
