<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link rel="stylesheet" type="text/css" href="css/updateps.css">
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<script language="javascript">
	//js
	function check() {
		var form = document.getElementsByName("carTypeForm")[0];
		if (form.typeName.value.length == 0) {
			alert("请输入内容！");
			return;
		}

		form.submit();
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
			<form name="carTypeForm" id="carTypeForm" action="CarTypeAction_add" method="post">
				<!--right-->
				<DIV class="right">
					<DIV class="o-mt">
						<H2>添加车型描述</H2>
					</DIV>
					<DIV id="baseinfo" class="m">
						<DIV class="mt">
							<UL class="tab">
								<LI class="curr"><S></S><B></B><A href="">添加车型</A>
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
											<SPAN class="label"><EM>*</EM>车型描述：</SPAN>
											<DIV class="fl">
												<DIV>
													<textarea id="typeName" name="typeName"
														style="width:300px;height:150px;"></textarea>
												</DIV>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>

										<DIV class="item">
											<SPAN class="label">&nbsp;</SPAN>
											<DIV class="fl">
												<a href="javascript:void(0)"
													class="button button-action button-rounded button-tiny"
													onclick="check();"><s></s>添加</a>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>

										<a style="margin-left:140px;color:red;font-size:1.5em;">${msg}</a>
									</DIV>

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

