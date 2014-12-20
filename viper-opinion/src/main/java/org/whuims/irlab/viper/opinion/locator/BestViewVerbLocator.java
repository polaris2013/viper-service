package org.whuims.irlab.viper.opinion.locator;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.opinion.common.Tag;
import org.whuims.irlab.viper.opinion.common.ViewIndicator;
import org.whuims.irlab.viper.opinion.utils.PositionUtils;

import java.util.List;

/**
 * 根据距离加权的算法，从候选观点指示动词中获取最优的观点指示动词。
 * @title VwordRetriever
 * @description 根据距离加权的算法求出最优的观点指示动词
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class BestViewVerbLocator {
    private static final double NE_WEIGHT = 0.4; // 接近观点持有者的权值
    private static final double PUNC_WEIGHT = 0.6; // 接近标点符号的权值

    private BestViewVerbLocator() {

    }

    /**
     *  获取最优观点指示动词的ID 如果找不到满足条件的观点动词，返回-1
     * @param sent  句子实体
     * @param viewerCandidates  观点持有者候选列表
     * @param viewVerbCandidates   观点指示动词候选列表
     * @return
     */
    public static int getBestViewVerbID(Sentence<Word> sent, List<Word> viewerCandidates, List<Word> viewVerbCandidates) {
        int id = -1;
        double bestVWeight = 100.0; // 最优观点指示动词的权值的初值设置
        if (viewVerbCandidates.size() == 1) {
            /** 如果某句话只有一个观点指示动词的话，把他作为观点指示动词 */
            return viewVerbCandidates.get(0).getWordID();
        }
        for (int i = 0; i < viewVerbCandidates.size(); i++) {

            /** 如果该观点动词在引号内，则跳过 */
            int index = viewVerbCandidates.get(i).getWordIndex();
            // 这里index+1的原因是，确保index在这个单词里边
            boolean boo = PositionUtils.inside(sent.getPosSentence(), "“", "”", index + 1);
            if (boo) {
                continue;
            }

            double vCandidate = 0.0; // 该动词的权值

            int disNe = 100; // 距离NE的距离初始值设置
            boolean boo1 = false; // 锁定disNe的布尔值
            int disPu = 100; // 距离标点的距离初始值设置
            boolean boo2 = false; // 锁定disPu的布尔值

            Word wor = viewVerbCandidates.get(i);
            int vId = wor.getWordID();

            /** 找最近的NE和标点的距离 */
            int j = vId - 1;
            // 在vID前边查找
            for (; j >= 0; j--) {
                if (boo1 == false
                        && (sent.get(j).getIndicator() == ViewIndicator.nameIndicator || sent.get(j).getIndicator() == ViewIndicator.rrIndicator)) {
                    disNe = vId - j - 1;
                    boo1 = true;
                } else if (boo2 == false && Tag.VP_TAG_LIST.contains(sent.get(j).getPos())) {
                    disPu = vId - j - 1;
                    boo2 = true;
                }
                if (boo1 && boo2) {
                    break;
                }
            }
            boo1 = boo2 = false;
            j = vId + 1;
            // 在vId后边查找
            for (; j < sent.size(); j++) {
                if (boo1 == false
                        && (sent.get(j).getIndicator() == ViewIndicator.nameIndicator || sent.get(j).getIndicator() == ViewIndicator.rrIndicator)) {
                    int disNe2 = j - vId - 1;
                    if (disNe > disNe2) {
                        disNe = disNe2;
                        boo1 = true;
                    }
                } else if (boo2 == false && Tag.VP_TAG_LIST.contains(sent.get(j).getPos())) {
                    int disPu2 = j - vId - 1;
                    if (disPu > disPu2) {
                        disPu = disPu2;
                        boo2 = true;
                    }
                }
                if (boo1 && boo2) {
                    break;
                }
            }
            // 计算权值
            vCandidate = NE_WEIGHT * disNe + PUNC_WEIGHT * disPu;
            if (vCandidate < bestVWeight) { // 将最小的权值赋给bestVWeight;
                bestVWeight = vCandidate;
                id = vId;
            }
        }
        return id;
    }
}
