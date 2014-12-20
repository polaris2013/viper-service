package org.whuims.irlab.viper.opinion.extend;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.opinion.common.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 扩展观点持有者, 向前查找一些词汇 
 * @title ViewerExtender
 * @description  扩展观点持有者, 向前查找一些词汇 
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class ViewerExtender {
    /**"/n /s /f /a /b /m /q /k /r /d"*/
    static String[] model1 = { Tag.NAME_TAG, Tag.WHERE_WORD_TAG, Tag.DIRECTION_WORD_TAG, Tag.ADJECTIVE_WORD_TAG,
            Tag.DISTICNT_WORD_TAG, Tag.MEASURE_WORD_TAG, Tag.SUFFIX_WORD_TAG, Tag.REFER_WORD_TAG, Tag.ADVERB_WORD_TAG };
    /**"/vn /ude1";*/
    static String[] model2 = { Tag.VIRTUAL_WORD_TAG, Tag.PARTICLE_WORD_TAG };
    /** "/n /k /f /ng /nr /nr1 /nr2";*/
    static String[] model3 = { Tag.NAME_TAG, Tag.SUFFIX_WORD_TAG, Tag.DIRECTION_WORD_TAG, Tag.OTHER_NAME_TAG,
            Tag.PERSON_NAME_TAG, Tag.CHINESE_FAMILY_NAME_TAG, Tag.CHINESE_LAST_NAME_TAG };
    /** "/ude1 /nr1 /nr2 /nr /rr /s";*/
    static String[] model4 = { Tag.PARTICLE_WORD_TAG, Tag.PERSON_NAME_TAG, Tag.CHINESE_FAMILY_NAME_TAG,
            Tag.CHINESE_LAST_NAME_TAG, Tag.PERSONAL_REFER_TAG, Tag.WHERE_WORD_TAG };

    static List<String> extendList1 = Arrays.asList(model1); // 向前扩展
    static List<String> extendList2 = Arrays.asList(model2);
    static List<String> extendList3 = Arrays.asList(model3); // 向后扩展
    static List<String> extendList4 = Arrays.asList(model4);

    static final String UDE_WORD_STR = "的";

    private ViewerExtender() {

    }

    /**
     * 观点持有者扩展，在句中前向，后向扩展
     * @param sent  句子
     * @param nameId  原始观点持有者在句中的ID
     * @return
     */
    public static String getViewHolder(Sentence<Word> sent, int nameId) {
        StringBuffer buf = new StringBuffer();
        Stack<String> stack = new Stack<String>();
        stack.push(sent.get(nameId).getWordStr());
        // 向前扩展
        for (int i = nameId - 1; i >= 0; i--) {
            Word word = sent.get(i);
            String pos = word.getPos();
            if (pos.length() < 2) {
                continue;
            }
            if (extendList1.contains(pos.substring(0, 2)) || extendList2.contains(pos)) {
                stack.push(word.getWordStr());
                continue;
            }
            break;
        }
        if (stack.peek().equals(UDE_WORD_STR)) {
            stack.pop();
        }
        while (!stack.isEmpty()) {
            buf.append(stack.pop());
        }
        // 第一次向后扩展
        int index = 0;
        for (int i = nameId + 1; i < sent.size(); i++) {
            Word word = sent.get(i);
            String pos = word.getPos();
            if (extendList3.contains(pos)) {
                buf.append(word.getWordStr());
                index++;
                continue;
            }
            break;
        }
        // 第二次向后扩展
        for (int i = nameId + index + 1; i < sent.size(); i++) {
            Word word = sent.get(i);
            String pos = word.getPos();
            if (extendList4.contains(pos)) {
                buf.append(word.getWordStr());
                continue;
            }
            break;
        }
        String bufStr = "";
        if (buf.toString().endsWith(UDE_WORD_STR)) {
            bufStr = buf.toString().substring(0, buf.toString().length() - 1);
        } else {
            bufStr = buf.toString();
        }
        return bufStr;
    }
}
