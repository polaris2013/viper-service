package org.whuims.irlab.viper.ictclas;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import ICTCLAS.I3S.AC.ICTCLAS50;

import org.whuims.irlab.viper.common.CFGKEYS;
import org.whuims.irlab.viper.common.IAnalyzer;
import org.whuims.irlab.viper.common.ViperConfig;
import org.whuims.irlab.viper.common.exception.AnalyzerException;


/**
 *  分词器调用类, 以后会做成对象线程池
 * @title Splitter
 * @description 分词器调用类
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class IctclasAnalyzer implements IAnalyzer{

    private ICTCLAS50 ictclas = null;
    private boolean available;
    private static IctclasAnalyzer instance = null; //唯一实例
    private static ViperConfig config=ViperConfig.getInstance();
    private static String eCode="UTF-8";
    private IctclasAnalyzer() throws IOException {  
        this.ictclas = new ICTCLAS50();
        this.available = init();
    }
     
    public static synchronized IctclasAnalyzer getInstance() {
        if (instance == null) {
            try {
                instance = new IctclasAnalyzer();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.err.println("init analyzer fail!");
                System.exit(1);
            }
        }
        return instance;
    }

    /** 初始化分词器 */
    private boolean init() throws IOException {
        String argu = config.getPropValue(CFGKEYS.ICTCLAS_ROOT, "");
        if (ictclas.ICTCLAS_Init(argu.getBytes(eCode)) == false) {
            System.err.println("Init Fail!");
            return false;
        } else {
            return true;
        }
    }

    /** 加载用户词典 */
    public int importUserDict(String userDict) throws AnalyzerException {
        if (available) {
         
            try {
                int nCount;
                nCount = ictclas.ICTCLAS_ImportUserDictFile(userDict.getBytes(eCode),3);
                
                return nCount;
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                throw new AnalyzerException ("import user dict exception!",e);
            }
          
        } else {
            return 0;
        }
    }
    
    

    /**
     * 文本分词
     * 
     * @param str
     *            待分词的字符串
     * @param tag
     *            是否标记词性，0：不标记;1：标记
     * @return 分词后的字符串列表
     * @throws IOException
     */
    public String analyzeText(String str, int tag) throws AnalyzerException {
        if (available) {
            String string;
            byte[] bytes;
            try {
                bytes = ictclas.ICTCLAS_ParagraphProcess(str.getBytes(eCode),0,tag);
                string = new String(bytes, 0, bytes.length, eCode);
                return string.trim();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                throw new AnalyzerException("ictclas analyze fail!",e);
            }
        } else {
            return null;
        }
    }

    /**
     * 文件分词
     * 
     * @param sSrcFilename
     *            要分词的文件
     * @param sDestFilename
     *            分词后保存的文件
     * @param tag
     *            是否加词性标记，0:不加;1：加
     * @return 分词成功，返回true，否则返回false
     * @throws IOException
     */
    public boolean analyzeFile(String sSrcFilename, String sDestFilename, int tag) throws IOException {
        boolean boo = false;
        if (available) {
            boo = ictclas.ICTCLAS_FileProcess(sSrcFilename.getBytes(eCode), 0,tag,sDestFilename.getBytes("gb2312") );
            return boo;
        }
        return boo;
    }

    
    
    /**
     * 退出分词器
     */
    public void exit() {
        ictclas.ICTCLAS_Exit();
    }

}
