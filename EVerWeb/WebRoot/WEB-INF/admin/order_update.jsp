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
<script language="javascript">
	//登录js
	function fsubmit(obj) {
		var form = document.getElementsByName("userForm")[0];
		if (form.id.faith.length == 0) {
			alert("请输入诚信度！");
			return;
		} else if (form.remaining.value.length == 0) {
			alert("请输入余额！");
			return;
		}
		obj.submit();
	}
	function freset(obj) {
		obj.reset();
	}
</script>
</head>
<body>
	<!-- 顶部导航条 -->
	<jsp:include page="top.jsp" flush="false" />
	<!-- header end-->
	<div class="main">
		<div class="w main">
			<form name="userForm" id="userForm"
				onsubmit="javascript:return checkValue();" action="#" method="post">
				<DIV class="right">
					<DIV class="o-mt">
						<H2>修改充电记录</H2>
					</DIV>
					<DIV id="baseinfo" class="m">
						<DIV class="mt">
							<UL class="tab">
								<LI class="curr"><S></S><B></B><A href="">充电记录基本信息</A>
								</LI>
							</UL>
						</DIV>
						<DIV class="mc">
							<DIV class="i-m">
								<DIV class="i-mc">
									<DIV class="form">
										<DIV style="DISPLAY: none">
											<A id="verify_link" href="javascript:void(0);"
												target="_blank">text</A>
										</DIV>
										<DIV class="item">
											<SPAN class="label">车桩主姓名：</SPAN>
											<DIV class="fl">
												<STRONG class="username"><s:property value="stake.user.name"/></STRONG> <SPAN class="rank r3"><S></S>
													<A></A> </SPAN>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>
										<DIV class="item">
											<SPAN class="label">车桩主电话：</SPAN>
											<DIV class="fl">
												<STRONG class="telephone"><s:property value="stake.user.telephone"/>4</STRONG> <SPAN
													class="rank r3"><S></S> <A></A> </SPAN>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>
										<DIV class="item">
											<SPAN class="label">车桩经度</SPAN>
											<DIV class="fl">
												<STRONG class="longitude"><s:property value="stake.longtitude"/></STRONG> <SPAN class="rank r3"><S></S>
													<A></A> </SPAN>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>
										<DIV class="item">
											<SPAN class="label">车桩纬度：</SPAN>
											<DIV id="telephone" class="fl">
												<STRONG class="latitude"><s:property value="stake.latitude"/></STRONG>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>
										<DIV class="item">
											<SPAN class="label">开始时间：</SPAN>
											<DIV id="telephone" class="fl">
												<STRONG class="availableStime"><s:property value="stake.availableStime"/></STRONG>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>
										<DIV class="item">
											<SPAN class="label">结束时间：</SPAN>
											<DIV id="telephone" class="fl">
												<STRONG class="availableEtime"><s:property value="stake.availableEtime"/></STRONG>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>
										<DIV class="item">
											<SPAN class="label">车桩描述：</SPAN>
											<DIV id="telephone" class="fl">
												<STRONG class="description"><s:property value="stake.description"/></STRONG>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>
										<DIV class="item">
											<SPAN class="label"><EM>*</EM>车桩状态：</SPAN>
											<DIV class="fl">
												<SELECT id="status" class="sele" name="status"
													style="width:130px;">
													<OPTION value="${item.id}" selected>已使用</OPTION>
													<OPTION value="${item.id}">未使用</OPTION>
													<OPTION value="${item.id}">已取消</OPTION>
												</SELECT>
												<DIV class="clr"></DIV>
												<DIV class="prompt-06">
													<SPAN id="province_msg"></SPAN>
												</DIV>
											</DIV>
										</DIV>


										<DIV class="item">
											<SPAN class="label">&nbsp;</SPAN>
											<DIV class="fl">
												<a href="javascript:fsubmit(document.userForm);"> <img
													src="/Charge/img/info/submit.bmp" border="0" /> </a>
											</DIV>
										</DIV>
										<DIV class="clr"></DIV>
									</DIV>

									<!--  <a style="margin-left:140px;color:red;font-size:1.5em;">${msg}</a>-->
								</DIV>

							</DIV>
						</DIV>

					</DIV>
				</DIV>
			</form>

			<!--right-->
			<!-- left -->
			<jsp:include page="left.jsp" flush="false" />
			<!-- left end -->
		</div>
	</div>
</body>
</html>

