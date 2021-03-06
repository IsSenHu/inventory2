<%--
  Created by IntelliJ IDEA.
  User: 11785
  Date: 2018/1/30
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" language="java" import="java.util.*, com.husen.enums.OrderStatu"  contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>爱动网商家系统</title>

    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="/assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

    <!-- page specific plugin styles -->

    <!-- text fonts -->
    <link rel="stylesheet" href="/assets/css/fonts.googleapis.com.css" />

    <!-- ace styles -->
    <link rel="stylesheet" href="/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
    <![endif]-->
    <link rel="stylesheet" href="/assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="/assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" type="text/css" media="all" href="/assets/css/daterangepicker-bs3.css"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/assets/css/ace-ie.min.css" />
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->
    <script src="/assets/js/ace-extra.min.js"></script>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="/assets/js/html5shiv.min.js"></script>
    <script src="/assets/js/respond.min.js"></script>
    <![endif]-->
</head>
<style>
    #add{
        position: absolute;
        width: 600px;
        height: 356px;
        z-index: 1;
        left: 30%;
        top: 20%;
        border-radius: 5px;
        box-shadow: 0.5px 0.5px 1px 1px #438EB9;
        background-color: rgba(255,255,255,0.9);
        display: none;
    }
</style>
<body class="no-skin">
<div id="navbar" class="navbar navbar-default ace-save-state" style="height: 45px;">
    <div class="navbar-container ace-save-state" id="navbar-container">
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>
        </button>

        <div class="navbar-header pull-left">
            <a href="${pageContext.request.contextPath}/toIndex.do" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    爱动网商家系统
                </small>
            </a>
        </div>

        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue dropdown-modal">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="assets/images/avatars/user.jpg" alt="Jason's Photo" />
                        <span class="user-info">
									<small>欢迎,</small>
									${sessionScope.boss.name}
								</span>

                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>

                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="/shiro/logout">
                                <i class="ace-icon fa fa-power-off"></i>
                                注销
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div><!-- /.navbar-container -->
</div>

<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try{ace.settings.loadState('main-container')}catch(e){}
    </script>

    <div id="sidebar" class="sidebar                  responsive                    ace-save-state">
        <script type="text/javascript">
            try{ace.settings.loadState('sidebar')}catch(e){}
        </script>

        <div class="sidebar-shortcuts" id="sidebar-shortcuts">
            <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                <button class="btn btn-success">
                    <i class="ace-icon fa fa-signal"></i>
                </button>

                <button class="btn btn-info">
                    <i class="ace-icon fa fa-pencil"></i>
                </button>

                <button class="btn btn-warning">
                    <i class="ace-icon fa fa-users"></i>
                </button>

                <button class="btn btn-danger">
                    <i class="ace-icon fa fa-cogs"></i>
                </button>
            </div>

            <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                <span class="btn btn-success"></span>

                <span class="btn btn-info"></span>

                <span class="btn btn-warning"></span>

                <span class="btn btn-danger"></span>
            </div>
        </div><!-- /.sidebar-shortcuts -->

        <ul class="nav nav-list">
            <li class="active">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-tachometer"></i>
                    <span class="menu-text">
								控制台
							</span>

                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">
                    <li class="">
                        <a href="${pageContext.request.contextPath}/toIndex.do">
                            <i class="menu-icon fa fa-caret-right"></i>
                            主页
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class="">
                        <a href="${pageContext.request.contextPath}/toFouth.do">
                            <i class="menu-icon fa fa-caret-right"></i>
                            查看审核情况
                        </a>

                        <b class="arrow"></b>
                    </li>
                </ul>
            </li>

            <li class="">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-list"></i>
                    <span class="menu-text"> 商品管理 </span>

                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">
                    <li class="">
                        <a href="${pageContext.request.contextPath}/pageItem.do">
                            <i class="menu-icon fa fa-caret-right"></i>
                            物料查询
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class="">
                        <a href="${pageContext.request.contextPath}/findGood.do">
                            <i class="menu-icon fa fa-caret-right"></i>
                            商品管理
                        </a>

                        <b class="arrow"></b>
                    </li>
                </ul>
            </li>

            <li class="">
                <a href="" class="dropdown-toggle">
                    <i class="menu-icon fa fa-pencil-square-o"></i>
                    <span class="menu-text"> 订单管理 </span>

                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">
                    <li class="">
                        <a href="${pageContext.request.contextPath}/orders.do">
                            <i class="menu-icon fa fa-caret-right"></i>
                            订单列表
                        </a>

                        <b class="arrow"></b>
                    </li>
                </ul>
            </li>

            <li class="">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-list-alt"></i>
                    <span class="menu-text"> 退款管理 </span>

                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">
                    <li class="">
                        <a href="${pageContext.request.contextPath}/refunds.do">
                            <i class="menu-icon fa fa-caret-right"></i>
                            退款列表
                        </a>

                        <b class="arrow"></b>
                    </li>
                </ul>
            </li>
        </ul><!-- /.nav-list -->


        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>
    </div>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="#">主页</a>
                    </li>
                    <li class="active">订单管理</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="ace-settings-container" id="ace-settings-container">
                    <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                        <i class="ace-icon fa fa-cog bigger-130"></i>
                    </div>
                    <div class="ace-settings-box clearfix" id="ace-settings-box">
                        <div class="pull-left width-50">
                            <div class="ace-settings-item">
                                <div class="pull-left">
                                    <select id="skin-colorpicker" class="hide">
                                        <option data-skin="no-skin" value="#438EB9">#438EB9</option>
                                        <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                                        <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                                        <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                                    </select>
                                </div>
                                <span>&nbsp; Choose Skin</span>
                            </div>

                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-navbar" autocomplete="off" />
                                <label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
                            </div>

                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-sidebar" autocomplete="off" />
                                <label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>
                            </div>

                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-breadcrumbs" autocomplete="off" />
                                <label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
                            </div>

                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" autocomplete="off" />
                                <label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
                            </div>

                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-add-container" autocomplete="off" />
                                <label class="lbl" for="ace-settings-add-container">
                                    Inside
                                    <b>.container</b>
                                </label>
                            </div>
                        </div><!-- /.pull-left -->

                        <div class="pull-left width-50">
                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" autocomplete="off" />
                                <label class="lbl" for="ace-settings-hover"> Submenu on Hover</label>
                            </div>

                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" autocomplete="off" />
                                <label class="lbl" for="ace-settings-compact"> Compact Sidebar</label>
                            </div>

                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" autocomplete="off" />
                                <label class="lbl" for="ace-settings-highlight"> Alt. Active Item</label>
                            </div>
                        </div><!-- /.pull-left -->
                    </div><!-- /.ace-settings-box -->
                </div><!-- /.ace-settings-container -->

                <div class="page-header">
                    <h1>
                        订单管理
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                            订单列表
                        </small>
                    </h1>
                </div><!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-4">
                                    <div class="col-xs-1" style="text-align: right;"></div>
                                    <div class="col-xs-5">
                                        <input type="text" class="form-control" id="id" name="id" placeholder="请输入订单编号"/>
                                    </div>
                                    <div class="col-xs-1" style="text-align: right;"></div>
                                    <div class="col-xs-5">
                                        <input type="text" class="form-control" id="userId" name="userId" placeholder="请输入用户ID"/>
                                    </div>
                                </div>
                                <div class="col-xs-4">
                                    <div class="col-xs-1" style="text-align: right;"></div>
                                    <div class="col-xs-5">
                                        <select id="orderStatu" name="orderStatu" class="form-control">
                                            <option value="">请选择订单状态</option>
                                            <c:forEach items="${OrderStatu.values()}" var="s">
                                                <option value="${s.value}">${s.description}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-xs-1" style="text-align: right;"></div>
                                    <div class="col-xs-5">

                                    </div>
                                </div>
                                <div class="col-xs-2">
                                    <div class="col-xs-2" style="text-align: right;"></div>
                                    <div class="col-xs-10">

                                    </div>
                                </div>
                                <div class="col-xs-2" style="text-align: left;">
                                    <a href="javascript:void(0)">
                                        <button id="findOrder" style="width: 66px;height: 32px; border-radius: 6px; background-color: #438EB9; color: #FFFFFF;">查询</button>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table  table-bordered table-hover" style="text-align: center;">
                                    <thead>
                                    <tr>
                                        <th style="width:5%; text-align: center;">订单明细</th>
                                        <th style="width:15%; text-align: center;">订单编号</th>
                                        <th style="width:20%; text-align: center;">生成时间</th>
                                        <%-- 1，有运费就显示运费，无运费就显示无运费
                                            2，添加一个查看物流信息的按钮
                                         --%>
                                        <th style="width:5%; text-align: center;">运费</th>
                                        <th style="width:10%; text-align: center;">订单状态</th>
                                        <th style="width:10%; text-align: center;">所属用户</th>
                                        <th style="width:20%; text-align: center;">发货地址</th>
                                        <th style="width:5%; text-align: center;">联系人</th>
                                        <th style="width:10%; text-align: center;">联系电话</th>
                                        <th style="width:10%; text-align: center;">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                            <c:forEach items="${page.content}" var="order">
                                            <tr>
                                                <td class="center">
                                                    <div class="action-buttons">
                                                        <a href="#" class="green bigger-140 show-details-btn" title="Show Details">
                                                            <i class="ace-icon fa fa-angle-double-down"></i>
                                                            <span class="sr-only">Details</span>
                                                        </a>
                                                    </div>
                                                </td>
                                                <td>${order.id}</td>
                                                <td><fmt:formatDate value="${order.payTime}" pattern="yyyy年MM月dd日 HH时mm分ss秒"></fmt:formatDate></td>
                                                <c:if test="${order.isFreight == 'havePostage'}">
                                                    <td><fmt:formatNumber value="${order.freight}" pattern=".00元"></fmt:formatNumber></td>
                                                </c:if>
                                                <c:if test="${order.isFreight != 'havePostage'}">
                                                    <td>0.00元</td>
                                                </c:if>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${order.statu == 0}">未付款</c:when>
                                                        <c:when test="${order.statu == 1}">已付款未发货</c:when>
                                                        <c:when test="${order.statu == 2}">已付款已发货</c:when>
                                                        <c:when test="${order.statu == 3}">确认收货未评价</c:when>
                                                        <c:when test="${order.statu == 4}">已评价</c:when>
                                                        <c:when test="${order.statu == 6}">订单已被确认</c:when>
                                                        <c:otherwise>订单已取消</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td><c:out value="${order.user.username}"></c:out></td>
                                                <td><c:out value="${order.address.address}"></c:out></td>
                                                <td><c:out value="${order.address.receivePersonName}"></c:out></td>
                                                <td><c:out value="${order.address.phone}"></c:out></td>
                                                <td>
                                                    <div class="hidden-sm hidden-xs btn-group">
                                                        <c:if test="${order.statu == OrderStatu.PAYED_BUT_NO_DELIVER.value}">
                                                            <a href="javascript:void(0)">
                                                                <button value="${order.orderId}" class="btn btn-pink sure">确认订单</button>
                                                            </a>
                                                        </c:if>
                                                        <c:if test="${order.statu == OrderStatu.SURED.value}">
                                                            <a href="javascript:void(0)">
                                                                <button value="${order.orderId}" class="btn btn-pink sure" disabled>已确认</button>
                                                            </a>
                                                        </c:if>
                                                        <c:if test="${order.statu != OrderStatu.SURED.value || order.statu != OrderStatu.SURED.value}">
                                                            无
                                                        </c:if>
                                                    </div>

                                                    <div class="hidden-md hidden-lg">
                                                        <div class="inline pos-rel">
                                                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                                                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                                            </button>

                                                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                                                <li>
                                                                    <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                                                            <span class="blue">
                                                                                <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                                            </span>
                                                                    </a>
                                                                </li>

                                                                <li>
                                                                    <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                            <span class="green">
                                                                                <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                            </span>
                                                                    </a>
                                                                </li>

                                                                <li>
                                                                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                                                            <span class="red">
                                                                                <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                                            </span>
                                                                    </a>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr class="detail-row">
                                                <td colspan="12">
                                                    <div class="table-detail">
                                                        <div class="row">
                                                            <div class="col-xs-12">
                                                                <div class="space visible-xs"></div>

                                                                <div class="profile-user-info profile-user-info-striped">

                                                                    <div class="profile-info-row">
                                                                        <div class="profile-info-name"> 其他操作 </div>

                                                                        <div class="profile-info-value">
                                                                            <button class="btn btn-info">暂无</button>
                                                                        </div>
                                                                    </div>

                                                                    <div class="profile-info-row">
                                                                        <div class="profile-info-name"> 金额合计 </div>

                                                                        <div class="profile-info-value">
                                                                            <span><fmt:formatNumber value="${order.totalMoney}" pattern=".00元"></fmt:formatNumber></span>
                                                                        </div>
                                                                    </div>

                                                                    <div class="profile-info-row">
                                                                        <div class="profile-info-name"> 所属店铺 </div>

                                                                        <div class="profile-info-value">
                                                                            <span><c:out value="${order.stores.name}"></c:out></span>
                                                                        </div>
                                                                    </div>
                                                                    <c:forEach items="${order.items}" var="item">
                                                                        <div class="profile-info-row">
                                                                            <div class="profile-info-name"><c:out value="${item.good.goodName}"></c:out></div>
                                                                            <div class="profile-info-value">
                                                                                <table class="table table-bordered">
                                                                                    <tr>
                                                                                        <th style="text-align: center; width: 20%;">商品名称</th>
                                                                                        <th style="text-align: center; width: 20%;">单价</th>
                                                                                        <th style="text-align: center; width: 20%;">数量</th>
                                                                                        <th style="text-align: center; width: 20%;">运费</th>
                                                                                        <th style="text-align: center; width: 20%;">合计</th>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="text-align: center;"><c:out value="${item.good.goodName}"></c:out></td>
                                                                                        <td style="text-align: center;"><fmt:formatNumber value="${item.unitPrice}" pattern="0.00元"></fmt:formatNumber></td>
                                                                                        <td style="text-align: center;">${item.number}</td>
                                                                                        <td style="text-align: center;"><fmt:formatNumber value="${item.freight}" pattern="0.00元"></fmt:formatNumber></td>
                                                                                        <td style="text-align: center;"><fmt:formatNumber value="${item.totalMoney + item.freight}" pattern="0.00元"></fmt:formatNumber></td>
                                                                                    </tr>
                                                                                </table>
                                                                            </div>
                                                                        </div>
                                                                    </c:forEach>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            </c:forEach>
                                    </tbody>
                                </table>
                            </div><!-- /.span -->
                        </div><!-- /.row -->
                        <div class="row" style="display: none;">
                            <div class="col-xs-12">
                                <div>
                                    <table id="dynamic-table" class="table table-striped table-bordered table-hover">
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!-- /.page-content -->
        </div>
        <c:if test="${page.rowsTotal > page.pageSize}">
            <center>
                <ul class="pagination">
                    <c:choose>
                        <c:when test="${page.currentPage == 1}">
                            <li><a href="${pageContext.request.contextPath}/orders.do?currentPage=1&orderId=${findOrder.orderId}&userId=${findOrder.userId}&orderStatu=${findOrder.orderStatu}">首页</a></li>
                            <li><a href="${pageContext.request.contextPath}/orders.do?currentPage=${page.currentPage + 1}&orderId=${findOrder.orderId}&userId=${findOrder.userId}&orderStatu=${findOrder.orderStatu}">下一页</a></li>
                            <li><a href="${pageContext.request.contextPath}/orders.do?currentPage=${page.totalPage}&orderId=${findOrder.orderId}&userId=${findOrder.userId}&orderStatu=${findOrder.orderStatu}">尾页</a></li>
                        </c:when>
                        <c:when test="${page.currentPage == page.totalPage}">
                            <li><a href="${pageContext.request.contextPath}/orders.do?currentPage=1&orderId=${findOrder.orderId}&userId=${findOrder.userId}&orderStatu=${findOrder.orderStatu}">首页</a></li>
                            <li><a href="${pageContext.request.contextPath}/orders.do?currentPage=${page.currentPage - 1}&orderId=${findOrder.orderId}&userId=${findOrder.userId}&orderStatu=${findOrder.orderStatu}">上一页</a></li>
                            <li><a href="${pageContext.request.contextPath}/orders.do?currentPage=${page.totalPage}&orderId=${findOrder.orderId}&userId=${findOrder.userId}&orderStatu=${findOrder.orderStatu}">尾页</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pageContext.request.contextPath}/orders.do?currentPage=1&orderId=${findOrder.orderId}&userId=${findOrder.userId}&orderStatu=${findOrder.orderStatu}">首页</a></li>
                            <li><a href="${pageContext.request.contextPath}/orders.do?currentPage=${page.currentPage - 1}&orderId=${findOrder.orderId}&userId=${findOrder.userId}&orderStatu=${findOrder.orderStatu}">上一页</a></li>
                            <li><a href="${pageContext.request.contextPath}/orders.do?currentPage=${page.currentPage + 1}&orderId=${findOrder.orderId}&userId=${findOrder.userId}&orderStatu=${findOrder.orderStatu}">下一页</a></li>
                            <li><a href="${pageContext.request.contextPath}/orders.do?currentPage=${page.totalPage}&orderId=${findOrder.orderId}&userId=${findOrder.userId}&orderStatu=${findOrder.orderStatu}">尾页</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </center>
        </c:if>
    </div><!-- /.main-content -->

    <div class="footer">
        <div class="footer-inner">
            <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">ECJTU</span>
							HuSen &copy; 2017-2018
						</span>
            </div>
        </div>
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->
<script src="/assets/js/jquery-2.1.4.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="/assets/js/jquery-1.11.3.min.js"></script>
<![endif]-->
<script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/js/my.js"></script>
<!-- page specific plugin scripts -->
<script src="/assets/js/jquery.dataTables.min.js"></script>
<script src="/assets/js/jquery.dataTables.bootstrap.min.js"></script>
<script src="/assets/js/dataTables.buttons.min.js"></script>
<script src="/assets/js/buttons.flash.min.js"></script>
<script src="/assets/js/buttons.html5.min.js"></script>
<script src="/assets/js/buttons.print.min.js"></script>
<script src="/assets/js/buttons.colVis.min.js"></script>
<script src="/assets/js/dataTables.select.min.js"></script>
<script src="/assets/js/moment.js"></script>
<script src="/assets/js/daterangepicker.js"></script>
<!-- ace scripts -->
<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>
<script src="/js/findOrder.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
    jQuery(function($) {
        //initiate dataTables plugin
        var myTable =
            $('#dynamic-table')
            //.wrap("<div class='dataTables_borderWrap' />")   //if you are applying horizontal scrolling (sScrollX)
                .DataTable( {
                    bAutoWidth: false,
                    "aoColumns": [
                        { "bSortable": false },
                        null, null,null, null, null,
                        { "bSortable": false }
                    ],
                    "aaSorting": [],


                    //"bProcessing": true,
                    //"bServerSide": true,
                    //"sAjaxSource": "http://127.0.0.1/table.php"	,

                    //,
                    //"sScrollY": "200px",
                    //"bPaginate": false,

                    //"sScrollX": "100%",
                    //"sScrollXInner": "120%",
                    //"bScrollCollapse": true,
                    //Note: if you are applying horizontal scrolling (sScrollX) on a ".table-bordered"
                    //you may want to wrap the table inside a "div.dataTables_borderWrap" element

                    //"iDisplayLength": 50


                    select: {
                        style: 'multi'
                    }
                } );



        $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

        new $.fn.dataTable.Buttons( myTable, {
            buttons: [
                {
                    "extend": "colvis",
                    "text": "<i class='fa fa-search bigger-110 blue'></i> <span class='hidden'>Show/hide columns</span>",
                    "className": "btn btn-white btn-primary btn-bold",
                    columns: ':not(:first):not(:last)'
                },
                {
                    "extend": "copy",
                    "text": "<i class='fa fa-copy bigger-110 pink'></i> <span class='hidden'>Copy to clipboard</span>",
                    "className": "btn btn-white btn-primary btn-bold"
                },
                {
                    "extend": "csv",
                    "text": "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>Export to CSV</span>",
                    "className": "btn btn-white btn-primary btn-bold"
                },
                {
                    "extend": "excel",
                    "text": "<i class='fa fa-file-excel-o bigger-110 green'></i> <span class='hidden'>Export to Excel</span>",
                    "className": "btn btn-white btn-primary btn-bold"
                },
                {
                    "extend": "pdf",
                    "text": "<i class='fa fa-file-pdf-o bigger-110 red'></i> <span class='hidden'>Export to PDF</span>",
                    "className": "btn btn-white btn-primary btn-bold"
                },
                {
                    "extend": "print",
                    "text": "<i class='fa fa-print bigger-110 grey'></i> <span class='hidden'>Print</span>",
                    "className": "btn btn-white btn-primary btn-bold",
                    autoPrint: false,
                    message: 'This print was produced using the Print button for DataTables'
                }
            ]
        } );
        myTable.buttons().container().appendTo( $('.tableTools-container') );

        //style the message box
        var defaultCopyAction = myTable.button(1).action();
        myTable.button(1).action(function (e, dt, button, config) {
            defaultCopyAction(e, dt, button, config);
            $('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
        });


        var defaultColvisAction = myTable.button(0).action();
        myTable.button(0).action(function (e, dt, button, config) {

            defaultColvisAction(e, dt, button, config);


            if($('.dt-button-collection > .dropdown-menu').length == 0) {
                $('.dt-button-collection')
                    .wrapInner('<ul class="dropdown-menu dropdown-light dropdown-caret dropdown-caret" />')
                    .find('a').attr('href', '#').wrap("<li />")
            }
            $('.dt-button-collection').appendTo('.tableTools-container .dt-buttons')
        });

        ////

        setTimeout(function() {
            $($('.tableTools-container')).find('a.dt-button').each(function() {
                var div = $(this).find(' > div').first();
                if(div.length == 1) div.tooltip({container: 'body', title: div.parent().text()});
                else $(this).tooltip({container: 'body', title: $(this).text()});
            });
        }, 500);





        myTable.on( 'select', function ( e, dt, type, index ) {
            if ( type === 'row' ) {
                $( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
            }
        } );
        myTable.on( 'deselect', function ( e, dt, type, index ) {
            if ( type === 'row' ) {
                $( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
            }
        } );




        /////////////////////////////////
        //table checkboxes
        $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

        //select/deselect all rows according to table header checkbox
        $('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#dynamic-table').find('tbody > tr').each(function(){
                var row = this;
                if(th_checked) myTable.row(row).select();
                else  myTable.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
            var row = $(this).closest('tr').get(0);
            if(this.checked) myTable.row(row).deselect();
            else myTable.row(row).select();
        });



        $(document).on('click', '#dynamic-table .dropdown-toggle', function(e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        });



        //And for the first simple table, which doesn't have TableTools or dataTables
        //select/deselect all rows according to table header checkbox
        var active_class = 'active';
        $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
            var th_checked = this.checked;//checkbox inside "TH" table header

            $(this).closest('table').find('tbody > tr').each(function(){
                var row = this;
                if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
                else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#simple-table').on('click', 'td input[type=checkbox]' , function(){
            var $row = $(this).closest('tr');
            if($row.is('.detail-row ')) return;
            if(this.checked) $row.addClass(active_class);
            else $row.removeClass(active_class);
        });



        /********************************/
        //add tooltip for small view action buttons in dropdown menu
        $('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});

        //tooltip placement on right or left
        function tooltip_placement(context, source) {
            var $source = $(source);
            var $parent = $source.closest('table')
            var off1 = $parent.offset();
            var w1 = $parent.width();

            var off2 = $source.offset();
            //var w2 = $source.width();

            if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
            return 'left';
        }
        /***************/
        $('.show-details-btn').on('click', function(e) {
            e.preventDefault();
            $(this).closest('tr').next().toggleClass('open');
            $(this).find(ace.vars['.icon']).toggleClass('fa-angle-double-down').toggleClass('fa-angle-double-up');
        });
    })
</script>
</body>
</html>

