<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommunity.bean.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - E-Community</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="includes/navbar.jsp" />
    
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h3>My Profile</h3>
                    </div>
                    <div class="card-body">
                        <% 
                            User user = (User) session.getAttribute("user");
                            if (user != null) {
                        %>
                        
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>User ID:</strong></div>
                            <div class="col-md-8"><%= user.getUserId() %></div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>Full Name:</strong></div>
                            <div class="col-md-8"><%= user.getFullName() %></div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>Email:</strong></div>
                            <div class="col-md-8"><%= user.getEmail() %></div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>User Type:</strong></div>
                            <div class="col-md-8">
                                <span class="badge <%= "STUDENT".equals(user.getUserType()) ? "bg-primary" : "bg-success" %>">
                                    <%= user.getUserType() %>
                                </span>
                            </div>
                        </div>
                        
                        <% if ("STUDENT".equals(user.getUserType())) { %>
                            <div class="row mb-3">
                                <div class="col-md-4"><strong>Matric Number:</strong></div>
                                <div class="col-md-8"><%= user.getMatricNumber() %></div>
                            </div>
                        <% } else { %>
                            <div class="row mb-3">
                                <div class="col-md-4"><strong>IC Number:</strong></div>
                                <div class="col-md-8"><%= user.getIcNumber() %></div>
                            </div>
                        <% } %>
                        
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>Phone Number:</strong></div>
                            <div class="col-md-8"><%= user.getPhoneNumber() != null ? user.getPhoneNumber() : "Not provided" %></div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>Account Type:</strong></div>
                            <div class="col-md-8">
                                <% if (user.isAdmin()) { %>
                                    <span class="badge bg-danger">ADMINISTRATOR</span>
                                <% } else { %>
                                    <span class="badge bg-secondary">Regular User</span>
                                <% } %>
                            </div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>Member Since:</strong></div>
                            <div class="col-md-8"><%= user.getRegistrationDate() %></div>
                        </div>
                        
                        <hr>
                        
                        <div class="d-grid gap-2">
                            <a href="dashboard" class="btn btn-primary">Back to Dashboard</a>
                            <a href="my-participations.jsp" class="btn btn-info">View My Participations</a>
                        </div>
                        
                        <% } else { %>
                            <div class="alert alert-danger">
                                User session not found. Please <a href="login">login</a> again.
                            </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="includes/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>