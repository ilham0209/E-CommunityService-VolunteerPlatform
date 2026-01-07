<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommunity.dao.VolunteerProgramDAO" %>
<%@ page import="com.ecommunity.bean.VolunteerProgram" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Programs - E-Community</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="includes/navbar.jsp" />
    
    <div class="container mt-5">
        <div class="row mb-4">
            <div class="col-md-8">
                <h1>Volunteer Programs</h1>
                <p class="text-muted">Browse and join community service programs</p>
            </div>
            <div class="col-md-4 text-end">
                <c:if test="${sessionScope.isAdmin}">
                    <a href="program?action=create" class="btn btn-primary">Create New Program</a>
                </c:if>
            </div>
        </div>
        
        <!-- Success/Error Messages -->
        <% if (request.getParameter("success") != null) { %>
            <div class="alert alert-success alert-dismissible fade show">
                <% String success = request.getParameter("success");
                   if ("created".equals(success)) { %>
                       Program created successfully!
                <% } else if ("updated".equals(success)) { %>
                       Program updated successfully!
                <% } else if ("deleted".equals(success)) { %>
                       Program deleted successfully!
                <% } %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>
        
        <!-- Programs List -->
        <div class="row g-4">
            <% 
                VolunteerProgramDAO programDAO = new VolunteerProgramDAO();
                List<VolunteerProgram> programs = programDAO.getAllPrograms();
                
                if (programs != null && !programs.isEmpty()) {
                    for (VolunteerProgram program : programs) {
            %>
                <div class="col-md-6 col-lg-4">
                    <div class="card program-card h-100">
                        <div class="card-header">
                            <%= program.getProgramName() %>
                        </div>
                        <div class="card-body">
                            <p class="card-text"><%= program.getDescription().substring(0, Math.min(100, program.getDescription().length())) %>...</p>
                            <p class="mb-2"><strong>Category:</strong> <span class="badge bg-secondary"><%= program.getCategory() %></span></p>
                            <p class="mb-2"><strong>Location:</strong> <%= program.getLocation() %></p>
                            <p class="mb-2"><strong>Date:</strong> <%= program.getStartDate() %> to <%= program.getEndDate() %></p>
                            <p class="mb-2"><strong>Participants:</strong> <%= program.getCurrentParticipants() %> / <%= program.getMaxParticipants() %></p>
                            <p class="mb-2">
                                <strong>Status:</strong> 
                                <span class="badge 
                                    <% if ("UPCOMING".equals(program.getStatus())) { %>bg-info
                                    <% } else if ("ONGOING".equals(program.getStatus())) { %>bg-success
                                    <% } else if ("COMPLETED".equals(program.getStatus())) { %>bg-secondary
                                    <% } else { %>bg-danger<% } %>">
                                    <%= program.getStatus() %>
                                </span>
                            </p>
                        </div>
                        <div class="card-footer">
                            <a href="program-detail.jsp?id=<%= program.getProgramId() %>" class="btn btn-sm btn-primary">View Details</a>
                            <c:if test="${sessionScope.isAdmin}">
                                <a href="program?action=edit&id=<%= program.getProgramId() %>" class="btn btn-sm btn-warning">Edit</a>
                                <form action="program-delete" method="POST" style="display: inline;">
                                    <input type="hidden" name="programId" value="<%= program.getProgramId() %>">
                                    <button type="submit" class="btn btn-sm btn-danger btn-delete">Delete</button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            <% 
                    }
                } else {
            %>
                <div class="col-12">
                    <div class="alert alert-info">No programs available at the moment.</div>
                </div>
            <% } %>
        </div>
    </div>
    
    <jsp:include page="includes/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>