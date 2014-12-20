package org.whuims.irlab.viper.opinion.extend;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.opinion.common.Tag;
import org.whuims.irlab.viper.opinion.utils.PositionUtils;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 *  简单指代消解，找到"他/她"，"他/她们"在上下文中所指代的具体观点持有者
 * @title RrRetriever
 * @description 简单指代消解 
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class RrResolver {
    public static String[] posAr = { Tag.PERSON_NAME_TAG, Tag.CHINESE_FAMILY_NAME_TAG, Tag.CHINESE_LAST_NAME_TAG }; // 表征单数的词性

    private static final int WORD_COUNT = 1; // 候选先行词的数量
    private static final int SENT_COUNT = 4; // 向前查找的句子的阀值

    // 单数 人称代词
    private final static String SINGLE_RR_WORDSTR1 = "她";
    private final static String SINGLE_RR_WORDSTR2 = "他";
    // 复数 人称代词
    private final static String PLURAL_RR_WORDSTR1 = "她们";
    private final static String PLURAL_RR_WORDSTR2 = "他们";

    public static final String[] RR_WORDSTR_ARRAY = { SINGLE_RR_WORDSTR1, SINGLE_RR_WORDSTR2, PLURAL_RR_WORDSTR1,
            PLURAL_RR_WORDSTR2 };

    private RrResolver() {

    }

    /**
     * 简单指代消解，找到"他/她"，"他/她们"在上下文中所指代的具体观点持有者
     * <pre>具体指代消解方法：向前找出一定阀值内的不超过wordCount个的人名词 
     * 根据一定的排除法，去除一部分, 根据一定点优先法排序 选出优先值最高的词汇
     * @param context 抽取观点的文本
     * @param sent  包含观点的句子
     * @param nId  观点持有者（人称代词）在句子中的ID
     * @return
     */
    public static Word getRR(List<Sentence<Word>> context, Sentence<Word> sent, int nId) {
        Queue<Word> queue = new ArrayDeque<Word>(); // 保存人名的堆栈
        String word = sent.get(nId).getWordStr();
        if (PositionUtils.isPart(word, RR_WORDSTR_ARRAY)) {
            queue = addStack(context, sent, nId, posAr);
            if (queue.size() > 0) {
                return queue.poll();
            }
        }
        return null;
    }
    /**
     * 返回找到的词汇
     * @param context  抽取观点的文本
     * @param sent  包含观点的句子
     * @param nId    观点持有者（人称代词）在句子中的ID
     * @param posAr  人名词汇集合
     * @return  消解候选栈
     */
    private static Queue<Word> addStack(List<Sentence<Word>> context, Sentence<Word> sent, 
            int nId, String[] posAr) {

        Queue<Word> stack = new ArrayDeque<Word>();
        if (nId > sent.size() - 1 || nId < 0) { // check Out of Index Error
            return stack;
        }
        int sentId = sent.getSentId(); // 获取句子的编号
        for (int i = nId; i >= 0; i--) {
            if (PositionUtils.isPosPart(sent.get(i).getPos(), posAr)) {
                if (stack.size() < WORD_COUNT) {
                    stack.add(sent.get(i));
                } else {
                    break;
                }
            }
        }
        int firId = 0; // 起始句子编号
        if (sentId - SENT_COUNT > 0) {
            firId = sentId - SENT_COUNT;
        }
        // 在前四句内查找人物代词
        for (int i = sentId - 1; i >= firId; i--) {
            boolean label = false; // 跳出最外层句子循环的标志
            for (int j = context.get(i).getWordCount() - 1; j >= 0; j--) {
                if (PositionUtils.isPosPart(context.get(i).get(j).getPos(), posAr)) {
                    if (stack.size() < WORD_COUNT) {
                        stack.add(context.get(i).get(j));
                    } else {
                        label = true;
                        break;
                    }
                }
            }
            if (label) {
                break;
            }
        }
        return stack;
    }
    
}
