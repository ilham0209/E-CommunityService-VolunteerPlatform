<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommunity.bean.ActivityLog" %>
<%@ page import="com.ecommunity.dao.UserDAO" %>
<%@ page import="com.ecommunity.bean.User" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Activity Log - E-Community</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="includes/navbar.jsp" />
    
    <div class="container mt-5">
        <h1 class="mb-4">Activity Log (Admin Only)</h1>
        <p class="text-muted">System activity tracking - Login, Logout, and other actions</p>
        
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>User</th>
                                <th>Action</th>
                                <th>Description</th>
                                <th>IP Address</th>
                                <th>Date & Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                List<ActivityLog> logs = (List<ActivityLog>) request.getAttribute("logs");
                                UserDAO userDAO = new UserDAO();
                                
                                if (logs != null && !logs.isEmpty()) {
                                    for (ActivityLog log : logs) {
                                        String userName = "Unknown";
                                        if (log.getUserId() != null) {
                                            User user = userDAO.getUserById(log.getUserId());
                                            if (user != null) {
                                                userName = user.getFullName();
                                            }
                                        }
                            %>
                            <tr>
                                <td><%= log.getLogId() %></td>
                                <td><strong><%= userName %></strong></td>
                                <td>
                                    <% 
                                        String badgeClass = "bg-secondary";
                                        if ("LOGIN".equals(log.getActionType())) {
                                            badgeClass = "bg-success";
                                        } else if ("LOGOUT".equals(log.getActionType())) {
                                            badgeClass = "bg-warning";
                                        } else if ("CREATE_PROGRAM".equals(log.getActionType())) {
                                            badgeClass = "bg-primary";
                                        } else if ("DELETE".equals(log.getActionType())) {
                                            badgeClass = "bg-danger";
                                        }
                                    %>
                                    <span class="badge <%= badgeClass %>"><%= log.getActionType() %></span>
                                </td>
                                <td><%= log.getDescription() != null ? log.getDescription() : "-" %></td>
                                <td><%= log.getIpAddress() != null ? log.getIpAddress() : "-" %></td>
                                <td><%= log.getCreatedAt() %></td>
                            </tr>
                            <% 
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="6" class="text-center">No activity logs found</td>
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