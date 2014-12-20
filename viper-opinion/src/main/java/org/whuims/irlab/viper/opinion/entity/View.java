package org.whuims.irlab.viper.opinion.entity;

/**
 * 一个观点的实体 
 * @title View
 * @description 一个观点的实体 
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class View {
    private String viewerName = ""; // 观点持有者抽取名称
    private String viewerNameExtend = ""; // 扩展后的观点持有者
    private String namePos = ""; // 观点持有者词性标记
    private String viewWord = ""; // 观点动词
    private String viewText = ""; // 观点正文

    

   

 

    public String getViewerName() {
        return viewerName;
    }

    public void setViewerName(String viewerName) {
        this.viewerName = viewerName;
    }

    public String getViewerNameExtend() {
        return viewerNameExtend;
    }

    public void setViewerNameExtend(String viewerNameExtend) {
        this.viewerNameExtend = viewerNameExtend;
    }

    public String getViewWord() {
        return viewWord;
    }

    public void setViewWord(String viewWord) {
        this.viewWord = viewWord;
    }

    public String getNamePos() {
        return namePos;
    }

    public void setNamePos(String namePos) {
        this.namePos = namePos;
    }

   

    public String getViewText() {
        return viewText;
    }

    public void setViewText(String viewText) {
        this.viewText = viewText;
    }

    @Override
    public  String toString() {
        return "View [cName=" + viewerName + ", fName=" + viewerNameExtend + ", namePos=" + namePos + ", vWord=" + viewWord + ", view="
                + viewText + "]";
    }

}
