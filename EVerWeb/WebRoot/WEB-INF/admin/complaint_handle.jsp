<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//dtD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/dtD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理中心</title>
<link rel="stylesheet" type="text/css" href="css/top-search.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/info.css">
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<script type="text/javascript" language="javascript">
	//js
	function freset(obj) {
		obj.reset();
	}
	function check() {
		var ops = document.getElementsByName("faith")[0];
		var ops1 = document.getElementsByName("remaining")[0];
		var form = document.getElementsByName("userForm")[0];
		if (ops.value.length == 0) {
			alert("诚信值不能为空！");
			ops.forcus();
			return false;
		}
		if (ops1.value.length == 0) {
			alert("余额不能为空！");
			ops1.forcus();
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

			<!--right-->
			<div class="right">
				<div class="o-mt">
					<H2>投诉申请单</H2>
				</div>
				<div id="buy-area" class="m m5" data-widget="tabs">
					<div class="mc">
						<div class="tb-void">
							<TABLE border="0" cellSpacing="0" cellPadding="0" width="100%">
								<TBODY>
									<TR>
										<TH width="100">编号</TH>
										<TH width="100">投诉方</TH>
										<TH width="100">被投诉方</TH>
										<TH width="100">时间</TH>
										<TH width="300">内容</TH>
									</TR>
								</TBODY>

								<TBODY id="tb" class="parent">
									<TR id="track">
										<td><div class="id">
												<s:property value="complaint.id" />
											</div>
										</td>
										<td><div class="from_id">
												<s:property value="complaint.userByFromId.name" />
											</div>
										</td>
										<td><div class="to_id">
												<s:property value="complaint.userByToId.name" />
											</div>
										</td>
										<td><div class="time">
												<s:property value="complaint.time" />
											</div>
										</td>
										<td><div class="content">
												<s:property value="complaint.content" />
											</div></td>
									</TR>
								</TBODY>
							</TABLE>
							<br />
						</div>
					</div>
				</div>
				<div class="o-mt">
					<H2>被投诉的车桩使用记录</H2>
				</div>
				<div id="buy-area" class="m m5" data-widget="tabs">
					<div class="mc">
						<div class="tb-void">
							<table border="0" cellSpacing="0" cellPadding="0" width="100%">
								<tbody>
									<tr>
										<TH width="80">车主姓名</TH>
										<TH width="100">车主电话</TH>
										<TH width="80">车桩主姓名</TH>
										<TH width="100">车桩主电话</TH>
										<TH width="100">开始时间</TH>
										<TH width="100">结束时间</TH>
										<TH width="80">状态</TH>
									</tr>
								</tbody>

								<tbody id="tb" class="parent">
									<tr id="track">
										<td><div class="name">
												<s:property
													value="complaint.useStakeByUseStakeId.userByUserId.name" />
											</div></td>
										<td><div class="phone">
												<s:property
													value="complaint.useStakeByUseStakeId.userByUserId.telephone" />
											</div></td>
										<td><div class="stake_owner_name">
												<s:property
													value="complaint.useStakeByUseStakeId.userByStakeOwnerId.name" />
											</div></td>
										<td><div class="stake_owner_telephone">
												<s:property
													value="complaint.useStakeByUseStakeId.userByStakeOwnerId.telephone" />
											</div></td>
										<td><div class="stime">
												<s:property value="complaint.useStakeByUseStakeId.startTime" />
											</div></td>
										<td><div class="etime">
												<s:property value="complaint.useStakeByUseStakeId.endTime" />
											</div>
										</td>
										<td>
											<div class="status">
												<s:if test="complaint.useStakeByUseStakeId.status==0">正在进行 </s:if>
												<s:if test="complaint.useStakeByUseStakeId.status==1">已完成 </s:if>
												<s:if test="complaint.useStakeByUseStakeId.status==2">异常 </s:if>
											</div></td>
									</tr>
								</tbody>
							</table>
							<br />
						</div>
					</div>
				</div>

				<div id="baseinfo" class="m">
					<div class="mc"></div>
					<div class="mt">
						<UL class="tab">
							<li class="curr"><S></S><B></B><a href="">被投诉者基本信息</a>
							</li>
						</UL>
					</div>
					<div class="mc">
						<div class="i-m">
							<div class="i-mc">
								<form name="userForm" id="userForm"
									action="ComplaintAction_handle?currentCompId=<s:property value='complaint.id'/>" method="post">
									<div class="form">
										<div class="item">
											<span class="label">用户名：</span>
											<div class="fl">
												<strong class="username"><s:property
														value="carUser.username" /> </strong> <span class="rank r3"><S></S>
													<a></a> </span>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<span class="label">姓名：</span>
											<div class="fl">
												<strong class="username"><s:property
														value="carUser.name" /> </strong> <span class="rank r3"><S></S>
													<a></a> </span>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<span class="label">电话：</span>
											<div class="fl">
												<strong class="username"><s:property
														value="carUser.telephone" /> </strong> <span class="rank r3"><S></S>
													<a></a> </span>
											</div>
											<div class="clr"></div>
										</div>

										<div class="item">
											<span class="label">是否拥有车桩：</span>
											<div id="have_stake" class="fl">
												<strong class="have_stake"> <s:if
														test="carUser.haveStake==0">没有车桩</s:if> <s:if
														test="carUser.haveStake==1">有车桩</s:if> </strong> <span
													class="rank r3"><S></S> <a></a> </span>
											</div>
											<div class="clr"></div>
										</div>
										<div class="item">
											<span class="label"><em>*</em>诚信度： </span>
											<div id="faith" class="fl">
												<input type="number" max="100" style="margin-left:10px" id="faith" class="text" name="faith"
													value=<s:property value="carUser.faith"/>>
											</div>
											<div class="clr"></div>
										</div>

										<div class="item">
											<span class="label"><em>*</em>余额：</span>
											<div class="fl">
												<div>
													<input id="remaining" style="margin-left:10px" class="text" type="number" name="remaining"
														value=<s:property value="carUser.remaining"/>> <S
														id="petName_orderly"></S>
													<div class="clr"></div>
												</div>
											</div>
											<div class="clr"></div>
										</div>



										<div class="item">
											<span class="label">&nbsp;</span>
											<div class="fl">
												<a href="javascript:void(0)"
													class="button button-action button-rounded button-tiny"
													onclick="check();"style="margin-left:10px"><s></s>确定惩罚</a>
											</div>
										</div>
										<div class="clr"></div>

									</div>
								</form>
							</div>
							<a style="margin-left:140px;color:red;font-size:1.5em;">${msg}</a>
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

