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
					<H2>通知详情</H2>
				</div>
				<div id="baseinfo" class="m">
					<div class="mc">
						<div class="i-m">
							<div class="i-mc">
									<div class="form">
										<div class="item">
											<SPAN class="label">消息接受方：</SPAN>
											<div class="fl">
												<strong class="sendType" name="username">
												<s:if test="notice.type==0">仅车主</s:if>
													<s:if test="notice.type==1">仅车桩主</s:if>
													<s:if test="notice.type==2">所有人</s:if>
												</strong> <SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">消息发送时间：</SPAN>
											<div class="fl">
												<strong class="sendTime"><s:date name="notice.time" format="yyyy-MM-dd HH:mm:ss"/>
												</strong> <SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">标题：</SPAN>
											<div class="fl">
												<strong class="title"><s:property value="notice.title" />
												</strong> <SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<SPAN class="label">内容：</SPAN>
											<div class="fl">
												<strong class="content"><s:property value="notice.content" />
												</strong> <SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
											<div class="clr"></div>
										</div>

										<div class="item">
											<span class="label">&nbsp;</span>
											<div class="fl">
												 <a href="javascript:void(0);" onclick="javascript :history.back(-1);"
													class="button button-action button-rounded button-tiny"><s></s>返回</a>
												<!-- <a href="UseStakeAction_loadUpdate?status=" class="button button-action button-rounded button-tiny"><s></s>确定</a> -->	
												<SPAN class="rank r3"><S></S> <A></A> </SPAN>
											</div>
										</div>
										<div class="clr"></div>
									</div>
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

