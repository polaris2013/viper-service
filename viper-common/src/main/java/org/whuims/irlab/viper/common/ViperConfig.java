/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @title ViperConfig
 * @description TODO 
 * @author juyuan
 * @date 2014年9月16日
 * @version 1.0
 */
public class ViperConfig {
    private static Properties prop = new Properties();
    private static final String configPath = "viper-config.properties";

    private ViperConfig() {
        this.init();
    }

    private void init() {
        String configfile=configPath;
        try {
            configfile = this.getClass().getClassLoader().getResource("").toURI().getPath() + configPath;
        } catch (URISyntaxException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        File file = new File(configfile);
        if (!file.exists()) {
            file = new File("src/main/resources/viperCfg.properties");
            if (!file.exists()) {
                System.err.println("can not found config file!" + configfile);
                instance = null;
                return;
            }
        }

        InputStream is = null;
        InputStreamReader reader = null;
        try {
            is = new FileInputStream(file);
            reader = new InputStreamReader(is, "UTF-8");
            prop.load(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static ViperConfig instance; //唯一实例

    public static synchronized ViperConfig getInstance() {
        if (instance != null)
            return instance;
        return new ViperConfig();
    }

    public String getPropValue(String key, String defaultVal) {
        if (prop.containsKey(key))
            return prop.getProperty(key);
        return defaultVal;
    }
}
