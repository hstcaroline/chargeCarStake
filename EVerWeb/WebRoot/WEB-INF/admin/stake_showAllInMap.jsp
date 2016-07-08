<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//dtD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/dtD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理中心</title>
<link rel="stylesheet" type="text/css" href="css/top-search.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/info.css">
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<!--引用百度地图API-->
<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
}

.iw_poi_title {
	color: #CC5522;
	font-size: 14px;
	font-weight: bold;
	overflow: hidden;
	padding-right: 13px;
	white-space: nowrap
}

.iw_poi_content {
	font: 12px arial, sans-serif;
	overflow: visible;
	padding-top: 4px;
	white-space: -moz-pre-wrap;
	word-wrap: break-word
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?key=&v=1.1&services=true">
</script>
<script language="JavaScript"> 
function selSubmit(sobj)
	{
		var docurl = "StakeAction_showStakeInMap?status="+document.getElementById("status").value;
		if (docurl != "") {
                       open(docurl,'_self');
                       sobj.selectedIndex=0;
                       sobj.blur();
                    }
	}
</script> 
</head>
<body>
	<!-- 顶部导航条 -->
		<div class="top-search">
		<div id="shortcut">
			<div class="w">
				<ul class="fr">
					<li id="ttbar-login" class="fore1"><a class="link-login"
						href="#"><s:property value="#session.loginUser" />,你好</a></li>
					<li class="spacer"></li>
					<li class="fore2">
						<div class="dt">
							<a class="link-exit style-red" href="LogoutAction">安全退出</a>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<div class="w">
			<div class="ld">
				<a href="javascript:void(0)"><b></b> <img alt="Ever充电桩共享系统"
					src="img/login/logo.png" width="200" height="60" /> </a>
			</div>
		</div>
	</div>
	<div class="w">
		<div id="nav">
			<div id="categorys">
				<div>
					<button type="button" class="button button-primary"
						onclick="location='LoginAction_load'">首页</button>
				</div>
			</div>
			<ul id="navitems">
				<button type="button" class="button button-primary"
					onclick="location='StakeAction_showStakeInMap?status=-1'">充电桩状况</button>
			</ul>
		</div>
	</div>
	<div>
		<div class="w">
				<div class="breadcrumb">
					<STRONG><a href="#">车桩状态：</A> </STRONG> <select name="status" id="status"
						class="mapsel" onchange=selSubmit(this)>
						<option value="-1"
							<s:if test="status==-1">selected = "selected"</s:if>>全部车桩</option>
						<option value="0"
							<s:if test="status==0">selected = "selected"</s:if>>可用</option>
						<option value="1"
							<s:if test="status==1">selected = "selected"</s:if>>正在使用</option>
						<option value="2"
							<s:if test="status==2">selected = "selected"</s:if>>未分享</option>
						<option value="3"
							<s:if test="status==3">selected = "selected"</s:if>>异常</option>
						<option value="4"
							<s:if test="status==4">selected = "selected"</s:if>>待审核</option>
						<option value="5"
							<s:if test="status==5">selected = "selected"</s:if>>审核未通过</option>
					</select>
				</div>
			
		</div>
	</div>
	<!-- header end-->
	<div class="main">
		<div class="w main">
			<!--百度地图容器-->
			<div style="width:1210px;height:700px;border:#ccc solid 1px;"
				id="dituContent"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
    //创建和初始化地图函数：
    function initMap(){
        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
        addMarker();//向地图中添加marker
    }
    
     //创建地图函数：
    function createMap(){
        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var p1 = <s:property value="initLongitude" />;
        var p2 = <s:property value="initlatitude" />;
        var point = new BMap.Point(p1,p2);//定义一个中心点坐标
        map.centerAndZoom(point,17);//设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map;//将map变量存储在全局
    }
    
    //地图事件设置函数：
    function setMapEvent(){
       // map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }
    
    //地图控件添加函数：
    function addMapControl(){
        //向地图中添加缩放控件
	var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
	map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
	map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
	map.addControl(ctrl_sca);
    }
    
    //标注点数组
    /* var markerArr = [{title:"新车桩分享",content:"<s:property value="stake.description"/>",point:"<s:property value="stake.longitude"/>|<s:property value="stake.latitude"/>",
    holderName:"<s:property value="stake.user.name"/>",holderphone:"<s:property value="stake.user.telephone"/>",holderFaith:"<s:property value="stake.user.faith"/>",
    stakeId:"<s:property value="stake.id"/>",isOpen:0,icon:{w:23,h:25,l:46,t:21,x:9,lb:12}}]; */
    var json={isOpen:0,icon:{w:23,h:25,l:46,t:21,x:9,lb:12}};
    //创建marker
    function addMarker(){
    	var i = 0;
        <s:iterator value="stakes" id="sta">  
        	var value=<s:property value="#sta.id" />  
        	var p0 = <s:property value="#sta.longitude" />;
        	var p1 = <s:property value="#sta.latitude"/>;
        	var point = new BMap.Point(p0,p1);
        	var iconImg = createIcon(json.icon);
        	var marker = new BMap.Marker(point,{icon:iconImg});
        	var title = {title : '<span style="font-size:14px;color:#0A8021">'+'车桩'+'</span>'};
        	var iw = new BMap.InfoWindow("<b>车桩主姓名:</b>"+'<s:property value="#sta.user.name"/>'+"</br><b>电话:</b>"+'<s:property value="#sta.user.telephone"/>'
        	+"</br><b>诚信值:</b>"+'<s:property value="#sta.user.faith"/>'+"</br>"+"<div style='line-height:1.8em;font-size:12px;'><b>车桩描述:</b>"+'<s:property value="#sta.description" />'+"</br>"
        	+"</br><b><a class='button button-action button-rounded  button-tiny' style='float:left;margin-left:15px;' href='StakeAction_loadUpdate?currentId=<s:property value="#sta.id"/>'>修改</a>"
        	+"</span><a href='StakeAction_deleteStakeInMap?currentId=<s:property value="#sta.id"/>' class='button button-caution button-rounded button-tiny'  style='float:left;margin-left:35px;' >删除</a>",title);
        	var label = new BMap.Label("车桩",{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
        	marker.setLabel(label);
        	map.addOverlay(marker);
        	label.setStyle({
                       borderColor:"#808080",
                       color:"#333",
                       cursor:"pointer"
                       });
           (function(){
				//var index = i;
				var _iw = iw;
				var _marker = marker;
				_marker.addEventListener("click",function(){
				    this.openInfoWindow(_iw);
			    });
			    _iw.addEventListener("open",function(){
				    _marker.getLabel().hide();
			    })
			    _iw.addEventListener("close",function(){
				    _marker.getLabel().show();
			    })
				label.addEventListener("click",function(){
				    _marker.openInfoWindow(_iw);
			    })
				if(!!json.isOpen){
					label.hide();
					_marker.openInfoWindow(_iw);
				}
		})()
   		i++;
    </s:iterator>  
    }
    //创建一个Icon
    function createIcon(json){
        var icon = new BMap.Icon("img/info/us_mk_icon.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
        return icon;
    }
    
    initMap();//创建和初始化地图
</script>
</html>

