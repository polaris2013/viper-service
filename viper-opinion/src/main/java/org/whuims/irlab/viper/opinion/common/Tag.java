package org.whuims.irlab.viper.opinion.common;

import org.whuims.irlab.viper.common.CFGKEYS;
import org.whuims.irlab.viper.common.ViperConfig;
import org.whuims.irlab.viper.opinion.utils.FileUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 定义词性标注分类集合的常量， 包括人名词性集合NR_TAG_LIST，人称代词词性集合RR_TAG_LIST, 观点指示动词集合V_TAG_LIST,动词后的标点符号集合VP_TAG_LIST
 *
 * <pre>具体词性标注的意义，参见 http://blog.163.com/qindisney2003@126/blog/static/32335595201071810103562/
 * 
 * @title Tag
 * @description  词性集合
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class Tag {
    // 以下词性标记，为常用的实体，包括 人名实体，地点，机构等
    /** n 名词 */
    public static final String NAME_TAG = "/n";
    /** nr 人名 */
    public static final String PERSON_NAME_TAG = "/nr";
    /** nr1 汉语姓氏  */
    public static final String CHINESE_FAMILY_NAME_TAG = "/nr1";
    /** nr2 汉语名字  */
    public static final String CHINESE_LAST_NAME_TAG = "/nr2";
    /** ns 地名  */
    public static final String PLACE_NAME_TAG = "/ns";
    /** nt 机构团体名  */
    public static final String ORG_NAME_TAG = "/nt";
    /**名词性语素*/
    public static final String OTHER_NAME_TAG = "/ng"; //
    /**人称代词*/
    public static final String PERSONAL_REFER_TAG = "/rr";

    // 以下词性标记，为断句，或者句中标点 标志

    /** wj 句号。*/
    public static final String PERIOD_TAG = "/wj";
    /** ww 问号。*/
    public static final String QUESTION_TAG = "/ww";
    /** wt 叹号，全角：！ 半角：!*/
    public static final String SIGH_TAG = "/wt";
    /**  wd 逗号，全角：， 半角：,*/
    public static final String COMMA_TAG = "/wd";
    /** wf 分号，全角：； 半角： ;*/
    public static final String SEMICOLON_TAG = "/wf";
    /** wm 冒号，全角：： 半角： */
    public static final String COLON_TAG = "/wm";
    /** ws 省略号 */
    public static final String ELLIPSIS_TAG = "/ws";

    // 以下词性标记，多为修饰词，副词，助词用于观点持有者的扩展

    /** k 后缀 */
    public static final String SUFFIX_WORD_TAG = "/k";
    /** s 处所词 */
    public static final String WHERE_WORD_TAG = "/s";
    /** f 方位词 */
    public static final String DIRECTION_WORD_TAG = "/f";
    /**ude1 的, 助词*/
    public static final String PARTICLE_WORD_TAG = "/ude1";
    /**a 形容词*/
    public static final String ADJECTIVE_WORD_TAG = "/a";
    /**b 区别词, 如 初级，大型*/
    public static final String DISTICNT_WORD_TAG = "/b";
    /**m 数词 如345*/
    public static final String NUMERAL_WORD_TAG = "/m";
    /**q 量词 如x朵 x条等*/
    public static final String MEASURE_WORD_TAG = "/q";
    /**r 代词*/
    public static final String REFER_WORD_TAG = "/r";
    /**d 副词*/
    public static final String ADVERB_WORD_TAG = "/d";

    /**vn 名动词,如xx调查，xx研究等*/
    public static final String VIRTUAL_WORD_TAG = "/vn";
    // 标记人名的词性{包括人名、姓和人称代词}
    public static final String[] NR_TAG = { PERSON_NAME_TAG, CHINESE_FAMILY_NAME_TAG, CHINESE_LAST_NAME_TAG,
            OTHER_NAME_TAG, PLACE_NAME_TAG, ORG_NAME_TAG };

    // 有效的人称代词的数组，它在后边会去掉
    public static final String[] RR_TAG = { "他/rr", "她/rr", "他们/rr", "她们/rr", "我/rr", "我们/rr", "它/rr" };

    // 寻找动词后的标点符号标识
    public static final String[] VP_TAG = { PERIOD_TAG, QUESTION_TAG, SIGH_TAG, COMMA_TAG, SEMICOLON_TAG, COLON_TAG,
            ELLIPSIS_TAG };

    // 引号内容成句判断的标点符号标识
    public static final String[] SENTENCE_TAG = { PERIOD_TAG, QUESTION_TAG, SIGH_TAG, COMMA_TAG, SEMICOLON_TAG,
            COLON_TAG, ELLIPSIS_TAG };

    /**
     * 人物词性标记 列表
     */
    public static final List<String> NR_TAG_LIST = Arrays.asList(Tag.NR_TAG);

    
    private static ViperConfig config=ViperConfig.getInstance();
    /**
     * 动词词性标记 列表
     */
    public static final List<String> V_TAG_LIST = FileUtils.readFileList(config.getPropValue(CFGKEYS.ICTCLAS_VIEWVERB_DIC, "nvrs/word.txt"), "utf-8");

    /**
     * 人称代词标记 列表
     */
    public static final List<String> RR_TAG_LIST = Arrays.asList(Tag.RR_TAG);

    /**
     * 动词后的标点符号标识 列表
     */
    public static final List<String> VP_TAG_LIST = Arrays.asList(Tag.VP_TAG);

    /* 返回某个人名标记的优先级 */
    public static double getNamePri(String nameTag) {
        double i = 1.0;
        if (nameTag.equals(Tag.PERSON_NAME_TAG) || nameTag.equals(Tag.CHINESE_FAMILY_NAME_TAG)
                || nameTag.equals(Tag.CHINESE_LAST_NAME_TAG) || nameTag.equals("/defnr1")) {
            i = 1.7;
        } else if (nameTag.equals(Tag.PERSONAL_REFER_TAG)) {
            i = 1.4;
        } else if (nameTag.equals(Tag.ORG_NAME_TAG)) { // 其他普通名词
            i = 1.3;
        } else if (nameTag.equals(Tag.PLACE_NAME_TAG)) {
            i = 1.25;
        } else if (nameTag.equals(Tag.OTHER_NAME_TAG)) {
            i = 1.2;
        } else if (nameTag.startsWith(Tag.NAME_TAG)) {
            i = 1.1;
        }
        return i;
    }
}
