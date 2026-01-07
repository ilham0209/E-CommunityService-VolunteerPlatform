<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - E-Community Platform</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
    <!-- Navigation Bar -->
    <jsp:include page="includes/navbar.jsp" />
    
    <div class="container mt-5">
        <!-- Welcome Section -->
        <div class="row mb-4">
            <div class="col-12">
                <h1>Dashboard</h1>
                <p class="lead">Welcome back, <strong>${sessionScope.userName}</strong>!</p>
            </div>
        </div>
        
        <!-- Statistics Cards -->
        <div class="row g-4 mb-5">
            <!-- Total Users -->
            <div class="col-md-3">
                <div class="card text-white bg-primary h-100">
                    <div class="card-body">
                        <h5 class="card-title">Total Users</h5>
                        <h2 class="display-4">${totalUsers}</h2>
                        <p class="card-text">Registered members</p>
                    </div>
                </div>
            </div>
            
            <!-- Total Programs -->
            <div class="col-md-3">
                <div class="card text-white bg-success h-100">
                    <div class="card-body">
                        <h5 class="card-title">Total Programs</h5>
                        <h2 class="display-4">${totalPrograms}</h2>
                        <p class="card-text">Available programs</p>
                    </div>
                </div>
            </div>
            
            <!-- My Participations -->
            <div class="col-md-3">
                <div class="card text-white bg-info h-100">
                    <div class="card-body">
                        <h5 class="card-title">My Participations</h5>
                        <h2 class="display-4">${myParticipations}</h2>
                        <p class="card-text">Programs joined</p>
                    </div>
                </div>
            </div>
            
            <!-- Volunteer Hours -->
            <div class="col-md-3">
                <div class="card text-white bg-warning h-100">
                    <div class="card-body">
                        <h5 class="card-title">My Total Hours</h5>
                        <h2 class="display-4">${myTotalHours}</h2>
                        <p class="card-text">Hours contributed</p>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Recent Programs -->
<!--        <div class="row">
            <div class="col-12">
                <h3 class="mb-3">Recent Programs</h3>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Program Name</th>
                                <th>Category</th>
                                <th>Location</th>
                                <th>Start Date</th>
                                <th>Status</th>
                                <th>Participants</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="program" items="${recentPrograms}">
                                <tr>
                                    <td>${program.programName}</td>
                                    <td><span class="badge bg-secondary">${program.category}</span></td>
                                    <td>${program.location}</td>
                                    <td>${program.startDate}</td>
                                    <td>
                                        <span class="badge 
                                            <c:choose>
                                                <c:when test="${program.status == 'UPCOMING'}">bg-info</c:when>
                                                <c:when test="${program.status == 'ONGOING'}">bg-success</c:when>
                                                <c:when test="${program.status == 'COMPLETED'}">bg-secondary</c:when>
                                                <c:otherwise>bg-danger</c:otherwise>
                                            </c:choose>">
                                            ${program.status}
                                        </span>
                                    </td>
                                    <td>${program.currentParticipants} / ${program.maxParticipants}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>-->
        
        <!-- Quick Actions -->
        <div class="row mt-4">
            <div class="col-12">
                <h4>Quick Actions</h4>
                <a href="program-list.jsp" class="btn btn-primary me-2">Browse Programs</a>
                <a href="my-participations.jsp" class="btn btn-info me-2">My Participations</a>
                <a href="logout" class="btn btn-danger">Logout</a>
            </div>
        </div>
    </div>
    
    <!-- Footer -->
    <jsp:include page="includes/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>