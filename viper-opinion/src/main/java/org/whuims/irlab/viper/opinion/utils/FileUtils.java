package org.whuims.irlab.viper.opinion.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * 读取文件的工具类 
 * @title FileUtils
 * @description 读取文件的工具类 
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class FileUtils {
    /**
     * 根据指定编码读取文件，能够去除全角空格和空行还有空格（Backspace）
     * 
     * @param filename
     *            需要读取的文件
     * @param encoding
     *            文件的编码
     * @return 返回一个文件内容的String
     * */
    public static String readFile(File file, String encoding) {

        BufferedReader in = null;
        try {
            StringBuffer sb = new StringBuffer();

            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));

            String line;
            while ((line = in.readLine()) != null) {
                // 去除全角空格
                line = line.replace((char) 12288, ' ');
                /* 去除空行 */
                if (!line.trim().equals("")) {
                    sb.append(line.trim().replaceAll(" ", "") + "\n");
                }
            }
            return sb.toString();
        } catch (Exception ex3) {
            Log.error(FileUtils.class, "文件读取失败:" + ex3.getMessage());
            return "";
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.wrong(FileUtils.class, "文件流关闭异常！" + e.getMessage());
            }

        }
    }

    /**
     * 读取文件内容，不包括空行和注释行（//）
     * 
     * @param filename
     *            需要按行读取的文件
     * @param encoding
     *            文件的编码
     * @return 返回按照每行添加的List
     * */
    public static List<String> readFileList(String filename, String encoding) {
        List<String> list = new LinkedList<String>();
        BufferedReader in = null;
        try {
            File file = new File(filename);

            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
            String line;
            while ((line = in.readLine()) != null) {
                /* 去掉空行和注释行 */
                if (!line.equals("") && !line.trim().startsWith("//")) {
                    list.add(line.trim());
                }
            }

            return list;

        } catch (Exception ex3) {
            Log.error(FileUtils.class, "文件读取失败:" + ex3.getMessage());
            return list;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.wrong(FileUtils.class, "文件流关闭异常！" + e.getMessage());
            }
        }
    }
}
