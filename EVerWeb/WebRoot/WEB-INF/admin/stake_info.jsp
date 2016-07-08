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
	src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script>
</head>
<body>
	<!-- 顶部导航条 -->
	<jsp:include page="top.jsp" flush="false" />
	<!-- header end-->
	<div class="main">
		<div class="w main">
			<div class="right">
				<div id="buy-area" class="m m5" data-widget="tabs">
					<div class="mc">
						<!--百度地图容器-->
						<div style="width:1030px;height:600px;border:#ccc solid 1px;"
							id="dituContent"></div>
					</div>

				</div>
				<div id="userinfo" class="m m3">
					<div class="user">
						<div class="u-icon picture">
							<img id="userimg" alt="用户头像" src="img/info/caruser_header.png" />
						</div>
					</div>
					<!--user-->
					<div id="i-userinfo">
						<div class="username">
							<STRONG>车桩主基本信息：<s:property value="carUser.username" />
							</STRONG>
							<div id="jdtask" class="extra"></div>
						</div>
						<!--end-->

						<div id="remind">
							<div class="oinfo">
								<dl class="fore">
									<dt>姓名：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="stake.user.name" /> </A> </span>
									</dd>
									<dt>电话：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="stake.user.telephone" /> </A> </span>
									</dd>
								</dl>
							</div>
							<div class="ainfo">
								<dl class="fore">
									<dt>诚信值：</dt>
									<dd>
										<a class="flk-03" style="margin-left:8px;color:blue;"> <s:property
												value="stake.user.faith" /> </a>
									</dd>
								</dl>
							</div>
						</div>

						<div id="remind">
							<div class="oinfo">
								<dl class="fore">
									<dt>拥有车桩：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"> <s:property
													value="stake.user.stakes.size()" />个</A> </span>
									</dd>
								</dl>
							</div>
						</div>
					</div>
				</div>

				<!--userinfo-->
				<!--reco-area-->
				<div class="clr"></div>
				<!--con-area-->
			</div>
			<!--right-->
			<!-- left -->
			<jsp:include page="left.jsp" flush="false" />
			<!-- left end -->
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
        var p1 = <s:property value="initLongitude"/>;
        var p2 = <s:property value="initlatitude"/>;
        var point = new BMap.Point(p1,p2);//定义一个中心点坐标
        map.centerAndZoom(point,17);//设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map;//将map变量存储在全局
    }
    
    //地图事件设置函数：
    function setMapEvent(){
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
    var markerArr = [{title:"新车桩分享",id:"<s:property value="stake.id"/>",content:"<s:property value="stake.description"/>",point:"<s:property value="stake.longitude"/>|<s:property value="stake.latitude"/>",
    holderName:"<s:property value="stake.user.name"/>",holderphone:"<s:property value="stake.user.telephone"/>",holderFaith:"<s:property value="stake.user.faith"/>",
    stakeId:"<s:property value="stake.id"/>",isOpen:0,icon:{w:23,h:25,l:46,t:21,x:9,lb:12}}];
   
    //创建marker
    function addMarker(){
        for(var i=0;i<markerArr.length;i++){
            var json = markerArr[i];
            var p0 = json.point.split("|")[0];
            var p1 = json.point.split("|")[1];
            var point = new BMap.Point(p0,p1);
			var iconImg = createIcon(json.icon);
            var marker = new BMap.Marker(point,{icon:iconImg});
			var iw = createInfoWindow(i);
			var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
			marker.setLabel(label);
            map.addOverlay(marker);
            label.setStyle({
                        borderColor:"#808080",
                        color:"#333",
                        cursor:"pointer"
            });
			
			(function(){
				var index = i;
				var _iw = createInfoWindow(i);
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
        }
    }
    //创建InfoWindow
    function createInfoWindow(i){
        var json = markerArr[i];
        var title= {title : '<span style="font-size:14px;color:#0A8021">'+json.title+'</span>'};
        var iw = new BMap.InfoWindow("<div style='line-height:1.8em;font-size:12px;'><b>描述:</b>"+json.content+"</br><b>车桩主姓名:</b>"+json.holderName
        +"</br><b>电话:</b>"+json.holderphone+"</br><b>诚信值:</b>"+json.holderFaith+"</br>"
        +"</br><b><a href='StakeAction_check?currentId="+json.id+"&checkok=0'"+" class='button button-action button-rounded  button-tiny' style='float:left;margin-left:15px;'>通过</a>"
        +"</span> <a href='StakeAction_check?currentId="+json.id+"&checkok=1'"+" class='button button-caution button-rounded button-tiny'  style='float:left;margin-left:35px;'>驳回</a>",title);
        return iw;
    }
    //创建一个Icon
    function createIcon(json){
        var icon = new BMap.Icon("img/info/us_mk_icon.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
        return icon;
    }
    
    
    initMap();//创建和初始化地图
</script>
</html>

