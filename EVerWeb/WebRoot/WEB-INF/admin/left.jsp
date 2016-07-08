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
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/info.css">
</head>
<body>
	<div class="main">
		<div class="w main">
			<div class="left">
				<div id="ever" class="m">
					<div class="mt">
						<h2>
							<a href="javascript:void(0);">管理中心</a>
						</h2>
					</div>
					<div class="mc">
						<dl>
							<dt>
								账户管理<b></b>
							</dt>
							<dd>
								<div class="item">
									<a href="LoginAction_load">基本信息</a>
								</div>
								<div class="item">
									<a href="ShowPersonalInfo_load.action">修改密码</a>
								</div>
							</dd>
						</dl>
						<dl>
							<dt>
								用户管理<b></b>
							</dt>
							<dd>
								<div class="item">
									<a href="ShowPersonalInfo_showAllCarUser">查看所有用户</a>
								</div>
								<div class="item">
									<a href="ComplaintAction_showUnsolvedCom">用户投诉管理</a>
								</div>
							</dd>
						</dl>
						<dl>
							<dt>
								充电桩管理<b></b>
							</dt>
							<dd>
								<div class="item">
									<a href="StakeAction_showAllStake">查看所有车桩</a>
								</div>
								<div class="item">
									<a href="StakeAction_showUncheckedStake">充电桩审核</a>
								</div>
							</dd>
						</dl>
						<dl>
							<dt>
								使用记录管理<b></b>
							</dt>
							<dd>
								<div id="_ever_balance" class="item">
									<a href="UseStakeAction_showAllUseStake">查看充电记录</a>
								</div>
								<!-- <div id="_ever_refundment" class="item">
									<a href="#">修改与删除</a>
								</div> -->
							</dd>
						</dl>
						<dl>
							<dt>
								通知管理<b></b>
							</dt>
							<dd>
								<div class="item">
									<a href="NoticeAction_loadNoticeAdd">发布通知</a>
								</div>
								<div class="item">
									<a href="NoticeAction_showAllNotice">查看通知</a>
								</div>
							</dd>
						</dl>
						<dl>
							<dt>
								车型管理<b></b>
							</dt>
							<dd>
								<div class="item">
									<a href="CarTypeAction_loadAddCarType">添加车型</A>
								</div>
								<div class="item">
									<a href="CarTypeAction_showAllCarType">修改与删除</A>
								</div>
							</dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
