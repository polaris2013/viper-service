/**
 * 
 */
package org.whuims.irlab.viper.entitygraph.builder;

import java.util.List;

/**
 * @author juyuan
 *
 * @date 2010-10-27 下午03:36:00 

 */
public class Node {
	private int nodeId;//n,p,i,id想想还有哪些字段
	private String term;//
	private String type;
	private String pic;
	private List<String> sents;
	//private String pic;
	public int getNodeId() {
		return nodeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public List<String> getSents() {
		return sents;
	}
	public void setSents(List<String> sents) {
		this.sents = sents;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	
	
}
