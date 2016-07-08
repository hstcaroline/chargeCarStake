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
<script language="javascript">
	//登录js
	function check() {
		var form = document.getElementsByName("MsgForm")[0];
		if (form.title.value.length == 0) {
			alert("请输入标题！");
			return;
		}
		if (form.content.value.length == 0) {
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
			<div class="right">
				<div class="o-mt">
					<H2>已审核车桩的基本信息</H2>
				</div>
				<div id="userinfo" class="m m3">
					<div class="user">
						<div class="u-icon picture">
							<s:if test="stake.qrCode!=null">
							<img id="qrCode_img" style="width:180px;padding-right:10px"
								alt="车桩二维码" src="<s:property value="stake.qrCode"/>" />
							</s:if>
						</div>
					</div>
					<!--user-->
					<div id="i-userinfo">
						<div id="remind">
							<div class="oinfo" style="width:700px">
								<dl class="fore">
									<dt>车桩主姓名：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="stake.user.name" /> </A> </span>
									</dd>
									<dt>车桩主电话：</dt>
									<dd>
										<span style="width:50px"> <a class="flk-03"
											style="margin-left:8px;color:blue;"><s:property
													value="stake.user.telephone" /> </A> </span>
									</dd>
									<dt>诚信值：</dt>
									<dd>
										<a class="flk-03" style="margin-left:8px;color:blue;"> <s:property
												value="stake.user.faith" /> </a>
									</dd>
								</dl>
							</div>
						</div>

						<div id="remind">
							<div class="oinfo" style="width:700px">
								<dl class="fore">
									<dt>车桩开始时间：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"> <s:property
													value="stake.availableStime" /> </A> </span>
									</dd>
									<dt>车桩结束时间：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"> <s:property
													value="stake.availableEtime" /> </A> </span>
									</dd>
									<dt>车桩描述：</dt>
									<dd>
										<a class="flk-03" style="margin-left:8px;color:blue;"> <nobr>
												<s:property value="stake.description" />
											</nobr> </a>
									</dd>
								</dl>
							</div>
						</div>
						<div id="remind">
							<div class="oinfo" style="width:700px">
								<dl class="fore">
									<dt>车桩可充车型：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"> <s:property
													value="stake.carType.typeName" /> </A> </span>
									</dd>
									<dt>车桩类型：</dt>
									<dd>
										<span> <a class="flk-03"
											style="margin-left:8px;color:blue;"> <s:if
													test="stake.type==0">公有桩</s:if> <s:if test="stake.type==1">私有桩</s:if>
										</A> </span>
									</dd>
									<dt>车桩状态：</dt>
									<dd>
										<a class="flk-03" style="margin-left:8px;color:blue;"> <nobr>
												<s:if test="stake.status==0">可用 </s:if>
												<s:if test="stake.status==1">正在使用 </s:if>
												<s:if test="stake.status==2">未分享</s:if>
												<s:if test="stake.status==3">异常</s:if>
												<s:if test="stake.status==4">待审核</s:if>
												<s:if test="stake.status==5">审核未通过</s:if>
											</nobr> </a>
									</dd>
								</dl>
							</div>
						</div>
					</div>
				</div>

				<form name="MsgForm" id="MsgForm"
					action="MessageAction_addMsg?sendTo=<s:property value="stake.user.id"/> "
					method="post">
					<!--right-->

					<div class="o-mt">
						<H2>车桩审核完成，将审核结果发送给车桩主</H2>
					</div>
					<div id="baseinfo" class="m">
						<div class="mc">
							<div class="i-m">
								<div class="i-mc">
									<div class="form">
										<div style="DISPLAY: none">
											<A id="verify_link" href="javascript:void(0);"
												target="_blank">text</A>
										</div>
										<!-- <div class="item">
											<SPAN class="label"><EM>*</EM>发送给：</SPAN>
											<div class="fl">
												<input id="title" class="text" name="title"
														style="width:250px;" value="" />
												<div class="clr"></div>
												<div class="prompt-06">
													<SPAN id="province_msg"></SPAN>
												</div>
											</div>
										</div> -->
										<div class="item">
											<SPAN class="label"><EM>*</EM>标题：</SPAN>
											<div class="fl">
												<div id="cidinputdiv">
													<input id="title" class="text" name="title"
														style="width:250px;" value="车桩审核反馈" />
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
												<a href="javascript:void(0)"
													class="button button-action button-rounded button-tiny"
													onclick="check();"><s></s>发送</a>
											</DIV>
											<div class="clr"></div>
										</div>
										<!--  <a style="margin-left:140px;color:red;font-size:1.5em;">${msg}</a>-->
									</div>

								</div>
							</div>
						</div>
					</div>
				</form>
			</div>

			<!--right-->
			<!-- left -->
			<jsp:include page="left.jsp" flush="false" />
			<!-- left end -->
		</div>
	</div>
</body>
</html>

