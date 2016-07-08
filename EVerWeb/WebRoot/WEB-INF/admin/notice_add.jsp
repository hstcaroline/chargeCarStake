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
<script language="javascript">  //登录js
function check(){
var form= document.getElementsByName("noticeForm")[0];
	if(form.title.value.length==0){
	alert("请输入标题！");
	return;
	}
	if(form.content.value.length==0){
	alert("请输入内容！");
	return;
	}

form.submit();
}
function freset(obj){
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
			<form name="noticeForm" id="noticeForm" action="NoticeAction_addNotice" method="post">
				<!--right-->
				<div class="right">
					<div class="o-mt">
						<H2>添加通知</H2>
					</div>
					<div id="baseinfo" class="m">
						<div class="mc">
							<div class="i-m">
								<div class="i-mc">
									<div class="form" >
										<div style="DISPLAY: none">
											<A id="verify_link" href="javascript:void(0);"
												target="_blank">text</A>
										</div>
										<div class="item">
											<SPAN class="label"><EM>*</EM>发送给：</SPAN>
											<div class="fl">
												<SELECT id="type" class="selc" name="type"
													style="width:130px;">
													<option value="0" >仅车主</option>
													<option value="1">仅车桩主</option>
													<option value="2" selected>所有人</option>
												</SELECT>
												<div class="clr"></div>
												<div class="prompt-06">
													<SPAN id="province_msg"></SPAN>
												</div>
											</div>
										</div>
										<div class="item">
											<SPAN class="label"><EM>*</EM>标题：</SPAN>
											<div class="fl">
												<div id="cidInputdiv">
													<INPUT id="title" class="text" name="title"
														style="width:250px;" value="" />
												</div>
												<div class="clr"></div>
												<div class="prompt-06">
													<SPAN id="ucid_msg"></SPAN>
												</div>
											</div>
											<div class="clr"></div>
										</div>

										<div class="item">
											<SPAN class="label"><EM>*</EM>内容：</SPAN>
											<div class="fl">
												<div>
													<textarea id="content" name="content"
														style="width:300px;height:150px;"></textarea>
												</div>
											</div>
											<div class="clr"></div>
										</div>

										<div class="item">
											<SPAN class="label">&nbsp;</SPAN>
											<DIV class="fl">
												<a href="javascript:void(0)" class="button button-action button-rounded button-tiny"
													onclick="check();"><s></s>下一步</a>
											</DIV>
											<div class="clr"></div>
										</div>
										<!--  <a style="margin-left:140px;color:red;font-size:1.5em;">${msg}</a>-->
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

