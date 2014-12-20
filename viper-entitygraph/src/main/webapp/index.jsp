<%@ page language="java" contentType="text/html; charset=gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Entity Graph  Demo</title>
<style type="text/css">
body,div,ul,li {
	margin: 0 auto;
	padding: 0;
}
.zi{
    font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
    font-size:12px;
    color:grey;
    font-weight:bold;
    padding-top:50px;
   }

body {
	font: 12px "宋体";
	text-align: center;
}

a:link {
	color: #00F;
	text-decoration: none;
}

a:visited {
	color: #00F;
	text-decoration: none;
}

.a:hover {
	color: #c00;
	text-decoration: underline;
}

.ul {
	list-style: none;
}

.main {
	clear: both;
	padding-top: 20px;
	text-align: center;
}

/*第一种形式*/
#tabs0 {
	height: 300px;
	width: 800px;
	border: 1px solid #cbcbcb;
	background-color: #f2f6fb;
}

.menu0 {
	width: 800px;
}

.menu0 li {
	display: block;
	float: left;
	padding: 4px 0;
	width: 100px;
	text-align: center;
	cursor: pointer;
	background: #FFFFff;
}

.menu0 li.hover {
	background: #f2f6fb;
}

#main0  {
	display: block;
}
#main1  {
	display: none;
}
#demoTitle{
    font: 24px "Trebuchet MS";
    padding-top:30px;
    font-weight:bold;
	text-align: center;
}
.hints{
   text-align: left;
   color:#ADADAD;
   width: 80%;
}

.notes{
   font: 13px "Trebuchet MS";
   text-align: left;
   width:50%;
   padding-top: 50px;
}
.notes span{
   font-style: italic;
   color: red;
   font-weight: bold;

}
.notes a:hover{
  
	color: #c00;
	text-decoration: underline;
}
</style>
<script type="text/javascript">
	function graphBuild() {
		 var queryText=document.getElementById('queryText').value;
		 var resURL="net/net.jsp?queryText="+queryText;
		 
		 var resFrame="<iframe src=\""+resURL+"\" frameborder=0 width=800 height=800></iframe>";
	     document.getElementById("graphArea").innerHTML=resFrame;	
	}

	function setTab(n){   
		   var tli=document.getElementById("menu0").getElementsByTagName("li");   
		    
		   for(i=0;i<tli.length;i++){   
		      tli[i].className=i==n?"hover":"";   
		       
		   }  
		   if(n==0){ document.getElementById("main0").style.display="block";
		                document.getElementById("main1").style.display="none" ; }
           if(n==1){ document.getElementById("main1").style.display="block";
           document.getElementById("main0").style.display="none" ;
               }
		    
		 }   
	
	
</script>
</head>
<body>
<div>
 <div id="demoTitle">Demo: A Small-granularity Document Visualization Approach </div><hr/>
 <div id="tabs0">
<ul class="menu0" id="menu0">
	<li onclick="setTab(0)" class="hover">文本输入</li>
	<li onclick="setTab(1)">URL 输入</li>
</ul>

 <div class="main" id="main0">
  <form name="form1" method="get" action="net/net.jsp">
   <textarea cols="80" rows="6" id="queryText" name="queryText"></textarea><br />
   <input type="submit" id="bt"  value="view" /> 
   language: <input type="radio" name="lang" value="1" />中文     <input type="radio" name="lang" value="2" />英文
   <input type="hidden" name="loadmode" value="0">
  </form>
  <br/> <div class="hints">Input example: 武汉大学在中国,胡锦涛是中国主席 <br/>Input example: In our test,Bill Clinton was born a in Europe, we work at WTO.</div>
</div>
<div class="main" id="main1">
  <form name="form2" method="get" action="net/net.jsp">
    <input size="80" type="text"  id="queryText" name="queryText"><br />
    <input type="submit" id="btu"  value="view" />
    language: <input type="radio" name="lang" value="1" />中文     <input type="radio" name="lang" value="2" />英文
    <input type="hidden" name="loadmode" value="1">
 </form>
<br/> <div class="hints">Input example: http://edition.cnn.com/2013/03/20/politics/israel-obama-visit/index.html?hpt=hp_t1<br/> &nbsp;&nbsp;&nbsp;  http://expo2010.sina.com.cn/news/roll/20101031/005915316.shtml </div>
</div>
</div>

<div  class="notes">
   <p> Technical Features &gt;&gt;</p> 
      <ul> 
        <li> Load Module: support text and direct <a href="load.html" target="_blank">URL </a>input  mode<br/>&nbsp; &nbsp; </li>
        <li> Named Entity Extract: <a href="http://nlp.stanford.edu:8080/ner/" target="_blank">stanford NER</a>  (<span>CRF Classifier</span>)  <br/>
           &nbsp; &nbsp;  </li>
        <li> Training  Corpus : <span>switchable</span> between different language and various domains  <br/>
            &nbsp; &nbsp; 
        </li> 
        <li>Entity Relation Identification: <span>Rule-based </span>engine, flexible and <span>extensible</span>.</li>       
      </ul>
 </div>
 
  
   <div align="center" class="zi" > <hr>copyright&nbsp;&copy;&nbsp; whuims.yuanju.page@gmail.com</div>

   

</div>
</body>
</html>