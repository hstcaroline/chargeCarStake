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
<script type="text/javascript" language="javascript">
	function fsubmit(obj) {
		obj.submit();
	}
	function deleteAlert(id) {
		return confirm("确定要删除吗？");
	}
	function txtFocus(el) {
		if (el.defaultValue == el.value) {
			el.value = '';
			el.style.color = '#000';
		}
	}
	function txtBlur(el) {
		if (el.value == '') {
			el.value = el.defaultValue;
			el.style.color = 'rgb(191, 191, 191)';
		}
	}
</script>
</head>
<body>
	<!-- 顶部导航条 -->
	<jsp:include page="top.jsp" flush="false" />
	<!-- header end-->
	<div class="main">
		<div class="w main">
			<!--right-->
			<div class="right">
				<div class="o-mt">
					<H2>用户信息</H2>
				</div>
				<div id="buy-area" class="m m5" data-widget="tabs">
					<div class="mt">
					<a style="color:red;margin-left:10px;padding-left:10px"><s:property value="erroMsg" /></a>
						<div class="extra">
							<div class="search">
								<form id="searchForm" name="searchForm" action="ShowPersonalInfo_searchByName" method="post">
									<input
										id="ip_keyword" type="text" class="itxt" value="车主姓名"
										name="searchName" style="color: rgb(191, 191, 191);margin-right:4px" onfocus="txtFocus(this)" onblur="txtBlur(this)" value="您的中文全名">
									<a href="javascript:fsubmit(document.searchForm);"
										class="button button-royal button-tiny" onclick="check1();"><s></s>查询</a>
								</form>
							</div>
						</div>
					</div>
					<div class="mc">
						<form name="userForm" id="userForm"
							action="ShowPersonalInfo_showCurrentUser" method="post">
							<div class="tb-void">
								<TABLE border="0" cellSpacing="0" cellPadding="0" width="100%">
									<TBODY>
										<TR>
											<TH width="100">用户名</TH>
											<TH width="100">姓名</TH>
											<TH width="100">电话</TH>
											<TH width="100">是否拥有车桩</TH>
											<TH width="100">诚信度</TH>
											<TH width="100">余额</TH>
											<TH width="100">操作</TH>
										</TR>
									</TBODY>

									<TBODY id="tb" class="parent">
										<s:iterator value="carUsers" id="carUser">
											<TR id="track">
												<td><div class="username">
														<s:property value="#carUser.username" />
													</div></td>
												<td><div class="name">
														<s:property value="#carUser.name" />
													</div></td>
												<td><div class="phone">
														<s:property value="#carUser.telephone" />
													</div></td>
												<td><div class="have_stake">
														<s:if test="#carUser.haveStake==0">没有 </s:if>
														<s:if test="#carUser.haveStake==1">有 </s:if>
													</div></td>
												<td><div class="faith">
														<s:property value="#carUser.faith" />
													</div></td>
												<td><div class="remaining">
														<s:property value="#carUser.remaining" />
													</div></td>
												<td>

													<a href="ShowPersonalInfo_showCurrentCarUser?currentCarUserId=<s:property value='#carUser.id'/>"
													class="button button-action button-rounded  button-tiny"> 查看</a>
												    <a href="ShowPersonalInfo_deleteById?currentCarUserId=<s:property value='#carUser.id'/>"
													class="button button-caution button-rounded button-tiny" onclick="return confirm('确定要删除吗？');">删除</a></td>
											</TR>
										</s:iterator>
									</TBODY>
								</TABLE>
								<br />
								<div style="text-align:center">
									<jsp:include page="/inc/pager.jsp">
										<jsp:param value="ShowPersonalInfo_showAllCarUser.action"
											name="url" />
										<jsp:param value="${pageUsers.totalRecord }" name="items" />
									</jsp:include>
								</div>
							</div>
						</form>
					</div>

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
