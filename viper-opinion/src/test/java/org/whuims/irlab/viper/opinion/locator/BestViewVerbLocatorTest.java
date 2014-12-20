/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.opinion.locator;

import org.junit.Test;






import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.opinion.locator.BestViewVerbLocator;

/**
 * @title BestViewVerbLocator
 * @description TODO 
 * @author juyuan
 * @date 2014年8月31日
 * @version 1.0
 */
public class BestViewVerbLocatorTest extends BaseLocatorTest {

    @Test
    public void testGetBestViewVerbLocator() {
        System.out.println("候选观点指示动词集合 : ");
        this.listCandiates(viewVerbCandidates);
        int vwIdx = BestViewVerbLocator.getBestViewVerbID(exampleSent, viewerCandidates, viewVerbCandidates);
        Word word = exampleSent.get(vwIdx);
        System.out.println("最优指示动词: " + word.getWordStr() + " \t 词性 : " + word.getPos());
    }
}
