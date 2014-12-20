/**
 * 
 */
package org.whuims.irlab.viper.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.whuims.irlab.viper.common.ViperConfig;


/**
 * 停用词过滤器
 * 
 * @author herochen
 *
 * version 2010-8-7
 */
public class StopWordFilter {
    private static final  String STOPWORD_FILE_CFGKEY="viper.entitygraph.resource.stopword";
	private static Set<String> stopwords = new HashSet<String>();
	//add config?
	private static ViperConfig config=ViperConfig.getInstance();
	static{
	    String filepath=config.getPropValue(STOPWORD_FILE_CFGKEY, "conf/stopwords.txt");
	    if(!new File(filepath).exists()){
	        filepath="src/main/resources/"+filepath;
	    }
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "utf-8"));
			String buffer = null;
			while((buffer = br.readLine()) != null){
				if(buffer.startsWith("//")){
					continue;
				}
				stopwords.add(buffer);
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public static boolean isStopword(String term){
		return stopwords.contains(term);
	}
	
	public static void main(String args[]){
	   System.out.println( StopWordFilter.isStopword("a")+" "+StopWordFilter.isStopword("呼哧"));
	}
}
