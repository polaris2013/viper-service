/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.extractor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

/**
 * @title ExtractorTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月26日
 * @version 1.0
 */
public class ExtractorTest {
    @Test
     public void testUrlExtractor(){
        String urlString="http://mp.weixin.qq.com/s?__biz=MzA3ODA0NTUyNw==&mid=201548517&idx=1&sn=6c92335801add0cc0207eff6bd43f68e&3rd=MzA3MDU4NTYzMw==&scene=6#rd";
        String htmlCode="";
        String charset="gb2312";
        try {
            htmlCode=Utility.getWebContent(urlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Extractor extractor=new Extractor(htmlCode,urlString,charset);
        extractor.parse();
        
        System.out.println(extractor.getMainContent());
    }
    
//  @Test
    public void testTextExtractor(){
        String filePath = "F:/t.txt";
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"gb2312"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while(line != null){
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            String htmlCode = sb.toString();
            
            Extractor extractor=new Extractor(htmlCode,"","gb2312");
            extractor.parse();
            
            System.out.println(extractor.getMainContent());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
