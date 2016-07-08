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
				<div id="userinfo" class="m m3">
					<div class="user">
						<div class="u-icon picture">
							<img id="userimg" alt="用户头像" src="img/info/admin_header.png" />
						</div>
					</div>
					<!--user-->
					<div id="i-userinfo">
						<div class="username">
							<STRONG><s:property value="#session.loginUser" />管理员</STRONG>,欢迎您！
							<div id="jdtask" class="extra"></div>
						</div>
						<div class="account">
							<div class="member">
								<div class="rank r3">
									<a style="color:blue;">管理员</a>
								</div>
								<div style="DISPLAY: block" class="memb-info">
									<a>用户权限：管理权限</a>
								</div>
							</div>
						</div>
						<!--end-->

						<div id="remind">
							<div class="oinfo">
								<dl class="fore">
									<dt style="margin-right:27px;color:blue;">用户</dt>
									<dt>当前总数：</dt>
									<dd>
										<span> <a href="ShowPersonalInfo_showAllCarUser"
											class="flk-03" style="margin-left:8px;color:blue;"><s:property
													value="adminInfoDto.userCount" /> </A> </span>
									</dd>
								</dl>
							</div>
							<div class="ainfo">
								<dl class="fore">
									<dt>当前正在使用车桩：</dt>
									<dd>
										<a class="flk-03" style="margin-left:8px;color:blue;"> <s:property
												value="adminInfoDto.stakeUsingCount" /> </a>
									</dd>
								</dl>
							</div>
						</div>

						<div id="remind">
							<div class="oinfo">
								<dl class="fore">
									<dt style="margin-right:27px;color:blue">车桩</dt>
									<dt>当前总数：</dt>
									<dd>
										<span> <a href="StakeAction_showAllStake"
											class="flk-03" style="margin-left:8px;color:blue;"> <s:property
													value="adminInfoDto.stakeCount" /> </A> </span>
									</dd>
									<dt>当前在用：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"> <s:property
													value="adminInfoDto.stakeUsingCount" /> </A> </span>
									</dd>
								</dl>
							</div>
							<div class="ainfo">
								<dl class="fore">
									<dt>当前异常：</dt>
									<dd>
										<a class="flk-03" style="margin-left:8px;color:blue;"> <s:property
												value="adminInfoDto.stakeExcepCount" />
										</A>
									</dd>
								</dl>
							</div>
						</div>

						<div id="remind">
							<div class="oinfo">
								<dl class="fore">
									<dt style="margin-right:27px;color:blue">消息</dt>
									<dt>未处理投诉：</dt>
									<dd>
										<span> 
											<a href="ComplaintAction_showUnsolvedCom" class="flk-03" style="margin-left:1px;color:blue;">
												<s:property value="adminInfoDto.unsolvedCompMsgCount"/>
											</a>
										</span>
									</dd>
									<dt>未处理车桩审核：</dt>
									<dd>
										<span> <a class="flk-03" href="StakeAction_showUncheckedStake"
											style="margin-left:1px;color:blue;"> <s:property
													value="adminInfoDto.unsolvedCheckMsgCount"/>
										</a> </span>
									</dd>
								</dl>
							</div>
							<div class="ainfo">
								<dl class="fore">
									<dt>已处理消息：</dt>
									<dd>
									
										<a class="flk-03" style="margin-left:8px;color:blue;"> 
										<s:property
												value="adminInfoDto.solvedMsgCount" />
										</A>
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
</html>

