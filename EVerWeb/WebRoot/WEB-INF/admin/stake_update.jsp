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
	function fsubmit() {
		var form = document.getElementsByName("stakeForm")[0];
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
					<H2>修改车桩信息</H2>
				</div>
				<div id="baseinfo" class="m">
					<div class="mc">
						<div class="i-m">
							<div class="i-mc">
								<form name="stakeForm" id="stakeForm"
									onsubmit="javascript:return checkValue();" action="StakeAction_update"
									method="post">
									<div class="form">
										<div style="DISPLAY: none">
											<A id="verify_link" href="javascript:void(0);"
												target="_blank">text</A>
										</div>
										<div class="item">
											<SPAN class="label">车桩主用户名：</SPAN>
											<div class="fl">
												<STRONG class="username"><s:property
														value="stake.user.username" /> </STRONG> <SPAN class="rank r3"><S></S>
													<A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">车桩主姓名：</SPAN>
											<div class="fl">
												<STRONG class="name"><s:property
														value="stake.user.name" /> </STRONG> <SPAN class="rank r3"><S></S>
													<A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">车桩主电话：</SPAN>
											<div class="fl">
												<STRONG class="telephone"><s:property
														value="stake.user.telephone" />4</STRONG> <SPAN class="rank r3"><S></S>
													<A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">车桩经度：</SPAN>
											<div class="fl">
												<STRONG class="longitude"><s:property
														value="stake.longitude" /> </STRONG> <SPAN class="rank r3"><S></S>
													<A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">车桩纬度：</SPAN>
											<div id="telephone" class="fl">
												<STRONG class="latitude"><s:property
														value="stake.latitude" /> </STRONG><SPAN class="rank r3"><S></S>
													<A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">开始时间：</SPAN>
											<div id="telephone" class="fl">
												<STRONG class="availableStime"><s:property
														value="stake.availableStime" /> </STRONG><SPAN class="rank r3"><S></S>
													<A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">结束时间：</SPAN>
											<div id="telephone" class="fl">
												<STRONG class="availableEtime"><s:property
														value="stake.availableEtime" /> </STRONG><SPAN class="rank r3"><S></S>
													<A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">车桩描述：</SPAN>
											<div id="telephone" class="fl">
												<STRONG class="description"><s:property
														value="stake.description" /> </STRONG><SPAN class="rank r3"><S></S>
													<A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label"><EM>*</EM>状态：</SPAN>
											<div id="status" class="fl">
												<select name="status" class="selt" style="width: 130px;">
													<option value="0"
														<s:if test="stake.status==0">selected = "selected"</s:if>>可用</option>
													<option value="1"
														<s:if test="stake.status==1">selected = "selected"</s:if>>正在使用</option>
													<option value="2"
														<s:if test="stake.status==2">selected = "selected"</s:if>>未分享</option>
													<option value="3"
														<s:if test="stake.status==3">selected = "selected"</s:if>>异常</option>
													<option value="4"
														<s:if test="stake.status==4">selected = "selected"</s:if>>待审核</option>
													<option value="5"
														<s:if test="stake.status==5">selected = "selected"</s:if>>审核未通过</option>
												</select>
											
											</div>
										</div>
										<div class="item"></div>
										<div class="item">
											<span class="label">&nbsp;</span>
											<div class="fl">
												<a href="javascript:void(0);" onclick="fsubmit();" style="margin-left: 20px;margin-top: 10px;"
													class="button button-action button-rounded  button-small">修改</a>
											</div>
										</div>
										<div class="clr"></div>
									</div>
								</form>
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

