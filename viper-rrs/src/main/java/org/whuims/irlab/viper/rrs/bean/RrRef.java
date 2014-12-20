/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.rrs.bean;

/**
 * @title Reference
 * @description TODO 
 * @author juyuan
 * @date 2014年10月24日
 * @version 1.0
 */
public class RrRef {
         private String rrWord;
         private String rrPos;
         private String rrSentence;
         private String referSubject;
         private String referSubjectSentence;
         private String referSubjectPos;
        public String getRrWord() {
            return rrWord;
        }
        public void setRrWord(String rrWord) {
            this.rrWord = rrWord;
        }
        public String getRrPos() {
            return rrPos;
        }
        public void setRrPos(String rrPos) {
            this.rrPos = rrPos;
        }
        public String getRrSentence() {
            return rrSentence;
        }
        public void setRrSentecne(String rrSentence) {
            this.rrSentence = rrSentence;
        }
        public String getReferSubject() {
            return referSubject;
        }
        public void setReferSubject(String referSubject) {
            this.referSubject = referSubject;
        }
        public String getReferSubjectSentence() {
            return referSubjectSentence;
        }
        public void setReferSubjectSentence(String referSubjectSentence) {
            this.referSubjectSentence = referSubjectSentence;
        }
        public String getReferSubjectPos() {
            return referSubjectPos;
        }
        public void setReferSubjectPos(String referSubjectPos) {
            this.referSubjectPos = referSubjectPos;
        }
        
        public String toString(){
            StringBuilder sb=new StringBuilder();
            sb.append("代词："+rrWord+"\n");
            sb.append("代词词性："+rrPos+"\n");
            sb.append("所在句子："+rrSentence+"\n");
            sb.append("指代的主体："+referSubject+"\n");
            sb.append("指代主体的词性："+referSubjectPos+"\n");
            sb.append("指代主体所在的上下文："+referSubjectSentence);
            
            return sb.toString();
        }
         
}
