<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommunity.dao.ParticipationDAO" %>
<%@ page import="com.ecommunity.bean.Participation" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Participations - E-Community</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="includes/navbar.jsp" />
    
    <div class="container mt-5">
        <h1 class="mb-4">My Participations</h1>
        
        <c:if test="${empty sessionScope.user}">
            <div class="alert alert-warning">
                Please <a href="login">login</a> to view your participations.
            </div>
        </c:if>
        
        <c:if test="${not empty sessionScope.user}">
            <% 
                Integer userId = (Integer) session.getAttribute("userId");
                if (userId != null) {
                    ParticipationDAO participationDAO = new ParticipationDAO();
                    List<Participation> participations = participationDAO.getParticipationsByUser(userId);
                    
                    if (participations != null && !participations.isEmpty()) {
            %>
            
            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>#</th>
                                    <th>Program Name</th>
                                    <th>Registration Date</th>
                                    <th>Status</th>
                                    <th>Hours Contributed</th>
                                    <th>Rating</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                    int rowNum = 1;
                                    for (Participation p : participations) {
                                %>
                                <tr>
                                    <td><%= rowNum++ %></td>
                                    <td><strong><%= p.getProgramName() %></strong></td>
                                    <td><%= p.getRegistrationDate() %></td>
                                    <td>
                                        <span class="badge 
                                            <% if ("REGISTERED".equals(p.getAttendanceStatus())) { %>bg-info
                                            <% } else if ("ATTENDED".equals(p.getAttendanceStatus())) { %>bg-success
                                            <% } else if ("ABSENT".equals(p.getAttendanceStatus())) { %>bg-warning
                                            <% } else { %>bg-danger<% } %>">
                                            <%= p.getAttendanceStatus() %>
                                        </span>
                                    </td>
                                    <td>
                                        <%= p.getHoursContributed() != null ? p.getHoursContributed() : "0.00" %> hrs
                                    </td>
                                    <td>
                                        <% if (p.getRating() != null) { 
                                            for (int i = 0; i < p.getRating(); i++) { %>⭐<% }
                                        } else { %>
                                            <span class="text-muted">Not rated</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <a href="program-detail.jsp?id=<%= p.getProgramId() %>" 
                                           class="btn btn-sm btn-primary">View</a>
                                        <form action="participation?action=cancel" method="POST" style="display: inline;">
                                            <input type="hidden" name="participationId" value="<%= p.getParticipationId() %>">
                                            <button type="submit" class="btn btn-sm btn-danger btn-delete">Cancel</button>
                                        </form>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="mt-3">
                        <h5>Summary</h5>
                        <p><strong>Total Programs Joined:</strong> <%= participations.size() %></p>
                        <p><strong>Total Hours Contributed:</strong> 
                            <%= participationDAO.getTotalHoursByUser(userId) %> hours
                        </p>
                    </div>
                </div>
            </div>
            
            <% 
                    } else {
            %>
                <div class="alert alert-info">
                    <h4>No participations yet!</h4>
                    <p>You haven't joined any programs. <a href="program-list.jsp">Browse programs</a> to get started.</p>
                </div>
            <% 
                    }
                }
            %>
        </c:if>
    </div>
    
    <jsp:include page="includes/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>