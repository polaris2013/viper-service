/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.opinion.locator;

import org.junit.Test;

import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.opinion.locator.BestViewVerbLocator;
import org.whuims.irlab.viper.opinion.locator.BestViewerLocator;

/**
 * @title BestViewerLocatorTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月31日
 * @version 1.0
 */
public class BestViewerLocatorTest extends BaseLocatorTest {

    @Test
    public void testGetBestViewerID() {

        System.out.println("潜在观点持有者候选集合 : ");
        this.listCandiates(viewerCandidates);
        int vId = BestViewVerbLocator.getBestViewVerbID(exampleSent, viewerCandidates, viewVerbCandidates);
        System.out.println("获取最优观点指示动词 vId: " + vId);
        int bestViewerId = BestViewerLocator.getBestViewerID(exampleSent, vId, viewerCandidates);
        Word bestViewer = exampleSent.get(bestViewerId);
        System.out.println("最优观点持有者： " + bestViewer.getWordStr() + "\t 词性" + bestViewer.getPos());
    }
}
