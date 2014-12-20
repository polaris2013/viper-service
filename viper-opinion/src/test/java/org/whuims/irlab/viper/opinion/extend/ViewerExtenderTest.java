/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.opinion.extend;

import org.junit.Test;

import org.whuims.irlab.viper.opinion.extend.ViewerExtender;
import org.whuims.irlab.viper.opinion.locator.BaseLocatorTest;
import org.whuims.irlab.viper.opinion.locator.BestViewVerbLocator;
import org.whuims.irlab.viper.opinion.locator.BestViewerLocator;

/**
 * @title ViewerExtenderTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月28日
 * @version 1.0
 */
public class ViewerExtenderTest extends BaseLocatorTest {
    @Test
    public void testGetViewHolder() {
        int vId = BestViewVerbLocator.getBestViewVerbID(exampleSent, viewerCandidates, viewVerbCandidates);
        int viewerId = BestViewerLocator.getBestViewerID(exampleSent, vId, viewerCandidates);
        System.out.println("获取最优观点持有者：" + exampleSent.get(viewerId).getWordStr());
        String extViewer = ViewerExtender.getViewHolder(exampleSent, viewerId);
        System.out.println("扩展后的观点持有者：" + extViewer);
    }
}
