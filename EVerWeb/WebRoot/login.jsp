<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<link rel="stylesheet" type="text/css" href="css/login.css" />
<script type="text/script" src="js/jquery-1.11.3.min.js"></script>
<script language="javascript">  //登录js
function fsubmit(obj){
	//alert($('#userName'));
	/*
	if (document.getElementById('userName') === null || '' === document.getElementById('userName').value.trim()) {
		alert('用户名不能为空');
	} else if (document.getElementById('passWord') === null || '' === document.getElementById('passWord').value.trim()) {
		alert('密码不能为空')
	} else {
		obj.submit();
	}
	*/
	obj.submit();		
}
function freset(obj){
	obj.reset();
}
</script>
</head>
<body>
	<div class="w">
		<div id="logo">
			<img alt="充电桩" src="img/login/logo.png" width="170" height="60" />
		</div>
	</div>
	<form id="formlogin" name="login" method="post" action="LoginAction">
		<div id="entry" class=" w1">
			<div class="extra-en">
				<a>[管理员登录]</a>
			</div>
			<div id="bgDiv" class="mc ">
				<div
					style="width: 407px; background: url(img/login/manager.jpg) no-repeat 0px 0px; height: 354px"
					id="entry-bg"></div>
				<div class="form ">
					<div class="item fore1">
						<span>用户账号</span>
						<div class="item-ifo">
							<input id="userName" class="text" name="userName" value="" />
							<div class="i-name ico"></div>
						</div>
					</div>
					<div class="item fore2">
						<span>密码</span>
						<div class="item-ifo">
							<input id="passWord" class="text" type="password"
								name="passWord" />
						</div>
					</div>

					<div>
						<%@ taglib prefix="s" uri="/struts-tags" %>
						<s:if test="hasFieldErrors()"> 
							<s:iterator value="fieldErrors">
								<font color=red><s:property value="value[0]"/></font>
							</s:iterator>
						</s:if>
					</div>	
					<div class="item login-btn2013">
						<a href="javascript:fsubmit(document.login);"> <img
							src="img/login/loginButton.png" border="0" /> </a>
					</div>
					<%
					    if (request.getAttribute("Error") != null) {
					        String error = request.getAttribute("Error").toString();
					        //System.out.println(error);
					%>
					<br/> 
					<h3>
						<span style="color:red" align="center"><%=error%></span>
					</h3>
					<br/>
					<%
					    }
					%>
					<%
					    if (request.getAttribute("loginFirst") != null) {
					        String error = request.getAttribute("loginFirst").toString();
					        System.out.println(error);
					%>
					<br/>
					<h3>
						<span style="color:red" align="center"><%=error%></span>
					</h3>
					<br/>
					<%
					    }
					%>
				</div>
			</div>
			<div class="free-regist">
				<span><a href="#">免费注册&gt;&gt;</a> </span>
			</div>
		</div>
	</form>

	<div class="w1">
		<div id="mb-bg" class="mb"></div>
	</div>
</body>
</html>
