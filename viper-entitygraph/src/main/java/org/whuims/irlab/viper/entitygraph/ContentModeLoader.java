/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.entitygraph;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;


import org.whuims.irlab.viper.detector.CharsetDetector;
import org.whuims.irlab.viper.detector.FileCharsetDetector;
import org.whuims.irlab.viper.extractor.HtmlExtractor;

/**
 * @title Loader
 * @description TODO 
 * @author juyuan
 * @date 2014年8月31日
 * @version 1.0
 */
public class ContentModeLoader {

    public final static int FILE_MODE = 2;
    public final static int STRING_MODE = 0;
    public final static int URL_MODE = 1;
    private final static String def_encode = "utf-8";
    private static ContentModeLoader instance = null;

    private ContentModeLoader() {

    }

    public static ContentModeLoader getInstance() {
        if (instance == null) {
            return new ContentModeLoader();
        }
        return instance;
    }

    private String getCharset(File inputFile) {
        try {
            FileCharsetDetector dt = new FileCharsetDetector();
            return dt.guestFileEncoding(inputFile);
        } catch (Exception e) {

//        logger.info("getCharset error! using default!", e);
            return def_encode;
        }
    }

    public String load(String input, int mode) {
        String content = "";
        if (mode == FILE_MODE) {
            File inputFile = new File(input);
            if (!inputFile.exists()) {
//               logger.fatal("input file not exist!");
                return content;
            }
            String encode = getCharset(inputFile);
            try {
                content = FileUtils.readFileToString(inputFile, encode);
            } catch (IOException e) {
                // TODO Auto-generated catch block
//                logger.error("io error!", e);
            }
        } else if (mode == URL_MODE) {
            CharsetDetector detector = new CharsetDetector(input);
            String charset = detector.detect();
            HtmlExtractor extractor = new HtmlExtractor(input, charset);
            content = extractor.extract();

        } else if(mode==STRING_MODE) {
            content = input;
        }
        return content;
    }
}
