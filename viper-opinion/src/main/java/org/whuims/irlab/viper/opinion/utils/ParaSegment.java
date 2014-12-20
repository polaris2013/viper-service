package org.whuims.irlab.viper.opinion.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分句算法
 * @title ParaSegment
 * @description 分句算法
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class ParaSegment {
    static final String CONTENT_TAG = "“.+?”"; // 匹配引号内的内容
    static final String SEGMENT_TAG = "。|\\？|！"; // 分句的标点符号
    static final String TAG = " \\$\\d+? "; // 引号标识正则

    /**
     * 将一篇文章按照段落和标点分句 匹配引号内容标志：空格+$+编号+空格
     * 
     * @param srcStr
     *            待分析的文章内容
     * @return 分句后的文章内容
     */
    public static String paraSegmentToStr(String srcStr) {
        Queue<String> queue = new LinkedList<String>(); // 存放引号内容的队列
        /* 将引号内容顺序换出 */
        Pattern pt = Pattern.compile(CONTENT_TAG);
        Matcher mt = pt.matcher(srcStr);
        int n = 0;
        while (mt.find()) {
            srcStr = srcStr.replace(mt.group(), " $" + n + " ");
            queue.add(mt.group());
            n++;
        }
        /* 开始分句 */
        Pattern pat = Pattern.compile(SEGMENT_TAG);
        Matcher mat = pat.matcher(srcStr);
        while (mat.find()) {
            srcStr = srcStr.replace(mat.group(), mat.group() + "\n");
        }
        /* 将引号内容顺序换入 */
        Pattern ptt = Pattern.compile(TAG);
        Matcher mtt = ptt.matcher(srcStr);
        while (mtt.find()) {
            srcStr = srcStr.replace(mtt.group(), queue.poll());
        }
        /* 去除多余的回车符 */
        srcStr = srcStr.replaceAll("\n+|\n\\s+|\\s+\\n+", "\n");

        return srcStr;

    }

    /**
     * 将一篇文章按照段落和标点分句
     * 
     * @param srcSrc
     *            待分析的文章
     * @return 所有句子的list
     */
    public static ArrayList<String> paraSegmentToList(String srcStr) {
        Queue<String> queue = new LinkedList<String>(); // 存放引号内容的队列
        ArrayList<String> list = new ArrayList<String>(); // 存放分句后单句的list
        /* 将引号内容(引号中的疑似观点句不作切分)顺序换出 */
        Pattern pt = Pattern.compile(CONTENT_TAG);
        Matcher mt = pt.matcher(srcStr);
        int n = 0;
        while (mt.find()) {
            srcStr = srcStr.replace(mt.group(), " $" + n + " ");
            queue.add(mt.group());
            n++;
        }
        /* 开始分句 */
        Pattern pat = Pattern.compile(SEGMENT_TAG);
        Matcher mat = pat.matcher(srcStr);
        while (mat.find()) {
            srcStr = srcStr.replace(mat.group(), mat.group() + "\n");
        }
        String[] s = srcStr.split("\n");

        /* 将引号内容顺序换入 */
        Pattern ptt = Pattern.compile(TAG);
        for (int i = 0; i < s.length; i++) {
            Matcher mtt = ptt.matcher(s[i]);
            while (mtt.find()) {
                s[i] = s[i].replace(mtt.group(), queue.poll());
            }
        }
        /* 去掉空的元素并加入到list中 */
        for (int i = 0; i < s.length; i++) {
            if (!s[i].trim().equals("")) {
                list.add(s[i].trim());
            }
        }
        return list;
    }

}
