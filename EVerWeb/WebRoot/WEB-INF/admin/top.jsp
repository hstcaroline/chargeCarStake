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
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/buttons.css">
</head>

<body>
	<!-- header -->
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
	<!--header end-->
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
		<div class="breadcrumb">
			<StrONG><a href="javascript:void(0);">充电桩系统-管理中心</A> </StrONG>
		</div>
	</div>
</body>
</html>
