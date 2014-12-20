package org.whuims.irlab.viper.opinion;

import org.whuims.irlab.viper.opinion.ViewMiner;
import org.whuims.irlab.viper.opinion.entity.View;
import org.whuims.irlab.viper.opinion.utils.FileUtils;
import org.whuims.irlab.viper.opinion.utils.Log;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * 主类的测试类 - 抽取一篇文本的所有观点
 * @title ViewMinerTest
 * @description 主类的测试类 - 抽取一篇文本的所有观点
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class ViewMinerTest extends BaseAnalyzerTest {
    @Test
    public void testExtractView() {
        String rawContent = FileUtils.readFile(testFile, encoding);
        long start = System.currentTimeMillis();
        try {
            List<View> list = new ViewMiner().extractView(rawContent);
            for (Iterator<View> iterator = list.iterator(); iterator.hasNext();) {
                View view = iterator.next();
                System.out.println(view);
            }
        } catch (Exception e) {
            Log.error(ViewMinerTest.class, e.getMessage());
        }

        long end = System.currentTimeMillis();
        System.out.println("cost: " + (end - start) / 1000.0);
    }

}
