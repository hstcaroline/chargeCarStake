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
	function submit() {
		var form = document.getElementsByName("useStakeForm")[0];
		var v = document.getElementsByName("status")[0];
		form.submit();
	}
	
	function sb() {
		
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
					<H2>修改充电记录</H2>
				</div>
				<div id="baseinfo" class="m">
					<div class="mc">
						<div class="i-m">
							<div class="i-mc">
								<form name="useStakeForm" id="useStakeForm"
									action="UseStakeAction_updateUS" method="post">
									<div class="form">
										<div class="item">
											<SPAN class="label">车主姓名：</SPAN>
											<div class="fl">
												<strong class="username" name="username"><s:property
														value="useStake.userByUserId.name" />
												</strong> <SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">车主电话：</SPAN>
											<div class="fl">
												<strong class="telephone"><s:property
														value="useStake.userByUserId.telephone" />
												</strong> <SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">车桩主姓名：</SPAN>
											<div class="fl">
												<strong class="username"><s:property
														value="useStake.userByStakeOwnerId.name" />
												</strong> <SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">车桩主电话：</SPAN>
											<div class="fl">
												<strong class="telephone" name="holderTelephone"><s:property
														value="useStake.userByStakeOwnerId.telephone" />
												</strong> <SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">开始时间：</SPAN>
											<div id="telephone" class="fl">
												<strong class="availableStime"><s:property
														value="useStake.startTime" />
												</strong> <SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">结束时间：</SPAN>
											<div id="telephone" class="fl">
												<strong class="availableEtime"><s:property
														value="useStake.endTime" />
												</strong> <SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>

										<div class="item">
											<SPAN class="label"><EM>*</EM>状态：</SPAN>
											<div class="fl">
												<SELECT id="status" class="sele" name="status"
													style="width:130px;">
													<!-- <OPTION value="useStake.status==0" selected>正在进行</OPTION>
													<OPTION value="useStake.status==1">已完成</OPTION>
													<OPTION value="useStake.status==2">异常</OPTION> -->
													<option value="0"
														<s:if test="useStake.status==0">selected = "selected"</s:if>>正在进行</option>
													<option value="1"
														<s:if test="useStake.status==1">selected = "selected"</s:if>>已完成</option>
													<option value="2"
														<s:if test="useStake.status==2">selected = "selected"</s:if>>异常</option>
												</SELECT>
												<div class="clr"></div>
												<div class="prompt-06">
													<SPAN id="province_msg"></SPAN>
												</div>
											</div>
											<div class="clr"></div>
										</div>


										<div class="item">
											<span class="label">&nbsp;</span>
											<div class="fl">
												 <a href="javascript:void(0);" onclick="submit();"
													class="button button-action button-rounded button-tiny"><s></s>确定</a>
												<!-- <a href="UseStakeAction_loadUpdate?status=" class="button button-action button-rounded button-tiny"><s></s>确定</a> -->	
												<SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
										</div>
										<div class="clr"></div>
									</div>
								</form>
								<!--  <a style="margin-left:140px;color:red;font-size:1.5em;">${msg}</a>-->
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

