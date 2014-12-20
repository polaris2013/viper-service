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
import org.whuims.irlab.viper.entitygraph.processor.EntityProcessor;





/**
 * @author juyuan
 *
 * @date 2010-10-29 下午09:02:38 

 */
public class EdgeCreator {
    
    private List<Relation> relations=new ArrayList<Relation>();
    private List<Sentence<Word>> sentences=new ArrayList<Sentence<Word>>();
  
    private HashMap<Integer,String> entityInverseTable;
    private Logger LOG=LoggerFactory.getLogger(EdgeCreator.class);
	private void initCreator(EntityProcessor processor){
		relations=processor.getRelationList();
    	sentences=processor.getSentenceList();
    	entityInverseTable=processor.getEntityInverseTable();
    	
    }
    public List<Edge> create(){
        LOG.info("create edges...");
        List<Edge> edgelist=new ArrayList<Edge>();
    	for(Relation r:relations){
    		Edge e=new Edge();
    		
    		e.setSource(r.getSid());
    		e.setTarget(r.getTid());
    		e.setWeight(computeWeight(r));
    		e.setConnection(r.getType());
    		
    		e.setSuports(retrieveSupportContext(r.getSid(),r.getTid()));
    		if(!containEdge(e,edgelist)){//次数于距离共同转化为weight
    		   edgelist.add(e);
    		}
    	}
    	return edgelist;
    }
    private int computeWeight(Relation r){
    	int w=r.getFreq()*3/r.getDistance();//需改进
    	return w;
    }
    private boolean containEdge(Edge e,List<Edge> edgelist){
    	boolean dict=false;
    	for(Edge edge:edgelist){
    		if((edge.getSource()==e.getSource())&&(edge.getTarget()==e.getTarget())){
    			dict=true;
    			break;
    		};
    	}
    	return dict;
    }
    private List<String> retrieveSupportContext(int sid,int tid){
       	List<String> supportlist=new ArrayList<String>();
    	String sterm=getTerm(sid);
       	String tterm=getTerm(tid);
   
       	for(Sentence<Word> sent:sentences){
       	   
       		if(containTerm(sent,sterm)&&containTerm(sent,tterm)){
       			supportlist.add(sent.getTermSentence());
       		}
       	}
       	return supportlist;
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
    private String getTerm(int idx){
    	String term=entityInverseTable.get(idx);
    	if(term==null) term="";
    	return term;
    	
    }
    public  EdgeCreator(EntityProcessor processor){
       	initCreator(processor);
    }

	
}
