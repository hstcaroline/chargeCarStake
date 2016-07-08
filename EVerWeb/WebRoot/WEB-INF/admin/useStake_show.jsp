<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
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
</head>
<body>
	<!-- 顶部导航条 -->
	<jsp:include page="top.jsp" flush="false" />
	<!-- header end-->
	<div class="main">
		<div class="w main">
			<form name="userForm" id="userForm"
				onsubmit="javascript:return checkValue();"
				action="/share2/admin/updatebike" method="post">
				<!--right-->
				<div class="right">
					<div class="o-mt">
						<H2>车桩使用记录</H2>
					</div>
					<div id="buy-area" class="m m5" data-widget="tabs">
						<div class="mc">
							<div class="tb-void">
								<TABLE border="0" cellSpacing="0" cellPadding="0" width="100%">
									<TBODY>
										<TR>
											<TH width="80">车主姓名</TH>
											<TH width="100">车主电话</TH>
											<TH width="80">车桩主姓名</TH>
											<TH width="100">车桩主电话</TH>
											<TH width="100">开始时间</TH>
											<TH width="100">结束时间</TH>
											<TH width="80">状态</TH>
											<TH width="180">操作</TH>
										</TR>
									</TBODY>

									<TBODY id="tb" class="parent">
										<s:iterator value="useStakes" id="useStake">
											<TR id="track">
												<td><div class="name">
														<s:property value="#useStake.userByUserId.name" />
													</div></td>
												<td><div class="phone">
														<s:property value="#useStake.userByUserId.telephone" />
													</div></td>
												<td><div class="stake_owner_name">
														<s:property value="#useStake.stake.user.name" />
													</div></td>
												<td><div class="stake_owner_telephone">
														<s:property value="#useStake.stake.user.telephone" />
													</div></td>
												<td><div class="stime">
												<s:date name="#useStake.startTime" format="yyyy-MM-dd HH:mm:ss" />
														<!-- <s:property value="#useStake.startTime" /> -->
													</div></td>
												<td><div class="etime">
														<!-- <s:property value="#useStake.endTime" /> -->
														<s:date name="#useStake.endTime" format="yyyy-MM-dd HH:mm:ss" />
													</div></td>
												<td>
													<div class="status">
														<s:if test="#useStake.status==0">正在进行 </s:if>
														<s:if test="#useStake.status==1">已完成 </s:if>
														<s:if test="#useStake.status==2">异常 </s:if>
													</div></td>
												<td><a href="UseStakeAction_loadUpdate?currUseStakeId=<s:property value="#useStake.id"/>"
													class="button button-action button-rounded  button-tiny">
														修改</a> 
													<a href="UseStakeAction_deleteUseStake?currUseStakeId=<s:property value="#useStake.id"/>"
													class="button button-caution button-rounded button-tiny" onclick="return confirm('确定要删除吗？');">
														删除</a></td>
											</TR>
										</s:iterator>
									</TBODY>
								</TABLE>
								<br/>
								<div style="text-align:center">
									<jsp:include page="/inc/pager.jsp">
										<jsp:param value="UseStakeAction_showAllUseStake.action"
											name="url" />
										<jsp:param value="${pageUseStake.totalRecord }" name="items" />
									</jsp:include>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<!--right-->
			<!-- left -->
			<jsp:include page="left.jsp" flush="false" />
			<!-- left end -->
		</div>
	</div>
</body>
</html>

