<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//dtD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/dtD/xhtml1-transitional.dtd">
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
						<H2>投诉消息</H2>
					</div>
					<div id="buy-area" class="m m5" data-widget="tabs">
						<div class="mc">
							<div class="tb-void">
								<TABLE border="0" cellSpacing="0" cellPadding="0" width="100%">
									<TBODY>
										<tr>
											<TH width="100">编号</TH>
											<TH width="100">投诉方</TH>
											<TH width="100">被投诉方</TH>
											<TH width="100">时间</TH>
											<TH width="100">内容</TH>
											<TH width="100">投诉订单编号</TH>
											<TH width="140">操作</TH>
										</tr>
									</TBODY>

									<TBODY id="tb" class="parent">
									<s:if test="complaints.size()>0">
										<s:iterator value="complaints" id="complaint">
											<tr id="track">
												<td><div class="id">
														<s:property value="#complaint.id" />
													</div></td>
												<td><div class="from_id">
														<s:property value="#complaint.userByFromId.name" />
													</div></td>
												<td><div class="to_id">
														<s:property value="#complaint.userByToId.name" />
													</div></td>
												<td><div class="time">
														<s:date name="#complaint.time" format="yyyy-MM-dd"/>
													</div></td>
												<td><div class="content">
														<s:property value="#complaint.content" />
													</div>
												</td>
												<td><div class="useStake">
														<s:property value="#complaint.useStakeByUseStakeId.id" />
													</div>
												</td>
												<td><a href="ComplaintAction_loadHandler?currentCompId=<s:property value='#complaint.id'/>"
													class="button button-action
														button-rounded button-tiny">处理</a>
													<a href="ComplaintAction_ignore?currentCompId=<s:property value='#complaint.id'/>"
													class="button button-caution button-rounded button-tiny" onclick="return confirm('确定要忽略该投诉吗？');">
														忽略</a></td>
											</tr>
										</s:iterator>
										</s:if>
										<s:if test="complaints.size()==0">
										<tr id="track">
										<td  colspan=7 align="center">没有待处理投诉</td>
										</tr>
										</s:if>
									</TBODY>
								</TABLE>
								<br />
								<div style="text-align:center">
									<jsp:include page="/inc/pager.jsp">
										<jsp:param value="ComplaintAction_showUnsolvedCom"
											name="url" />
										<jsp:param value="${comPager.totalRecord }" name="items" />
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

