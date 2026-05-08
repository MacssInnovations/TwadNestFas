<!DOCTYPE html>

<html>
<head>

<style>
.opaqueLayer
{
	display:none;
	position:absolute;
	top:0px;
	left:0px;
	opacity:0.6;
	filter:alpha(opacity=60);
	background-color: #000000;
	z-Index:1000;
}
 
.showPopupLayer
{
	position:absolute;
	top:0px;
	left:0px;
	width:20px;
	height:20px;
	display:none;
	z-Index:1001;
	border:1px solid black;
	background-color:#FFFFFF;
	text-align:center;
	vertical-align:middle;
	padding:10px;
}
</style>

<script type="text/javascript">
function getBrowserHeight() {
	var intH = 0;
	var intW = 0;
	 
	if(typeof window.innerWidth == 'number' ) {
		intH = window.innerHeight;
		intW = window.innerWidth;
	}
	else if(document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
		intH = document.documentElement.clientHeight;
		intW = document.documentElement.clientWidth;
	}
	else if(document.body && (document.body.clientWidth || document.body.clientHeight)) {
		intH = document.body.clientHeight;
		intW = document.body.clientWidth;
	}
	return { width: parseInt(intW), height: parseInt(intH) };
}
 
function setLayerPosition() {
	var shadow = document.getElementById('shadow');
	var question = document.getElementById('showPopup');
	 
	var bws = getBrowserHeight();
	shadow.style.width = bws.width + 'px';
	shadow.style.height = bws.height + 'px';
	question.style.left = parseInt((bws.width - 20) / 2)+ 'px';
	question.style.top = parseInt((bws.height - 20) / 2)+ 'px';
	shadow = null;
	question = null;
}
 
function showLayer() {
	setLayerPosition();
	var shadow = document.getElementById('shadow');
	var question = document.getElementById('showPopup');
	 
	shadow.style.display = 'block';
	question.style.display = 'block';
	 
	shadow = null;
	question = null;
}

function hideLayer() {
	var shadow = document.getElementById('shadow');
	var question = document.getElementById('showPopup');
	 
	shadow.style.display = 'none';
	question.style.display = 'none';
	 
	shadow = null;
	question = null;
}
 
window.onresize = setLayerPosition;
</script>
	
</head>
<body>
<div id="shadow" class="opaqueLayer"></div>
<div id="showPopup" class="showPopupLayer">
	<!--<br /><br /><br /><strong>Request send to Server</strong>
	<br /><br /><strong>While wait for server response...</strong>
	<br /><br />-->
	
	<img src="../../../../../images/Loading.gif">
</div>

</body>
</html>