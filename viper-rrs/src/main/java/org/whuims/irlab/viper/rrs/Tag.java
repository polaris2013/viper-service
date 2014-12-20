/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.rrs;



/**
 * @title Tag
 * @description TODO 
 * @author juyuan
 * @date 2014年10月24日
 * @version 1.0
 */
public class Tag {

    public static String rPrefix = "/r";
    public static String nPrefix = "/n";

    /**
     * 人名词性集合
     */
    public static String[] PERSONNE_POS_SET = { "/nr", "/nr1", "/nr2", "/nrj", "/nrf" }; // 表征单数的词性
    /**
     * 处所词词性集合
     */
    public static String[] PLACENE_POS_SET = { "/ns", "/nsf" };
    /**
     *  其他专名 集合
     */
    public static String[] OTHERNE_POS_SET = { "/nz", "/nl","/nt" };
    /**
     * 时间词，词性
     */
    public static String[] TIMENE_POS_SET = { "/t", "/tg" };

    /**
     * 人称代词
     */
    public static String[] RP_POS_SET = { "/rr" };

    /**
     * 谓词性指示代词
     */
    public static String[] RO_POS_SET = { "/rzv", "/rz", "/rg" };
    /**
     * 时间指示代词
     */
    public static String[] RT_POS_SET = { "/rzt", "/ryt" };

    /**
     * 处所指示代词
     */
    public static String[] RS_POS_SET = { "/rzs", "/rys" };

    public static final String[] SINGLE_PERSON_RR_WORDS = { "她", "他" };
    public static final String[] PLURAL_PERSON_RR_WORDS = { "他们", "她们" };
    public static final String[] SINGLE_THING_RR_WORDS = { "它", "这","这样", "那", "这个","那个" };
    public static final String[] PLURAL_THING_RR_WORDS = { "它们", "这些", "那些" };
}
