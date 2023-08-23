<a href="${baseUrl}/admin/dashboard" class="brand-color logo">
    <!-- mini logo for sidebar mini 50x50 pixels -->
    <span class="logo-mini">
                <img
                        src="${baseUrl}/resources/dist/img/edoc.png" width="50" height="50"
                        alt="E doc Logo 1"></span>
    <!-- logo for regular state and mobile devices -->
    <span class="logo-lg"> <img src="${baseUrl}/resources/dist/img/edoc.png"
                                height="50" alt="E doc Logo">
			</span>
</a>

<nav class="navbar navbar-static-top">
    <!-- Sidebar toggle button-->
    <a href="#" class="sidebar-toggle" data-toggle="push-menu"
       role="button"> <span class="sr-only">Toggle navigation</span> <span
            class="icon-bar"></span> <span class="icon-bar"></span> <span
            class="icon-bar"></span>
    </a>


    <ul class="nav navbar-nav hidden-xs">

        <li class="dropdown">
            <a href="#" class="dropdown-toggle"
               data-toggle="dropdown">Patient</a>
            <ul class="dropdown-menu">

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown">Patient Management</a>
                    <ul class="dropdown-menu">

                        <li><a
                                href="${baseUrl}/patient/">View
                            Patient List</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle"
               data-toggle="dropdown">Doctor</a>
            <ul class="dropdown-menu">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown">Doctor Management</a>
                    <ul class="dropdown-menu">

                        <li><a href="${baseUrl}/doctor/">View Doctor</a>
                        </li>
                        <li><a href="${baseUrl}/doctor/hospital/">View Doctor's Affiliated Hospital</a>
                        </li>
                        <li><a href="${baseUrl}/doctor/qualification/">View Doctor's Qualification</a>
                        </li>
                        <li><a href="${baseUrl}/specialization/">Specialization List</a>
                        </li>
                        <li><a href="${baseUrl}/specialization/doctor/"> Doctor's Specialization List</a>
                        </li>
                    </ul>
                </li>

            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle"
               data-toggle="dropdown">Treatment</a>
            <ul class="dropdown-menu">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown"> Treatment</a>
                    <ul class="dropdown-menu">
                        <li><a href="${baseUrl}/treatment/add">Add Treatment</a>
                        </li>
                        <li><a href="${baseUrl}/treatment/">View Treatment</a>
                        </li>
                    </ul>

                </li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle"
               data-toggle="dropdown">Diagnosis</a>
            <ul class="dropdown-menu">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown"> Diagnosis</a>
                    <ul class="dropdown-menu">
                        <li><a href="${baseUrl}/diagnosis/add">Add Diagnosis</a>
                        </li>
                        <li><a href="${baseUrl}/diagnosis/">View Diagnosis</a>
                        </li>
                    </ul>

                </li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle"
               data-toggle="dropdown">Appointment</a>
            <ul class="dropdown-menu">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown"> Appointment</a>
                    <ul class="dropdown-menu">
                        <li><a href="${baseUrl}/appointment/add">Add Appointment</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>


    </ul>

    <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
            <li class="dropdown user-menu">
                <c:set var="profileImage" value="${baseUrl}/resources/dist/img/default.png"/>
                <c:if test="${employee.image != null}">
                    <c:set var="profileImage" value="${baseUrl}/files/${employee.image}"/>
                </c:if>
                <a class="dropdown-toggle profile-pic" href="${profileImage}" data-toggle="dropdown">
                    <img src="${profileImage}" class="user-image" alt="e Doc">

                </a>
                <ul class="dropdown-menu">
                    <li class="header">
                        <a class="profile-pic" href="${baseUrl}/admin/home">
                            <img src="${baseUrl}/resources/dist/img/default.png" class="user-image" alt="e Doc">
                        </a>
                        <div>e Doc</div>
                    </li>

                    <li><a href="#"><i class="material-icons">perm_identity</i> Profile</a></li>
                    <%-- <li><a href="#"><i class="material-icons">emoji_transportation</i>Maintain Company Profile</a></li>
                     <li><a href="${baseUrl}/settings/"><i class="material-icons">build</i>Settings</a></li>--%>
                    <li><a href="${baseUrl}/admin/logout"><i class="material-icons">power_settings_new</i> Log
                        Out</a></li>
                </ul>
            </li>
        </ul>
    </div>

</nav>
