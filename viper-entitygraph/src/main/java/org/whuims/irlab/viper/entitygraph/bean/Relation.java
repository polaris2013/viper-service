/**
 * 
 */
package org.whuims.irlab.viper.entitygraph.bean;

/**
 * @author juyuan
 *
 * @date 2010-10-23 下午05:12:39 

 */
public class Relation {
   private int sid;
   private int distance;
   private String type;
   private int tid;
   private int freq=1;//计算这个实体对出现了几次
   public int getSid() {
		return sid;
	}
   public int getFreq() {
	  return freq;
   }
   public void setFreq(int freq) {
 	 this.freq = freq;
   }
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
   
}
