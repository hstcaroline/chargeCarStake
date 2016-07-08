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
</head>
<body>
	<!-- 顶部导航条 -->
	<jsp:include page="top.jsp" flush="false" />
	<!-- header end-->
	<div class="main">
		<div class="w main">
			<div class="right">
				<div class="o-mt">
					<H2>处罚后的用户信息</H2>
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
							<STRONG>当前用户：<s:property value="carUser.username" /> </STRONG>
							<div id="jdtask" class="extra"></div>
						</div>
						<!--end-->

						<div id="remind">
							<div class="oinfo">
								<dl class="fore">
									<dt>用户姓名：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="carUser.name" /> </A> </span>
									</dd>
									<dt>用户电话：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="carUser.telephone" /> </A> </span>
									</dd>
								</dl>
							</div>
							<div class="ainfo">
								<dl class="fore">
									<dt>当前余额：</dt>
									<dd>
										<a class="flk-03" style="margin-left:8px;color:blue;"> <s:property
												value="carUser.remaining" />元 </a>
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
													value="carUser.stakes.size()" />个</A> </span>
									</dd>
									<dt>充电记录：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="carUser.useStakesForUserId.size()" />次 </A> </span>
									</dd>
								</dl>
							</div>
							<div class="ainfo">
								<dl class="fore">
									<dt>预约订单数：</dt>
									<dd>
										<a class="flk-03" style="margin-left:8px;color:blue;"><s:property
												value="carUser.orders.size()" />个</A>
									</dd>
								</dl>
							</div>
						</div>

						<div id="remind">
							<div class="oinfo">
								<dl class="fore" style="width:600px">
									<dt>诚信值：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="carUser.faith" /> </A> </span>
									</dd>
									<dt>拥有车辆：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="carUser.cars.size()" />辆 </A> </span>
									</dd>
									<dt>车桩被使用：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="carUser.useStakesForStakeOwnerId.size()" />次 </A> </span>
									</dd>
								</dl>
							</div>
						</div>
					</div>
				</div>
				<div  align="right">
					<a href="ComplaintAction_showUnsolvedCom"
						class="button button-glow button-rounded button-raised button-primary"><s></s>返回继续处理&gt;&gt;</a>
				</div>
			</div>
			<!--right-->
			<!-- left -->
			<jsp:include page="left.jsp" flush="false" />
			<!-- left end -->
		</div>
	</div>
</body>
</html>

