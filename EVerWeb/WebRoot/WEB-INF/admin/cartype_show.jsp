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
						<H2>车型列表</H2>
					</div>
					<div id="buy-area" class="m m5" data-widget="tabs">
						<div class="mc">
							<div class="tb-void">
								<TABLE border="0" cellSpacing="0" cellPadding="0" width="100%">
									<TBODY>

										<TR>
											<TH width="100">编号</TH>
											<TH width="100">车型描述</TH>
											<TH width="100">操作</TH>
										</TR>
									</TBODY>

									<TBODY id="tb" class="parent">
										<s:iterator value="carTypes" id="carType">
											<TR id="track">
												<td><div class="number">
														<s:property value="#carType.id" />
													</div></td>
												<td><div class="type_name">
														<s:property value="#carType.typeName" />
													</div></td>
												<td><a href="CarTypeAction_loadUpdate?currentId=<s:property value="#carType.id" />"
													class="button button-action
														button-rounded button-tiny">修改</a>
													<a href="CarTypeAction_deleteCarType?currentId=<s:property value="#carType.id" />"
													class="button button-caution button-rounded button-tiny" onclick="return confirm('确定要删除吗？');">
														删除</a>
											</TR>
										</s:iterator>
									</TBODY>
								</TABLE>
								<br />
								<div style="text-align:center">
									<jsp:include page="/inc/pager.jsp">
										<jsp:param value="CarTypeAction_showAllCarType.action"
											name="url" />
										<jsp:param value="${pageCarType.totalRecord }" name="items" />
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

