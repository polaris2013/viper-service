/**
 * 
 */
package org.whuims.irlab.viper.entitygraph.builder;

import java.util.List;

/**
 * @author juyuan
 *
 * @date 2010-10-27 下午04:06:51 

 */
public class Edge {
    private int source;
    private int target;
    private int weight;
    private String connection;
    private List<String> supports;//实体关系的支持例句
	
	
    
	public List<String> getSupports() {
		return supports;
	}
	public void setSuports(List<String> suports) {
		this.supports = suports;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
    
    
    
    
    
}
