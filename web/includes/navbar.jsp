<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">
            <strong>E-Community</strong>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <!-- Only show these links when user is logged in -->
                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item">
                        <a class="nav-link" href="dashboard">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="program-list.jsp">Programs</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="my-participations.jsp">My Participations</a>
                    </li>
                    <!-- Only show User List for admin -->
                    <c:if test="${sessionScope.isAdmin == true}">
                        <li class="nav-item">
                            <a class="nav-link" href="user-list">User List</a>
                        </li>
                    </c:if>
                </c:if>
            </ul>
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <!-- User is logged in - show profile and logout -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" 
                               data-bs-toggle="dropdown">
                                ${sessionScope.userName}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li><a class="dropdown-item" href="profile.jsp">Profile</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="logout">Logout</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <!-- User is NOT logged in - show login and register -->
                        <li class="nav-item">
                            <a class="nav-link" href="login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="register">Register</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>