<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBliC "-//W3C//dtD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/dtD/xhtml1-transitional.dtd">
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
			<form name="carTypeForm" id="carTypeForm" action="CarTypeAction_update?currentId=<s:property value="carType.id" />" method="post">
				<!--right-->
				<div class="right">
					<div class="o-mt">
						<H2>添加车型描述</H2>
					</div>
					<div id="baseinfo" class="m">
						<div class="mt">
							<ul class="tab">
								<li class="curr"><S></S><B></B><A href="">添加车型</A>
								</li>
							</ul>
						</div>
						<div class="mc">
							<div class="i-m">
								<div class="i-mc">
									<div class="form">
										<div class="item">
											<span class="label"><EM>*</EM>车型描述：</span>
											<div class="fl">
												<div>
													<textarea id="typeName" name="typeName"
														style="width:300px;height:150px;"><s:property value="carType.typeName" /></textarea>
												</div>
											</div>
											<div class="clr"></div>
										</div>

										<div class="item">
											<span class="label">&nbsp;</span>
											<div class="fl">
												<a href="javascript:void(0)"
													class="button button-action button-rounded button-tiny"
													onclick="check();"><s></s>确定修改</a>
											</div>
											<div class="clr"></div>
										</div>

										<a style="margin-left:140px;color:red;font-size:1.5em;">${msg}</a>
									</div>

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

