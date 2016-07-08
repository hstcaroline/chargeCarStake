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
								<dl class="fore">
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
								</dl>
							</div>
							<div class="ainfo">
								<dl class="fore">
									<dt>使用车桩（或者车桩被使用）：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="useStakes.size()" />次 </A> </span>
									</dd>
								</dl>
							</div>
						</div>
					</div>
				</div>
				<div class="o-mt">
					<H2>充电记录</H2>
				</div>
				<div id="buy-area" class="m m5" data-widget="tabs">
					<!-- <DIV class="mt">
						<DIV class="extra">
							<DIV class="search">
								<form id="searchForm" name="searchForm"
									action="ShowPersonalInfo_searchByName" method="post">
									<input
										onblur="if (this.value=='') this.value=this.defaultValue"
										onkeydown="javascript:if(event.keyCode==13) OrderSearch('ip_keyword');"
										onfocus="if (this.value==this.defaultValue)this.value=''"
										id="ip_keyword" type="text" class="itxt" value="车桩主姓名"
										name="searchName" style="color: rgb(204, 204, 204);">
									<a href="javascript:fsubmit(document.searchForm);"
										class="button button-tiny" onclick="check1();"><s></s>查&nbsp;询</a>
								</form>
							</DIV>
						</DIV>
					</DIV> -->

					<div class="mc">
						<form name="userForm" id="userForm"
							action="ShowPersonalInfo_showCurrentUser" method="post">
							<div class="tb-void">
								<TABLE border="0" cellSpacing="0" cellPadding="0" width="100%">
									<TBODY>
										<TR>
											<TH width="50">使用者编号</TH>
											<TH width="60">使用者姓名</TH>
											<TH width="70">车桩主编号</TH>
											<TH width="80">车桩主姓名</TH>
											<TH width="100">开始时间</TH>
											<TH width="100">结束时间</TH>
											<TH width="100">状态</TH>
											<TH width="100">操作</TH>
										</TR>
									</TBODY>

									<TBODY id="tb" class="parent">
										<s:if test="useStakes.size()>0">
											<s:iterator value="useStakes" id="useStake">
												<TR id="track">
													<td><div class="user_id">
															<s:property value="#useStake.userByUserId.id" />
														</div>
													</td>
													<td><div class="user_name">
															<s:property value="#useStake.userByUserId.name" />
														</div>
													</td>
													<td><div class="stake_holder_id">
															<s:property value="#useStake.userByStakeOwnerId.id" />
														</div>
													</td>
													<td><div class="stake_holder_name">
															<s:property value="#useStake.userByStakeOwnerId.name" />
														</div>
													</td>
													<td><div class="start_time">
															<s:property value="#useStake.startTime" />
														</div>
													</td>
													<td><div class="end_time">
															<s:property value="#useStake.endTime" />
														</div>
													</td>
													<td><div class="status">
															<s:if test="#useStake.status==0">正在进行 </s:if>
															<s:if test="#useStake.status==1">已完成 </s:if>
															<s:if test="#useStake.status==2">异常 </s:if>
														</div>
													</td>
													<td><a href="ShowPersonalInfo_loadUpdate"
														class="button button-action button-rounded  button-tiny">
															修改</a> <a href="UseStakeAction_deleteUseStake?currUseStakeId=<s:property value="#useStake.id"/>"
														class="button button-caution button-rounded button-tiny" onclick="return confirm('确定要删除吗？');">
															删除</a>
													</td>
												</TR>
											</s:iterator>
										</s:if>
										<s:if test="useStakes.size()==0">
											<tr id="track">
												<td colspan=8 align="center">没有充电记录</td>
											</tr>
										</s:if>
									</TBODY>
								</TABLE>
								<br />
								<div style="text-align:center">
									<jsp:include page="/inc/pager.jsp">
										<jsp:param value="ShowPersonalInfo_showCurrentCarUser.action"
											name="url" />
										<jsp:param value="${pageOrders.totalRecord}" name="items" />
									</jsp:include>
								</div>
							</div>
						</form>
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

