/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.entitygraph;

import java.util.List;




import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.entitygraph.builder.XmlGraphBuilder;

/**
 * @title GraphFascade
 * @description TODO 
 * @author juyuan
 * @date 2014年9月2日
 * @version 1.0
 */
public class EntityGraphFacade {
    private int loadMode;
    private String userInput;
    private int langOpt;
    private XmlGraphBuilder builder;
    private ContentModeLoader modeLoader = ContentModeLoader.getInstance();

    public EntityGraphFacade(String _userInput, int _loadMode, int _langOpt) {
        this.userInput = _userInput;
        this.loadMode = _loadMode;
        this.langOpt = _langOpt;
    }

    public String runBuild() {
        String content = modeLoader.load(userInput, loadMode);
        builder = new XmlGraphBuilder(content, langOpt);
        String xmlString = builder.buildGraph();
        return xmlString;

    }

    public List<Word> getPersonList() {
        return builder.getPersonList();
    }

    public List<Word> getPlaceList() {
        return builder.getPlaceList();
    }

    public List<Word> getOrgList() {
        return builder.getOrgList();
    }

    public static void main(String args[]) {
        String testEnStr = "in our test,Bill Clinton was born a in Europe, we work at WTO.";//指代消解，实体识别
//        String testCnUrl = "http://expo2010.sina.com.cn/news/roll/20101031/005915316.shtml";
//        String testCnFile = "test//wu.txt";
        EntityGraphFacade fascade = new EntityGraphFacade(testEnStr, 0, 2);
        // fascade=new EntityGraphFascade(testCnUrl,1,1);
        // fascade=new EntityGraphFascade(testCnFile,2,1);
        System.out.println(fascade.runBuild());
    }
}
