package org.whuims.irlab.viper.opinion;


import org.whuims.irlab.viper.common.IAnalyzer;
import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.ictclas.*;
import org.whuims.irlab.viper.opinion.analyzer.OpinionAnalyzer;
import org.whuims.irlab.viper.opinion.common.ViewIndicator;
import org.whuims.irlab.viper.opinion.entity.View;
import org.whuims.irlab.viper.opinion.extend.RrResolver;
import org.whuims.irlab.viper.opinion.extend.ViewJudge;
import org.whuims.irlab.viper.opinion.extend.ViewerExtender;
import org.whuims.irlab.viper.opinion.locator.BestViewVerbLocator;
import org.whuims.irlab.viper.opinion.locator.BestViewerLocator;
import org.whuims.irlab.viper.opinion.utils.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 主类： 读取一篇文章中所有观点
 * @title ViewMiner
 * @description  主类： 读取一篇文章中所有观点
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class ViewMiner {
    
    /**
     * 初始化中科院分词器，中科院分词器在分词的同时，做词性标注。中文分词准确率达98%以上，效果较好
     */
    private IAnalyzer analyzer = IctclasAnalyzer.getInstance();

    public ViewMiner() {

    }

    /**
     * 从文本内容中抽取观点
     * 
     * @param code
     * @return
     * @throws IOException
     */
    public List<View> extractView(String content) throws AnalyzerException {

        List<View> viewList = new LinkedList<View>();

        /** 1)预处理 */
        List<Sentence<Word>> list = preprocess(content);

        List<Word> viewerCandidates = null; // 某个句子中包含观点持有者候选词的列表
        List<Word> viewVerbCandidates = null; // 某个句子中包含观点指示动词候选词的列表

        // 针对句子循环，挖出每句中的观点
        for (int i = 0; i < list.size(); i++) {
            View view = new View();
            Sentence<Word> sent = list.get(i);

            /** 2)得到该句中人物名字列表和观点指示动词列表 */
            viewerCandidates= new ArrayList<Word>();
            viewVerbCandidates = new ArrayList<Word>();
            for (int j = 0; j < sent.size(); j++) {
                /* 循环句中每个词语 */
                Word word = sent.get(j);
                if (word.getIndicator()== ViewIndicator.nameIndicator || word.getIndicator()== ViewIndicator.rrIndicator) {
                    viewerCandidates.add(word); // 添加人名词汇
                } else if (word.getIndicator() == ViewIndicator.vwordIndicator) {
                    viewVerbCandidates.add(word); // 添加观点指示动词
                }
            }
            /** 3)进行第一次判断 */
            /* 如果没有潜在的观点持有者，则认为这句话不是观点 */
            if (viewerCandidates.size() > 0) {
                boolean boo = false;
                // 如果观点指示动词大小非0，则初步认为是观点句
                if (viewVerbCandidates.size() > 0) {
                    /* 获取最优观点指示动词 */
                    int vId = BestViewVerbLocator.getBestViewVerbID(sent, viewerCandidates, viewVerbCandidates);
                    if (vId >= 0) {
                        /* 初步获取观点持有者 */
                        int nameId = BestViewerLocator.getBestViewerID(sent, vId, viewerCandidates);
                        if (nameId >= 0) {
                            /** 4) 第二次判断观点 */
                            boolean boo2 = ViewJudge.isView(sent, nameId, vId);
                            if (boo2) { //第二次判断是观点
                                /** 5)  对人称代词进行简单指代消解 */
                                Word wor = RrResolver.getRR(list, sent, nameId);
                                /** 6)  观点持有者扩展 */
                                String extendName = "";
                                if (wor == null) { //如找不到人称代词指代的人名实体,直接填充
                                    view.setNamePos(sent.get(nameId).getPos());
                                    extendName = ViewerExtender.getViewHolder(sent, nameId);
                                } else { //找到人称代词指代的人名实体，进一步做观点持有者扩展
                                    view.setNamePos(wor.getPos());
                                    extendName = ViewerExtender.getViewHolder(list.get(wor.getSentID()),
                                            wor.getWordID());
                                }
                                view.setViewerName(sent.get(nameId).getWordStr()); // 参考观点持有者
                                view.setViewerNameExtend(extendName); // 扩展后的观点持有者
                                view.setViewWord(sent.get(vId).getWordStr()); // 参考观点指示动词
                                view.setViewText(sent.getPosSentence().replaceAll("/\\w+|\\s", "")); // 观点句
                                boo = true;
                            }
                        }
                    }
                }
                if (boo == true) {
                    viewList.add(view);
                } else {
                    /** 没有观点指示动词的情况下，放宽判断条件 观点判断，（只要有引号内是一句话，即认定为观点） 得到最终的观点列表 */
                    viewList = quoteViewJudge(list, sent, viewList, viewerCandidates);
                }
            }
        }
        return viewList;
    }

    /**
     * 预处理， 将文本内容处理为句子的列表(List<Sentence<Word>>格式)
     * 
     * @param content  待处理的文本内容
     * @return  句子的列表
     * @throws IOException
     */
    private  List<Sentence<Word>> preprocess(String content) throws AnalyzerException {
        List<Sentence<Word>> list = OpinionAnalyzer.analyzeContent(content,analyzer);
        return list;
    }


  


    /**
     * 有候选观点持有者，而没有观点指示动词的情况下，进行观点判断
     * <pre>依据 - 只要有引号内是一句话，即认定为观点 
     * 
     * @param context 输入文本的上下文
     * @param sent  某个句子
     * @param viewList 观点列表
     * @param viewerCandidate  观点持有者候选列表
     * @return
     */
    private List<View> quoteViewJudge(List<Sentence<Word>> context, Sentence<Word> sent, List<View> viewList,
            List<Word> viewerCandidates) {
        /** 没有观点指示动词时的观点判断 */
        boolean boo = ViewJudge.isView(sent.getPosSentence());
        if (boo == true) {
            View view = new View();
            String viewStr = sent.getPosSentence();
            /** 获取没有观点指示动词下的观点持有者 */
            int nameId = BestViewerLocator.getBestViewerID(sent, -1, viewerCandidates);

            if (nameId < 0) {
                Log.info(ViewMiner.class, "这句话不是观点! " + sent.getPosSentence());
            } else {
                /** 在此处指代消解 */
                Word wor = RrResolver.getRR(context, sent, nameId);
                String extendName = "";
                if (wor == null) {
                    view.setNamePos(sent.get(nameId).getPos());
                  //找不到指代的人称，则在观点持有者所在句子做扩展
                    extendName = ViewerExtender.getViewHolder(sent, nameId);  
                } else {
                    view.setNamePos(wor.getPos());
                  //在消解出的指代的对象所在的句子做观点持有者扩展
                    extendName = ViewerExtender.getViewHolder(context.get(wor.getSentID()), wor.getWordID());
                }
                view.setViewerName(sent.get(nameId).getWordStr());
                view.setViewerNameExtend(extendName);
                view.setViewWord(null);
                view.setViewText(viewStr.replaceAll("/\\w+|\\s", ""));
                viewList.add(view);
            }
        }
        return viewList;
    }

}
