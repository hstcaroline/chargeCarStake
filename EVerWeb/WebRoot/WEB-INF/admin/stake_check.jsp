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
<script type="text/javascript" language="javascript">
	function fsubmit() {
		var form = document.getElementsByName("stakeForm")[0];
		form.submit();
	}
</script>
</head>
<body>
	<!-- 顶部导航条 -->
	<jsp:include page="top.jsp" flush="false" />
	<!-- header end-->
	<div class="main">
		<div class="w main">
			<form name="stakeForm" id="stakeForm"
				action="#" method="post">
				<!--right-->
				<div class="right">
					<div class="o-mt">
						<H2>待审核车桩信息</H2>
					</div>
					<div id="buy-area" class="m m5" data-widget="tabs">
						<div class="mc">
							<div class="tb-void">
								<TABLE border="0" cellSpacing="0" cellPadding="0" width="100%">
									<TBODY>
										<tr>
											
											<TH width="100">车桩主姓名</TH>
											<TH width="100">车桩主电话</TH>
											<TH width="100">开放起始时间段</TH>
											<TH width="100">开放结束时间段</TH>
											<TH width="150">描述</TH>
											<TH width="100">可充车型</TH>
											<TH width="100">车桩类型</TH>
											<TH width="100">状态</TH>
											<TH width="180">操作</TH>
										</tr>
									</TBODY>

									<TBODY id="tb" class="parent">
									<s:if test="stakes.size()>0"> 
										<s:iterator value="stakes" id="stake"> 
											<tr id="track">
												<td hidden="hidden">  
													<input type="text" id="currentId" name="currentId" 
													value= "<s:property value="#stake.id"/>"/>
												</td>
												<td><div class="name"><s:property value="#stake.user.name" /></div>
												</td>
												<td><div class="phone"><s:property value="#stake.user.telephone" /></div>
												</td>
												<td><div class="stime"><s:property value="#stake.availableStime" /></div>
												</td>
												<td><div class="etime"><s:property value="#stake.availableEtime" /></div>
												</td>
												<td><div class="description"><s:property value="#stake.description" /></div>
												</td>
												<td><div class="available_cartype_id"><s:property value="#stake.carType.typeName" /></div>
												</td>
												<td>
													<div class="type">
													<s:if test="#stake.type==0">公有桩</s:if>
													<s:if test="#stake.type==1">私有桩</s:if>
													</div>
												</td>
												<td>
													<div class="status">
													<s:if test="#stake.status==0">可用 </s:if>
													<s:if test="#stake.status==1">正在使用 </s:if>
													<s:if test="#stake.status==2">未分享</s:if>
													<s:if test="#stake.status==3">异常</s:if>
													<s:if test="#stake.status==4">待审核</s:if>
													<s:if test="#stake.status==5">审核未通过</s:if>
													</div>
												</td>
												<td> 
												<a href="StakeAction_loadCheck?currentId=<s:property value="#stake.id"/>" 
													class="button button-action button-rounded  button-tiny" onclick="fsubmit()">审核</a>
												<a href="StakeAction_deleteUncheckedStake?currentId=<s:property value="#stake.id"/>" 
												class="button button-caution button-rounded button-tiny" onclick="return confirm('确定要删除吗？');">删除</a>
												</td>
											</tr>
										</s:iterator>
										</s:if>	
										<s:if test="stakes.size()==0">
										<tr id="track">
										<td  colspan=11 align="center">没有待审核车桩</td>
										</tr>
										</s:if>
									</TBODY>
								</TABLE>
								<br />
								<div style="text-align:center">
									<jsp:include page="/inc/pager.jsp">
										<jsp:param value="StakeAction_showUncheckedStake.action"
											name="url" />
										<jsp:param value="${pagerStake.totalRecord }" name="items" />
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

