<%--
  Created by IntelliJ IDEA.
  User: 11785
  Date: 2018/1/30
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" language="java" import="java.util.*"  contentType="text/html; charset=utf-8"%>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/assets/css/ace-ie.min.css" />
    <![endif]-->
    <link rel="stylesheet" href="/css/my2.css" />
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
    .ltt{
        font-size: 36px;
        color: brown;
    }
    .husen{
        font-size: 26px;
    }
    td{
        vertical-align: middle;
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
                    <li class="active">商品管理</li>
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
                        商品管理
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                            商品详细
                        </small>
                    </h1>
                </div><!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="col-xs-1"></div>
                        <div id="detail" class="col-xs-10">
                            <input type="hidden" name="goodId" value="${good.good.goodId}"/>
                            <table class="table table-bordered">
                                <tr>
                                    <td colspan="4" style="text-align: center;">
                                        <button id="showGoodInfo" class="btn btn-primary">修改商品信息</button>
                                        <button id="showDesPic" class="btn btn-purple">编辑详情图片</button>
                                        <button id="showOtherPics" class="btn btn-info">编辑其它图片</button>
                                        
                                        <c:if test="${good.good.isShelves == 'down'}">
                                            <button class="btn btn-pink" id="upGood">商品上架</button>
                                        </c:if>

                                        <c:if test="${good.good.isShelves == 'up'}">
                                            <button class="btn btn-pink" id="downGood">商品下架</button>
                                        </c:if>
                                        <button class="btn btn-yellow">商品收入</button>
                                        <button class="btn btn-inverse">查看商品评论</button>
                                        <button class="btn btn-grey">查看商品印象</button>
                                        <button id="showCoupon" class="btn btn-pink">优惠券管理</button>
                                        <button id="showPromotions" class="btn btn-info">折扣管理</button>
                                        <button id="showPostage" class="btn btn-inverse">邮费管理</button>
                                    </td>
                                </tr>
                                <tr style="text-align: center;">
                                    <td style="width: 10%;">商品编号：</td>
                                    <td style="width: 40%;">${good.good.goodId}</td>
                                    <td style="width: 10%;">商品名称：</td>
                                    <td style="width: 40%;">${good.good.goodName}</td>
                                </tr>
                                <tr style="text-align: center;">
                                    <td style="width: 10%;">原价：</td>
                                    <td style="width: 40%;">${good.good.originalPrice}元</td>
                                    <td style="width: 10%;">促销价：</td>
                                    <td style="width: 40%;">${good.good.salePrice}元</td>
                                </tr>
                                <tr style="text-align: center;">
                                    <td style="width: 10%;">材质：</td>
                                    <td style="width: 40%;">${good.item.material.materialName}</td>
                                    <td style="width: 10%;">规格：</td>
                                    <td style="width: 40%;">${good.item.specification.specificationName}</td>
                                </tr>
                                <tr style="text-align: center;">
                                    <td style="width: 10%;">物品当前库存：</td>
                                    <td style="width: 40%;">${good.item.currentInventory}</td>
                                    <td style="width: 10%;">物品上架时间</td>
                                    <td style="width: 40%;"><fmt:formatDate value="${good.item.timeToMarket}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>
                                </tr>
                                <tr style="text-align: center;">
                                    <td style="width: 10%;">物品品牌：</td>
                                    <td style="width: 40%;">${good.item.brand.brandName}</td>
                                    <td style="width: 10%;">适用者</td>
                                    <td style="width: 40%;">${good.item.applyer.applyerName}</td>
                                </tr>
                                <tr style="text-align: center;">
                                    <td style="width: 10%;">建议零售价：</td>
                                    <td style="width: 40%;">${good.item.suggestRetailPrice}元</td>
                                    <td style="width: 10%;">分类：</td>
                                    <td style="width: 40%;">${good.item.sportItem.sportItemName}</td>
                                </tr>
                                <tr style="text-align: center;">
                                    <td style="width: 10%;">物品图片：</td>
                                    <td colspan="3">
                                        <img style="width: 160px;" src="${good.item.picPath}"/>
                                    </td>
                                </tr>
                                <tr style="text-align: center;">
                                    <td style="width: 10%;">商品详情图片：</td>
                                    <td colspan="3">
                                        <img style="width: 100%;" src="${good.good.detailpic}"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="col-xs-1"></div>
                    </div>
                </div>
                <div id="postage" style="text-align: center;">
                    <h3>邮费管理</h3>
                    <h4 id="isPostage"></h4>
                    <table class="table table-bordered">
                        <tr>
                            <td style="width: 40%; text-align: center;">
                                是否包邮
                            </td>
                            <td style="text-align: center;">
                                <select name="isPostage" class="form-control">
                                    <option value="havePostage">不包邮</option>
                                    <option value="noPostage">包邮</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 40%; text-align: center;">
                                邮费多少
                            </td>
                            <td style="text-align: center;">
                                <input name="postageMoney" type="number" value="0" class="form-control"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center;">
                                <button id="updatePostage" class="btn btn-info">修改</button>
                                <button id="closePostage" class="btn btn-danger">关闭</button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="promotions">
                    <h3>已有的折扣</h3>
                    <table class="table table-bordered">
                        <%-- 折扣券名称等于（满 + 需达到的数量 + 打 + 折扣） --%>
                        <th style="text-align: center; width: 10%;">折扣名称</th>
                        <th style="text-align: center; width: 10%;">需达到的数量</th>
                        <th style="text-align: center; width: 10%;">所打的折扣</th>
                        <th style="text-align: center; width: 10%;">所属的商品</th>
                        <th style="text-align: center; width: 10%;">操作</th>
                        <tbody id="promotionss">

                        </tbody>
                    </table>
                    <table class="table table-bordered">
                        <tr>
                            <td>
                                <input type="text" class="form-control" name="number" placeholder="需要达到的数量"/>
                            </td>
                            <td>
                                <input type="text" class="form-control" name="discount" placeholder="折扣"/>
                            </td>
                            <td style="text-align: center;">
                                <button id="savePromotion" class="btn btn-pink">新增</button>
                                <button id="closePromotions" class="btn btn-danger">关闭</button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="coupons" style="text-align: center;">
                    <h3>已有的优惠券</h3>
                    <table class="table table-bordered">
                        <tr>
                            <%-- 优惠券名称等于 （满 + 要求金额 + 免 + 免的金额） --%>
                            <th style="width: 15%; text-align: center;">优惠券名称</th>
                            <th style="width: 10%; text-align: center;">所需金额</th>
                            <th style="width: 10%; text-align: center;">免多少钱</th>
                            <th style="width: 40%; text-align: center;">所属商品</th>
                            <th style="width: 25%; text-align: center;">过期时间</th>
                            <th style="width: 10%; text-align: center;">操作</th>
                        </tr>
                        <tbody id="couponss">

                        </tbody>
                    </table>
                    <table class="table table-bordered">
                        <tr>
                            <td>
                                <input type="text" class="form-control" name="need" placeholder="需达到金额"/>
                            </td>
                            <td>
                                <input type="text" class="form-control" name="free" placeholder="免多少钱"/>
                            </td>
                            <td>
                                <input type="number" class="form-control" name="expiryTimeLong" placeholder="优惠券时长/天"/>
                            </td>
                            <td style="text-align: center;">
                                <button id="saveCoupon" class="btn btn-pink">新增</button>
                                <button id="closeCoupons" class="btn btn-danger">关闭</button>
                            </td>
                        </tr>
                    </table>
                </div>

                <div id="otherPics" style="text-align: center;">
                    <h3>其他图片</h3>
                    <table class="table table-bordered" id="pics">
                    </table>
                    <form id="newOtherPic" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="goodId" value="${good.good.goodId}"/>
                        <table class="table table-bordered">
                            <tr>
                                <td style="text-align: right; width:26%;">
                                    新增图片
                                </td>
                                <td>
                                    <input name="pic" type="file"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <button id="saveOtherPic" class="btn btn-primary">保存</button>
                    <button id="closeOtherPics" class="btn btn-danger">关闭</button>
                </div>

                <div id="desPic" style="text-align: center;">
                    <h3>修改商品详情图片</h3>
                    <form id="newDesPic" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="goodId" value="${good.good.goodId}"/>
                        <table class="table table-bordered">
                            <tr>
                                <td style="text-align: right; width:26%;">
                                    请选择图片
                                </td>
                                <td>
                                    <input name="pic" type="file"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <button id="saveDesPic" class="btn btn-primary">保存</button>
                    <button id="closeDesPic" class="btn btn-danger">关闭</button>
                </div>

                <div id="goodInfo" style="text-align: center;">
                    <h3>修改商品信息</h3>
                    <table class="table table-bordered">
                        <tr>
                            <td style="width: 26%; text-align: right;">商品名称：</td>
                            <td>
                                <input type="text" class="form-control" name="newGoodName"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 26%; text-align: right;">原价：</td>
                            <td>
                                <input type="text" class="form-control" name="newOriginalPrice"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 26%; text-align: right;">促销价：</td>
                            <td>
                                <input type="text" class="form-control" name="newSalePrice"/>
                            </td>
                        </tr>
                    </table>
                    <button id="saveGoodInfo" class="btn btn-primary">保存</button>
                    <button id="closeGoodInfo" class="btn btn-danger">关闭</button>
                </div>
            </div><!-- /.page-content -->
        </div>
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
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>

<!-- page specific plugin scripts -->
<script src="${pageContext.request.contextPath}/assets/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery.dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/dataTables.buttons.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/buttons.flash.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/buttons.html5.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/buttons.print.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/buttons.colVis.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/dataTables.select.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/moment.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/daterangepicker.js"></script>
<!-- ace scripts -->
<script src="${pageContext.request.contextPath}/assets/js/ace-elements.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/ace.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/registStores.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/js/detail.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
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
                    //"sScrollY": "10%",
                    //"bPaginate": false,

                    //"sScrollX": "100%",
                    //"sScrollXInner": "110%",
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

    function close2(){
        $("#add").css("display","none");
    }
    function add(){
        $("#add").css("display","block");
    }

    $("input[name='timeToMarket']").datetimepicker({
        /*
        * [0,1,2,3,4,5,6]分别表示[星期日，周一，周二，周三，周四，周五，周六]
        * */
        //format : 'yyyy-mm-dd HH:mm:ss',//格式
        format : 'yyyy-mm-dd',
        weekStart : 1,//一周从哪天开始
        //startDate : '2018-01-11 10:03:56',//只能选择开始时间之后的时间
        //endDate : '2018-01-22 10:03:56',//只能选择结束时间之前的时间
        //daysOfWeekDisabled : [6, 0],//一周的周几不能选
        autoclose : true,//选完时间后是否自动关闭
        startView : 2,//日期选择器打开后首先选择的｛分、小时、日、月、年｝
        minView : 2,//最精确的时间｛分、小时、日、月、年｝
        todayBtn : true,//当天日期按钮
        todayHighlight : true,//当天日期高亮
        keyboardNavigation : true,//方向键改变日期
        language : 'zh-CN',//语言
        forceParse : true,//强制解析，就是你输入的可能不正规，但是它会强制尽量解析成你规定的格式
        minuteStep : 5,//步进值，此数值被当作步进值用于构建小时视图。就是最小的视图每5分钟可选一次。是以分钟为单位的。
        pickerPostion : 'top-right',//选择框位置
        showMeridian : true,//是否显示上下午
        initialDate : new Date()//初始化日期时间，默认值new Date()
    });
</script>
</body>
</html>

