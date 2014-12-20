var netSwf;

var netComputeApplet;

window.onload = function(){
	getSwfID();
}

function addComputeApplet()
{
	var appletText = '<object alt="Net Graph Computation" id="netComputeAppletIE" classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" codebase="http://java.sun.com/update/1.4.2/jinstall-1_4-windows-i586.cab#Version=1,4,0,0" WIDTH="100%" HEIGHT="100%" MAYSCRIPT>' +
			'<PARAM NAME=CODE VALUE="com.ibm.socialgraph.NetComputeApplet.class" >' +
			'<PARAM NAME=ARCHIVE VALUE="assets/NetComputeApplet.jar" >' +
			'<PARAM NAME="TYPE" VALUE="application/x-java-applet;version=1.4">' +
			'<PARAM NAME="scriptable" VALUE="false">' +
			'<COMMENT>' +
				'<embed alt="Net Graph Computation" id="netComputeAppletFF" type="application/x-java-applet;version=1.4" CODE="com.ibm.socialgraph.NetComputeApplet.class" ARCHIVE="assets/NetComputeApplet.jar" MAYSCRIPT="true" scriptable="false" pluginspage="http://java.sun.com/products/plugin/index.html#download" WIDTH="100%" HEIGHT="100%">' +
	    			'<noembed>Net Graph Computation requires JRE, please install JRE first.</noembed>' +
	    		'</embed>' +
			'</COMMENT>' +
		'</object>';
	var newDiv = document.createElement("div");
	newDiv.innerHTML = appletText;
	document.body.insertBefore(newDiv, document.getElementById("appletContainer"));
	getComputeAppletID();
}

function getSwfID()
{	
	netSwf = document.getElementById("NetGraph");
	if (netSwf == null) {
		alert('Please ensure Shockwave Flash Object installed and enabled in your browser!');
	}
}

function getComputeAppletID()
{	
	if(navigator.userAgent.indexOf("Firefox")!=-1){
		netComputeApplet = document.getElementById("netComputeAppletFF");
	} else if (navigator.appName.indexOf("Microsoft") != -1) {
		netComputeApplet = document.getElementById("netComputeAppletIE");	
		if (netComputeApplet == null) {
			setTimeout("getComputeAppletID();",1000);
		}
	} else {
		netComputeApplet = document.getElementById("netComputeAppletFF");
	}
}

// data: Flash -> Applet
function sendAppletData(data) {
	// call Applet function
	if (netComputeApplet) {
		if (data) {
			try {
				netComputeApplet.loadData(data, navigator.userAgent, "Firefox/2");
			} catch (ex) {
				netSwf.alertAppletErr(100); // detect no Java plug-in installed
			}
		}
	} else {
		netSwf.alertAppletErr(100);
	}
}

// data: Applet -> Flash
function sendFlashData(data) {
	// call Flash function
	try {
		var tmpData = data;
		tmpData += ""; //important! Fix ff2 bug
		netSwf.sendFlashData(tmpData);
	} catch (ex) {
		alert("Line " + ex.lineNumber + ":" + ex);
		netSwf.alertAppletErr(100,true);
	}
}