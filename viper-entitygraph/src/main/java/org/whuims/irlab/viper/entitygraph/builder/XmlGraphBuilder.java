/**
 * 
 */
package org.whuims.irlab.viper.entitygraph.builder;

import java.util.List;

import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.entitygraph.processor.EntityProcessor;



/**
 * @author juyuan
 *
 * @date 2010-10-27 下午03:55:22 

*/
public class XmlGraphBuilder {
    public String xmlh="<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"><key id=\"e\" for=\"node\" attr.name=\"email\" attr.type=\"string\"/><key id=\"p\" for=\"node\" attr.name=\"photo\" attr.type=\"string\"/><key id=\"w\" for=\"edge\" attr.name=\"weight\" attr.type=\"double\"/><key id=\"i\" for=\"node\" attr.name=\"intro\" attr.type=\"string\"/><key id=\"c\" for=\"edge\" attr.name=\"connect\" attr.type=\"array\"/><key id=\"c\" for=\"edge\" attr.name=\"weight\" attr.type=\"double\"> <default>1.0</default></key><graph xmlns=\"\" edgedefault=\"undirected\">"; 

	private String xmlString;
    private String buildNode(Node n){
    	StringBuilder sb=new StringBuilder();
    	sb.append("<![CDATA[");
    	sb.append("实体类型："+n.getType()+"\n\r");
    	sb.append("出现该实体的片段："+"\n");
    	for(String s:n.getSents()){
    		sb.append(s+"\n");
    	}
    	sb.append("]]>");
    	return sb.toString();
    }
    private String buildEdge(Edge e){
    	StringBuilder sb=new StringBuilder();
    	sb.append("<![CDATA[");
    	sb.append("实体关系:"+"\n"+e.getConnection()+"\n\r");
    	sb.append("支持文档片段："+"\n\r");
        for(String s:e.getSupports()){
        	sb.append(s+"\n");
        }
        sb.append("]]>");
        return sb.toString();
    	
    
    }
    
    public String buildGraph(){
        StringBuilder sb=new StringBuilder();
        sb.append(xmlh);
        NodeCreator nodecreator=new NodeCreator(processor);
        List<Node> nodelist=nodecreator.create();
        
        for(Node e:nodelist){
            
          String unit="<node id=\""+e.getNodeId()+"\">"
                  +"<data key=\"n\">"+e.getTerm()+"</data>"
                  +"<data key=\"p\">"+e.getPic()+"</data>"
                  +"<data key=\"i\">"+buildNode(e)+"</data>"+"</node>";
          sb.append(unit);
        }
        sb.append("<!--edges-->");
        EdgeCreator edgeCreator=new EdgeCreator(processor);
        List<Edge> edgelist=edgeCreator.create();
        for(Edge e:edgelist){
           String unit="<edge source=\""+e.getSource()+"\" "+ "target=\""+e.getTarget()+"\">"
                      +"<data key=\"w\">"+e.getWeight()+"</data>"
                      +"<data key=\"c\">"+buildEdge(e)+"</data>"
                      +"</edge>";
           sb.append(unit);
        }
           sb.append("</graph></graphml>");
           xmlString=sb.toString();
           return xmlString;
    }
    
    private EntityProcessor processor;
    
    public List<Word> getPersonList(){
          return processor.getPersonList();
    }
    public List<Word>getOrgList(){
        return processor.getOrgList();
    }
    public List<Word> getPlaceList(){
        return processor.getPlaceList();
    }
    public XmlGraphBuilder(String content,int langOpt ){
        processor=new EntityProcessor(content,langOpt);
        processor.process();
    }

    
}
