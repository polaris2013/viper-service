package org.whuims.irlab.viper.opinion.extend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;






import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.opinion.common.Tag;
import org.whuims.irlab.viper.opinion.utils.PositionUtils;


/**
 * 判断观点的两种方法 
 * @title ViewJudge
 * @description 判断观点的两种方法 
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class ViewJudge {
    static final int NAME_VERB_MAXDIST = 30; // 人名和动词之间允许的最远的距离
    static final int NAME_VERB_PUNCTUATION_MAXDIST = 20; // 人名和动词间有标点时允许的最远的距离

    private ViewJudge() {

    }

    /**
     * 判断一个句子是否是观点，依据
     *   <pre>一：如果动词和人名之间距离过远的话，不认为是观点
     *   <pre>二：如果人名和动词中间有断句标点符号，且两个词语超过允许的最远的距离，则不认为是观点
     * @param sent 某个句子的实体
     * @param nId  观点持有者词语在句子中的ID
     * @param vId  观点指示动词在句子中的ID
     * @return
     */
    public static boolean isView(Sentence<Word> sent, int nId, int vId) {

        /** 判断依据之一：如果动词和人名之间距离过远的话，不认为是观点 */
        if (vId - nId >= NAME_VERB_MAXDIST) {
            return false;
        }
        /** 如果人名和动词中间有断句标点符号，且两个词语超过 （NAME_VERB_PUNCTUATION_MAXDIST -人名和动词间有标点时允许的最远的距离），则不认为是观点 */
        for (int i = nId; i < vId; i++) {
            if (Tag.VP_TAG_LIST.contains(sent.get(i).getPos())) {
                if (vId - nId >= NAME_VERB_PUNCTUATION_MAXDIST) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 根据引号内容是否是一句话判断
     * @param para
     *            给出的一句话,这句话需要做了分词和词性标注 
     * @return 如果这句话判断为观点则返回true
     */
    public static boolean isView(String para) {
        boolean boo1 = false;
        /** 判断依据之二：只要句子中的引号内容成句，就判断此句子为观点句,当然已有前提为该句中有准观点持有者 */
        Pattern pt = Pattern.compile("“.+?”");
        Matcher mt = pt.matcher(para);
        while (mt.find()) {
            /* 如果引号内容以成句的标点符号结束，则认为是观点句 */
            if (PositionUtils.isEnd(mt.group(), Tag.SENTENCE_TAG)) {
                boo1 = true;
                break;
            }
        }
        return boo1;
    }

}
