package org.whuims.irlab.viper.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Config {
    public static Properties prop = new Properties();
	
	static{
		File file = new File("src/main/resources/conf/clientCfg.properties");
		InputStream is=null;
		InputStreamReader reader=null;
		try {
			is=new FileInputStream(file);
			reader=new InputStreamReader(is,"UTF-8");
			prop.load(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			e=null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try{
				if(reader != null){
					reader.close();
				}
			}catch(Exception e){
				
			}
			
		}
	}
	
	public synchronized static String getPropValue(String key, String defaultVal){
		if(prop.containsKey(key))
			return prop.getProperty(key);
		return defaultVal;
	}
}
