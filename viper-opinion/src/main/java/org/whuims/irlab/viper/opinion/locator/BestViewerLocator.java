package org.whuims.irlab.viper.opinion.locator;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.opinion.common.Tag;
import org.whuims.irlab.viper.opinion.utils.PositionUtils;

import java.util.List;

/**
 *  获取观点持有者
 * @title ViewerRetriever
 * @description 获取观点持有者
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class BestViewerLocator {
    // 说明：大权重优先级低
    private static final double ViewVerbDistWeight = 0.3; // 距离动词的权值
    private static final double SentEndDistWeight = 0.7; // min{距离句首,距离句尾}中较小距离的权值

    private static final double QuoteDistWeight = 0.5; // 距离引号的权值

    private BestViewerLocator() {

    }

    /**
     * 获取最优观点持有者的ID
     * 
     * @param sent
     *            某个句子
     * @param vId
     *            观点指示动词的ID，-1表示没有
     * @param  viewerCandiates
     *             这句话中出现的观点持有者候选词列表
     * @return 如果找不到则返回-1
     */
    public static int getBestViewerID(Sentence<Word> sent, int vId, List<Word> viewerCandidates) {
        int id = -1;
        double bestWeight = -100.0; // 最优观点持有者词语权值的初始值设置
        // System.out.println(sent.getPosSentence());

        for (int i = 0; i < viewerCandidates.size(); i++) {
            /** 如果人名在引号内，则跳过此次循环 */
            int index = viewerCandidates.get(i).getWordIndex();
            // 这里index+1是确保index在这个单词里边
            boolean boo = PositionUtils.inside(sent.getPosSentence(), "“", "”", index + 1);
            if (boo) {
                continue;
            }

            Word wor = viewerCandidates.get(i);
            int nId = wor.getWordID(); // 当前候选词在句子中的ID

            double vCandidate = -100.0; // 这个人名初始的权值
            int disV = 100; // 距离指示动词或者引号（没有指示动词的情况）的最近距离
            int disSE = 100; // 距离句首或句尾的最近距离

            /** 获取这个人名的权值 */
            double wp = Tag.getNamePri(wor.getPos());

            if (vId >= 0) { // 有观点指示动词的情况
                /** 如果可能的观点持有者词汇在动词之后的话，则跳过 */
                if (nId > vId) {
                    continue;
                }
                /** 人名与动词的距离 */
                if (sent.get(nId).getPos().equals(Tag.CHINESE_FAMILY_NAME_TAG)) {
                    // 如果是单姓的话，后边可能是他的名，所以尽量减少判断误差
                    disV = Math.abs(nId - vId) - 2;
                } else {
                    disV = Math.abs(nId - vId) - 1;
                }
            } else { // 无观点指示动词，而有成句引号内容时，距离引号最近的优先
                disV = disWithQuote(sent, nId);
            }
            // System.out.println("disV="+disV);

            /** 词语到句首或句尾的距离 */
            int tailInt = sent.getWordCount() - nId - 1; // 到句尾的距离
            disSE = (tailInt > nId) ? nId : tailInt;

            /** 综合权值计算 */
            if (vId >= 0) {
                vCandidate = (1 - ((ViewVerbDistWeight * disV + SentEndDistWeight * disSE) / sent.getWordCount())) * wp; // 权值计算
            } else {
                vCandidate = (1 - ((QuoteDistWeight * disV + SentEndDistWeight * disSE) / sent.getWordCount())) * wp; // 权值计算
            }

            if (vCandidate > bestWeight) {
                bestWeight = vCandidate;
                id = nId;
            }
        }

        if (id >= 0) {
            // 如果获得的最优动词是“它”，则认为这句话不是观点；
            if (sent.get(id).getWordStr().equals("它")) {
                id = -1;
            }
        }
        return id;
    }

    /**
     * 获取和最近引号的距离
     * @param sent 某个句子
     * @param nId  观点持有者候选词在句子中的ID
     * @return 
     */
    private static int disWithQuote(Sentence<Word> sent, int nId) {
        int dis = 100;
        if (nId > 0) {
            for (int i = nId - 1; i >= 0; i--) {
                if (sent.get(i).getWordStr().equals("”")) {
                    dis = nId - i - 1;
                    break;
                }
            }
        }
        if (nId < sent.getWordCount() - 1) {
            for (int i = nId + 1; i < sent.getWordCount(); i++) {
                if (sent.get(i).getWordStr().equals("“")) {
                    int dis2 = i - nId - 1;
                    if (dis2 < dis) {
                        dis = dis2;
                    }
                    break;
                }
            }
        }
        return dis;
    }

}
