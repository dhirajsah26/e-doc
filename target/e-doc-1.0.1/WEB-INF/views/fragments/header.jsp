
<%@ page contentType="text/html; charset=UTF-8" %>

<%--<%@ page import="StatusTypes" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="dateUtils" class="com.example.edoc.util.dateutil.DateUtils"></jsp:useBean>
<jsp:useBean id="helper" class="com.example.edoc.util.Helper"></jsp:useBean>


<c:set var="operationDateSetting" value="${sessionScope.operation_date_setting}" scope="application"/>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" scope="application"/>

<!DOCTYPE html>
<html>
<head>
    <!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <noscript>
        <meta http-equiv="refresh" content="0; url='/sms-web/javascript/blocked'"/>
    </noscript>

    <title>E doc </title>

    <link rel="shortcut icon" href="${baseUrl}/resources/" type="image/x-icon">
    <!-- Tell the browser to be responsive to screen width -->

    <meta
            content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
            name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${baseUrl}/resources/bower_components/bootstrap/dist/css/bootstrap.min.css">

    <!-- ionicons -->
    <link rel="stylesheet" href="${baseUrl}/resources/bower_components/Ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="${baseUrl}/resources/bower_components/Ionicons/css/ionicons.css">
    <link rel="stylesheet" href="${baseUrl}/resources/dist/css/skins/_all-skins.min.css">

    <!-- Google Material icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="${baseUrl}/resources/bower_components/font-awesome/css/font-awesome.min.css">
    <!-- Google Font -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
    <!-- Rubik Font -->
    <link href="https://fonts.googleapis.com/css?family=Rubik:400,500"
          rel="stylesheet">
    <!-- Theme style -->
    <link rel="stylesheet"
          href="${baseUrl}/resources/dist/css/AdminLTE.min.css">
    <!-- jQuery 3 -->
    <script src="${baseUrl}/resources/bower_components/jquery/dist/jquery.min.js"></script>


    <!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/locale/af.js"></script>  -->

    <!--  validation  -->
    <!-- <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script> -->
    <!-- <script type="text/javascript" src="plugins/validation/dist/jquery.validate.min.js"></script> -->

    <!--  validation-->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>


    <!-- select 2 -->
    <link href="${baseUrl}/resources/plugins/select2/dist/css/select2.min.css" rel="stylesheet"/>
    <script src="${baseUrl}/resources/plugins/select2/dist/js/select2.min.js"></script>
    <%-- <script src="${baseUrl}/resources/dist/js/pages/moment.js"></script> --%>
    <!-- iCheck 1.0.1 -->
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/iCheck/all.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${baseUrl}/resources/dist/css/style.css">

    <style>
        .user-menu li.header:before {
            border-bottom-color: #007FFF !important
        }

        s.brand-color, .dropdown-menu .header {
            background: #007FFF !important
        }

        /*th, td { white-space: nowrap; }
              .DTFC_LeftBodyWrapper table{border-right-color: #ddd !important}
              .DTFC_RightBodyWrapper table{border-left-color: #ddd !important}*/

        .DTFC_ScrollWrapper .dataTable {
            margin-top: 0 !important;
            margin-bottom: 0 !important
        }

        .DTFC_LeftHeadWrapper table.DTFC_Cloned, .DTFC_RightHeadWrapper table.DTFC_Cloned {
            border-top: none;
            border-bottom: none;
        }

        .DTFC_LeftHeadWrapper table {
            border-right-color: #88a4d2 !important
        }

        .DTFC_RightHeadWrapper table {
            border-left-color: #88a4d2 !important
        }

        .DTFC_LeftBodyWrapper tr, .DTFC_RightBodyWrapper tr {
            background: #fff
        }

        .DTFC_RightBodyWrapper, .DTFC_RightBodyLiner {
            overflow: initial !important;
            width: auto !important
        }

        .info-item {
            display: flex !important;
            align-items: center !important;
        }

        .lab {
            width: 20rem !important;
            text-align: right;
            padding: 5px 0;
            margin-right: 15px;
        }

        .lab-radio {
            width: 5rem !important;
        }

        .inp {
            width: 25rem;
        }

        #myTable_filter {
            text-align: right !important;
        }

        #myTable_filter input {
            margin-left: 15px !important;
        }

        #myTable_length select {
            margin: 0 15px !important;
        }

        .dataTables_paginate.paging_simple_numbers {
            float: right !important;
        }

        .pagination {
            margin: 0px !important;
        }


    </style>

</head>
<body class="sidebar-mini sidebar-collapse">

<!-- Loading on Dashboard -->
<script>
    /*$('body').append("<div id='overlay'><img src='${baseUrl}/resources/dist/img/loader.gif' width='70' height='70' alt='' /><br>Loading...</div>").css('overflow','hidden');
    $(window).on('load', function () {
        $('#overlay').fadeOut();
        $('body').css('overflow','auto');
    });*/

    //radio and checkbox
    $(document).ready(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });

    var operationDateSetting = "${operationDateSetting}";
</script>

<!-- Site wrapper -->
<div class="">
    <header class="main-header">

        <!-- <div class="sidebar-toggle-mobile"></div> -->
        <!-- Logo -->

        <!-- <div class="navbar-custom-menu-mobile"></div> -->
        <!-- Header Navbar: style can be found in header.less -->


        <c:choose>
            <c:when test="${Heading == 'patient'}">
                <%@include file="patientNavbar.jsp" %>


            </c:when>
            <c:when test="${Heading == 'doctor'}">
                <%@include file="doctorNavbar.jsp" %>
            </c:when>

            <c:otherwise>
                <%@include file="navbar.jsp" %>
            </c:otherwise>
        </c:choose>



    </header>
    <!-- =============================================== -->
    <!-- Left side column. contains the sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <div class="user-panel input-group">
                <div class="pull-left image">
                    <img src="${baseUrl}/resources/dist/img/default.png" class="img-circle" alt="User Image">
                </div>
                <div class="pull-left info">
                    <p>
                        <c:choose>
                            <c:when test="${Heading == 'patient'}">
                                ${patient_name}
                            </c:when>
                            <c:when test="${Heading == 'doctor'}">
                                ${doctor_name}
                            </c:when>
                            <c:otherwise>
                                ${admin_name}
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>
            <%--          <li><a class="" href="#">
                          <c:set var="profileImage" value="${baseUrl}/resources/dist/img/default.png" />
                          <c:if test="${employee.image != null}">
                              <c:set var="profileImage" value="${baseUrl}/files/${employee.image}" />
                          </c:if>
                          <a class="dropdown-toggle profile-pic" href="${profileImage}" data-toggle="dropdown">
                              <img src="${profileImage}" class="user-image" alt="e Doc">
                              &lt;%&ndash; ${fn:split(admin_name, " ")[0]}  &ndash;%&gt;  ${admin_name}
                          </a>
                      </li>--%>

            <form action="#" method="get" class="sidebar-form">
                <div class="input-group">
						<span class="input-group-btn">
							<div type="button" class="btn btn-flat">
								<i class="fa fa-search"></i>
							</div>
						</span> <input type="text" id="menuSearch" class="form-control"
                                       placeholder="Search...">
                </div>
            </form>
           </section>
        <!-- /.sidebar -->
    </aside>

</div>

<%--
<script>
    $(function () {
        loadNavigation();
    })

    function loadNavigation() {
        console.log("HELLO")

        $(".removable").remove();
        var loaderElement = "<li class='removable' style='text-align:center;'><img src='${baseUrl}/resources/images/loader.gif' width='20' height='20' alt='loader'></li>";
        $(".sidebar-menu").append(loaderElement);
        $.post('${baseUrl}' + '/navigation/fetch/v2', function (response) {
            var nav = "";
            if (response.success) {
                //console.log(response);
                $.each(response.body, function (index, item) {
                    if (!item.subModules) {
                        nav += "<li class=\"menu-highlight\"><a href='" + (shortBaseUrl + item.targetUrl) + "'><i class=\"material-icons\">" + item.icon + "</i> <span class=\"moduleName\">" + item.name + "</span></a></li>";
                    } else {
                        nav += "<li class=\"treeview removable\">" +
                            "<a href=\"#\">" +
                            "<i class=\"material-icons\">" + item.icon + "</i> <span class=\"moduleName\">" + item.name + " </span> <span class=\"pull-right-container\"> <i class=\"fa fa-angle-left pull-right\"></i></span>" +
                            "</a>" +
                            "<ul class=\"treeview-menu\">";

                        $.each(item.subModules, function (index, item) {
                            if (item.subModules) {
                                nav += "<li class=\"treeview\"><a href=\"#\"><i class=\"material-icons\">" + item.icon + "</i><span class=\"moduleName\">" + item.name + "</span><span class=\"pull-right-container\"> <i class=\"fa fa-angle-left pull-right\"></i> </span> </a>" +
                                    "<ul class=\"treeview-menu\">";

                                $.each(item.subModules, function (index, item) {
                                    nav += "<li><a href='" + (shortBaseUrl + item.targetUrl) + "'><i class=\"material-icons\">" + item.icon + "</i> <span class=\"moduleName\">" + item.name + "</span></a></li>";
                                });

                                nav += "</ul>" +
                                    "</li>";
                            } else {
                                nav += "<li class=\"menu-highlight\"><a href='" + (shortBaseUrl + item.targetUrl) + "'><i class=\"material-icons\">" + item.icon + "</i> <span class=\"moduleName\">" + item.name + "</span></a></li>";
                            }
                        });


                        nav += "</ul>" +
                            "</li>";
                    }
                });
            }

            $(".removable").remove();
            $(".sidebar-menu").append(nav);
            $("a[href='" + requestPath + "']").parent("li").addClass("active current");
            $(".sidebar-menu li.active").closest(".treeview").addClass("active menu-open");
            $(".sidebar-menu li.active").parents(".treeview").addClass("active menu-open");

            menuFix();
        });
    }
</script>--%>
