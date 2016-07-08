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
<link rel="stylesheet" type="text/css" href="css/updateps.css">
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<script type="text/javascript" language="javascript">
	//登录js
	function freset(obj) {
		obj.reset();
	}
	function check() {
		var ops = document.getElementsByName("oldpassword")[0];
		var form = document.getElementsByName("userForm")[0];
		if (ops.value.length == 0) {
			alert("原密码不能为空！");
			ops.forcus();
			return false;
		} else if (ops.value.length < 3) {
			alert("密码格式不正确！");
			ops.forcus();
			return false;
		}
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
			<div class="right">
				<div class="o-mt">
					<H2>修改密码</H2>
				</div>
				<DIV id="baseinfo" class="m">
					<div class="mc">
						<div class="i-m">
							<div class="i-mc">
								<div class="form">
									<FORM id="userForm" name="userForm" method="post"
										action="ShowPersonalInfo_checkOldpsw.action">

										<DIV class="item">
											<SPAN class="label"><EM>*</EM>原密码：</SPAN>
											<DIV class="fl">
												<DIV>
													<INPUT id="oldpassword" type="password" class="text" name="oldpassword"
														value="" /> <A style="color:red;margin-left:10px;"><s:property value="erroMsg" /></A>
												</DIV>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>
										<DIV class="item">
											<SPAN class="label">&nbsp;</SPAN>
											<DIV class="fl">
												<a href="javascript:void(0)" class="button button-action button-rounded button-tiny"
													onclick="check();"><s></s>下一步</a>
											</DIV>
											<DIV class="clr"></DIV>
										</DIV>
									</FORM>
								</div>
								<!--userinfo-->
								<!--reco-area-->
								<div class="clr"></div>
								<!--con-area-->
							</div>
						</div>
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

