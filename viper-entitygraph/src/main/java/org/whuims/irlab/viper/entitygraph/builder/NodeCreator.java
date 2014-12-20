/**
 * 
 */
package org.whuims.irlab.viper.entitygraph.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
























import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.entitygraph.bean.Relation;
import org.whuims.irlab.viper.entitygraph.common.EntityTypes;
import org.whuims.irlab.viper.entitygraph.processor.EntityProcessor;


/**
 * @author juyuan
 *
 * @date 2010-10-29 下午08:52:00 

 */
public class NodeCreator {
    //Net 根据距离做长度
	public static String Person = "人物-NR";
	public static String Place = "地点-NS";
	public static String ORG = "机构-NT";


    private HashMap<String,Word> entityWordTable=new HashMap<String,Word>();
    private List<Relation> relationlist=new ArrayList<Relation>();
    private List<Sentence<Word>> sentences=new ArrayList<Sentence<Word>>();
    private HashMap<Integer,String> entityInverseTable;
    private Logger LOG=LoggerFactory.getLogger(NodeCreator.class);
 	private  void initCreator(EntityProcessor processor){
 		
    	relationlist=processor.getRelationList();
    	entityWordTable=processor.getEntityWordTable();
    	sentences=processor.getSentenceList();
    	entityInverseTable=processor.getEntityInverseTable();
    }
    
    public List<Node> create(){
        List<Node> nodelist=new ArrayList<Node>();
        LOG.info("creating nodes ... ");
       for(Relation r:relationlist){
    	   Node n1=new Node();
    	   String nodeterm=getTerm(r.getSid());
    	   n1.setTerm(nodeterm);
    	   String type=getType(nodeterm);
    	   n1.setType(type);
    	   n1.setPic(getPic(type));
    	  
    	   n1.setSents(getHYPESentence(nodeterm));
    	   n1.setNodeId(r.getSid());
    	   if(!IsContain(nodelist,n1)){
    		   nodelist.add(n1);
    	   }
    	   Node n2=new Node();
    	   String nodeterm2=getTerm(r.getTid());
    	   n2.setTerm(nodeterm2);
    	   String type2=getType(nodeterm2);
    	   n2.setType(type2);
    	   n2.setPic(getPic(type2));
    	   n2.setSents(getHYPESentence(nodeterm2));
    	   n2.setNodeId(r.getTid());
    	   if(!IsContain(nodelist,n2)){
    		   nodelist.add(n2);
    	   }
       }
       
       return nodelist;
        
    }
    private String getType(String nodeterm){
    	Word wt=entityWordTable.get(nodeterm);
        int entityType=wt.getIndicator();
        if(entityType==EntityTypes.PERSON_ENTITY){
        	return Person;
        }if(entityType==EntityTypes.PLACE_ENTITY){
        	return Place;
        }if(entityType==EntityTypes.ORG_ENTITY){
        	return ORG;
        }else{
        	return null;
        }
        		
    }
    public String getPic(String type){
    	String picpath=null;
    	if(type.equals(Person)){
    		picpath=EntityTypes.PERSON_ENTITY_PIC;
    	}if(type.equals(Place)){
    		picpath=EntityTypes.PLACE_ENTITY_PIC;
    	}if(type.equals(ORG)){
    		picpath=EntityTypes.ORG_ENTITY_PIC;
    	}
    	return picpath;
    }
    public List<String> getHYPESentence(String nodeterm){
    	List<String> sents=new ArrayList<String>();
    	for(Sentence<Word> sent:sentences){
    		if(containTerm(sent,nodeterm)){
    			sents.add(sent.getTermSentence());
    		}
    	}
    	return sents;
    }
    public boolean containTerm(Sentence<Word> sent,String term){
    	boolean dict=false;
    	for(Word wt:sent.getEntityList()){
    		if(wt.getWordStr().equals(term)){
    			dict=true;
    			break;
    		}
    	}
    	return dict;
    }
    public boolean IsContain(List<Node> nodeList,Node node){
    	boolean b=false;
    	for(Node n:nodeList){
    		if(n.getNodeId()==node.getNodeId()){
    			 b=true;
    			 break;
    		} 
    	}
    	return b;
    }
    public String getTerm(int i){
    	String term=entityInverseTable.get(i);
        if(term==null) term="";
    	return term;
    	
    }
    public NodeCreator(EntityProcessor processor){
    	this.initCreator(processor);
    }
 

}
