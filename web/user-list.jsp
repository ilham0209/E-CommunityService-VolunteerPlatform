<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommunity.bean.User" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management - E-Community</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="includes/navbar.jsp" />
    
    <div class="container mt-5">
        <h1 class="mb-4">User Management (Admin Only)</h1>
        
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>User Type</th>
                                <th>Matric/IC</th>
                                <th>Phone</th>
                                <th>Admin</th>
                                <th>Registration Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                List<User> users = (List<User>) request.getAttribute("users");
                                if (users != null && !users.isEmpty()) {
                                    for (User u : users) {
                            %>
                            <tr>
                                <td><%= u.getUserId() %></td>
                                <td><strong><%= u.getFullName() %></strong></td>
                                <td><%= u.getEmail() %></td>
                                <td>
                                    <span class="badge <%= "STUDENT".equals(u.getUserType()) ? "bg-primary" : "bg-success" %>">
                                        <%= u.getUserType() %>
                                    </span>
                                </td>
                                <td>
                                    <%= u.getMatricNumber() != null ? u.getMatricNumber() : u.getIcNumber() %>
                                </td>
                                <td><%= u.getPhoneNumber() %></td>
                                <td>
                                    <% if (u.isAdmin()) { %>
                                        <span class="badge bg-danger">ADMIN</span>
                                    <% } else { %>
                                        <span class="badge bg-secondary">User</span>
                                    <% } %>
                                </td>
                                <td><%= u.getRegistrationDate() %></td>
                            </tr>
                            <% 
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="8" class="text-center">No users found</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <a href="dashboard" class="btn btn-secondary mt-3">Back to Dashboard</a>
    </div>
    
    <jsp:include page="includes/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>