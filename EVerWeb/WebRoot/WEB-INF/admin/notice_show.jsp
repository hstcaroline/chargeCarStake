<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
						<H2>所有通知</H2>
					</div>
					<div id="buy-area" class="m m5" data-widget="tabs">
						<div class="mc">
							<div class="tb-void">
								<TABLE border="0" cellSpacing="0" cellPadding="0" width="100%">
									<TBODY>
										<TR>
											<TH width="100">编号</TH>
											<TH width="100">消息接受方</TH>
											<TH width="100">消息发送时间</TH>
											<TH width="100">标题</TH>
											<TH width="100">内容</TH>
											<TH width="120">操作</TH>
										</TR>
									</TBODY>

									<TBODY id="tb" class="parent">
										<s:iterator value="notices" id="notice">
											<TR id="track">
												<td><div class="id">
														<s:property value="#notice.id" />
													</div>
												</td>
												<td><div class="type">
													<s:if test="#notice.type==0">仅车主</s:if>
													<s:if test="#notice.type==1">仅车桩主</s:if>
													<s:if test="#notice.type==2">所有人</s:if>
													</div>
												</td>
												<td><div class="time">
														<s:date name="#notice.time" format="yyyy-MM-dd HH:mm:ss"/>
													</div>
												</td>
												<td><div class="title">
														<nobr><s:property value="#notice.title" /></nobr>
													</div>
												</td>
												<td><div class="content">
														<nobr><s:property value="#notice.content" /></nobr>
													</div></td>
												<td><a href="NoticeAction_noticeDetail?currentNoticeId=<s:property value='#notice.id'/>" class="button button-action
														button-rounded button-tiny">查看</a> 
													<a href="NoticeAction_deleteNotice?currentNoticeId=<s:property value='#notice.id'/>" 
													class="button button-caution button-rounded button-tiny" onclick="return confirm('确定要删除吗？');">删除</a>
												</td>
											</TR>
										</s:iterator>
									</TBODY>
								</TABLE>
								<br />
								<div style="text-align:center">
									<jsp:include page="/inc/pager.jsp">
										<jsp:param value="NoticeAction_showAllNotice.action" name="url" />
										<jsp:param value="${pagerNotice.totalRecord }" name="items" />
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

