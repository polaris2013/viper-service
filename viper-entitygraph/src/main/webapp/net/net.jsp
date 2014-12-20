
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import = "com.baidu.inf.viper.entitygraph.EntityGraphFacade,com.baidu.inf.viper.entitygraph.bean.Word,java.util.List" %>
<style type="text/css">


body {
	font: 14px "宋体";
	text-align: center;
}
#entlist{
   text-align: left;
   color: #007979;
   margin:20px auto;
   font: 16px "楷体";
   font-weight: bold;
   width:60%;
}
.personEnt{
  color:#ffffff;
  background:#990000;
}
.placeEnt{
  color:#ffffff;
  background:#660066;
}
.orgEnt{
  color:#ffffff;
  background:#cc0066;
}
#logslist{
   font: 16px "Trebuchet MS";
   margin:0 auto;
   text-align: center;
   color: #003E3E;
   font-style: italic;
   width:60%;
}
</style>

<%
int mode;
String modeStr=request.getParameter("loadmode");
if(modeStr==null){
	mode=0;
}else{
	mode=Integer.parseInt(modeStr);
}


int langOp;
String langOpStr=request.getParameter("lang");
if(langOpStr==null){
	langOp=0;
}else{
	langOp=Integer.parseInt(langOpStr);
}


String content="";
if(request.getParameter("queryText")==null) {
	 content="";
}else{
    content=new String(request.getParameter("queryText").getBytes("ISO8859-1"), "gb2312"); 
}
   
   EntityGraphFacade facade=new EntityGraphFacade(content,mode,langOp);
   out.println("<div id='logslist'>");
   
  
   long t0=System.currentTimeMillis();
   String xmlGraph=facade.runBuild();
   long t1=System.currentTimeMillis();
   out.println("build successfully!\t cost "+ (t1-t0)/1000.0+" s<br/>");
   
  
   List<Word> personList=facade.getPersonList();
   List<Word> placeList=facade.getPlaceList();
   List<Word> orgList=facade.getOrgList();
   
   
   out.println("start to build network...<br/>");
   out.println("</div>");
   if(session.getAttribute("xmlGraph")!=null){
	   session.removeAttribute("xmlGraph");
	   System.out.println("cache still exists!!!!!");
   }
   session.setAttribute("xmlGraph",xmlGraph);
   
  
%>
<title>Entity Graph View</title>

<script src="Scripts/swfobject_modified.js" type="text/javascript"></script>
<script type="text/javascript">
    var a="assets/data/data.jsp";
    function getIDAS() {
        return a;
    }
</script>

</head>

<body>
<div id="entlist"><% 
   if(personList.size()+orgList.size()+placeList.size()<1){
	   out.println("There's no Named Entity or the current trained Classifier fail to extract NEs!<br>");
   }else{
	   out.println("Person Entity[人名实体]:");
	   for(Word wt:personList){
		   out.println("<span class=\"personEnt\">"+wt.getWordStr()+"</span>, ");
	   }
	   out.println("<br/> Organization Entity[机构实体]：");
	   for(Word wt: orgList){
		   out.println("<span class=\"orgEnt\">"+wt.getWordStr()+"</span>, ");
	   }
	   out.println("<br/> Place Entity[地名实体]：");
	   for(Word wt: placeList){
		   out.println("<span class=\"placeEnt\">"+wt.getWordStr()+"</span>, ");
	   }
	   out.println("<br/>");
   }
   %></div>
<object id="netFlash" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="730" height="700">
  <param name="movie" value="NetGraph.swf" />
  <param name="quality" value="high" />
  <param name="wmode" value="opaque" />
  <param name="swfversion" value="6.0.65.0" />
  <!-- 此 param 标签提示使用 Flash Player 6.0 r65 和更高版本的用户下载最新版本的 Flash Player。如果您不想让用户看到该提示，请将其删除。 -->
  <param name="expressinstall" value="Scripts/expressInstall.swf" />
  <!-- 下一个对象标签用于非 IE 浏览器。所以使用 IECC 将其从 IE 隐藏。 -->
  <!--[if !IE]>-->
  <object type="application/x-shockwave-flash" data="NetGraph.swf" width="710" height="646">
    <!--<![endif]-->
    <param name="quality" value="high" />
    <param name="wmode" value="opaque" />
    <param name="swfversion" value="6.0.65.0" />
    <param name="expressinstall" value="Scripts/expressInstall.swf" />
    <!-- 浏览器将以下替代内容显示给使用 Flash Player 6.0 和更低版本的用户。 -->
    <div>
      <h4>此页面上的内容需要较新版本的 Adobe Flash Player。</h4>
      <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="获取 Adobe Flash Player" width="112" height="33" /></a></p>
    </div>
    <!--[if !IE]>-->
  </object>
  <!--<![endif]-->
</object>
<script type="text/javascript">
     swfobject.registerObject("netFlash");
</script>
</body>
</html>
