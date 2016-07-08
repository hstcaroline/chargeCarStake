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
<link rel="stylesheet" type="text/css" href="css/info.css">
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<script type="text/javascript" language="javascript">
	//登录js
	function freset(obj) {
		obj.reset();
	}
	function check() {
		var ops = document.getElementsByName("faith")[0];
		var ops1 = document.getElementsByName("remaining")[0];
		var form = document.getElementsByName("userForm")[0];
		if (ops.value.length == 0) {
			alert("诚信值不能为空！");
			ops.focus();
			return false;
		} 
		if(ops.value>100)
		{
			alert("诚信值不能超过100");
			ops.focus();
			return false;
		}
		if (ops1.value.length ==0) {
			alert("余额不能为空！");
			ops1.focus();
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
			<form name="userForm" id="userForm" action="ShowPersonalInfo_update" method="post">
				<div class="right">
					<div class="o-mt">
						<H2>修改用户信息</H2>
					</div>
					<div id="baseinfo" class="m">
						<div class="mc">
							<div class="i-m">
								<div class="i-mc">
									<div class="form">
										<div class="item">
											<span class="label">用户名：</span>
											<div class="fl">
												<strong class="username"><s:property
														value="carUser.username" />
												</strong> <span class="rank r3"><S></S> <a></a> </span>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<span class="label">姓名：</span>
											<div class="fl">
												<strong class="username"><s:property
														value="carUser.name" />
												</strong> <span class="rank r3"><S></S> <a></a> </span>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<span class="label">电话：</span>
											<div class="fl">
												<strong class="username"><s:property
														value="carUser.telephone" />
												</strong>
												 <span class="rank r3"><S></S> <a></a> </span>
											</div>
											<div class="clr"></div>
										</div>

										<div class="item">
											<span class="label">是否拥有车桩：</span>
											<div id="have_stake" class="fl">
												<strong class="have_stake"> <s:if
														test="carUser.haveStake==0">没有车桩</s:if> <s:if
														test="carUser.haveStake==1">有车桩</s:if> 
												</strong>
												<span class="rank r3"><S></S> <a></a> </span>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<span class="label"><em>*</em>诚信度： </span>
											<div id="faith" class="fl">
												 <input type="number" style="margin-left:10px" id="faith" max="100" class="text" name="faith"
														value=<s:property value="carUser.faith"/> >
											</div>
											<div class="clr"></div>
										</div>
										
										<div class="item">
											<span class="label"><em>*</em>余额：</span>
											<div class="fl">
												<div>
													<input id="remaining" style="margin-left:10px" type="number" class="text" name="remaining"
														value=<s:property value="carUser.remaining"/> /> 
													<div class="clr"></div>
												</div>
											</div>
											<div class="clr"></div>
										</div>



										<div class="item">
											<span class="label">&nbsp;</span>
											<div class="fl">
												<a href="javascript:void(0)" class="button button-action button-rounded button-tiny"
													onclick="check();" style="margin-left:20px"><s></s>确定</a>
											</div>
										</div>
										<div class="clr"></div>
									</div>

									<a style="margin-left:140px;color:red;font-size:1.5em;">${msg}</a>
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

