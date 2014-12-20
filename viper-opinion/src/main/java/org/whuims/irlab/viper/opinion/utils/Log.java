package org.whuims.irlab.viper.opinion.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 建议日志记录输出器
 * @title Log
 * @description 建议日志记录输出器
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class Log {

    /**
     * 级别分别为：debug:0,info:1,warming:2,error:3,wrong:4
     */
    public static int level = 0;
    private static PrintWriter log;

    static {
        try {
            log = new PrintWriter(new FileWriter("reaper.log", true), true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("无法打开日志文件: " + "reaper.log");
            log = new PrintWriter(System.err);
        }
    }

    public static void debug(Class clazz, Object out) {
        if (level <= 0) {
            log.println(new Date() + ": " + clazz.getName() + "->debug:" + out.toString());
        }
    }

    /**  
     * 将文本信息写入日志文件  
     */
    public static void info(Class clazz, Object out) {
        if (level <= 1) {
            log.println(new Date() + ": " + clazz.getName() + "->info:" + out.toString());
        }
    }

    public static void warning(Class clazz, Object out) {
        if (level <= 2) {
            log.println(new Date() + ": " + clazz.getName() + "->warming:" + out.toString());
        }
    }

    public static void error(Class clazz, Object out) {
        if (level <= 3) {
            log.println(new Date() + ": " + clazz.getName() + "->error:" + out.toString());
        }
    }

    public static void error(Class clazz, Throwable e, String msg) {
        if (level <= 3) {
            log.println(new Date() + ": " + clazz.getName() + "->error:" + msg);
            e.printStackTrace(log);
        }
    }

    public static void wrong(Class clazz, Object out) {
        if (level <= 4) {
            log.println(new Date() + ": " + clazz.getName() + "->wrong:" + out.toString());
        }
    }

    /**
     * 将log信息输出在Console
     * @param clazz 运行类
     * @param out 信息
     */
    public static void printConsole(Class clazz, Object out) {
        System.out.println(new Date() + ": " + clazz.getName() + "->println:" + out.toString());
    }

}
