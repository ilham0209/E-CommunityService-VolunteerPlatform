<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommunity.dao.VolunteerProgramDAO" %>
<%@ page import="com.ecommunity.bean.VolunteerProgram" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Program Details - E-Community</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="includes/navbar.jsp" />
    
    <div class="container mt-5">
        <% 
            String programIdStr = request.getParameter("id");
            if (programIdStr != null) {
                int programId = Integer.parseInt(programIdStr);
                VolunteerProgramDAO programDAO = new VolunteerProgramDAO();
                VolunteerProgram program = programDAO.getProgramById(programId);
                
                if (program != null) {
        %>
        
        <div class="row">
            <div class="col-12">
                <a href="program-list.jsp" class="btn btn-secondary mb-3">← Back to Programs</a>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header bg-gradient-primary text-white">
                <h2><%= program.getProgramName() %></h2>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-8">
                        <h4>Description</h4>
                        <p class="lead"><%= program.getDescription() %></p>
                        
                        <hr>
                        
                        <h5>Program Details</h5>
                        <table class="table">
                            <tr>
                                <th width="30%">Category:</th>
                                <td><span class="badge bg-secondary fs-6"><%= program.getCategory() %></span></td>
                            </tr>
                            <tr>
                                <th>Location:</th>
                                <td><%= program.getLocation() %></td>
                            </tr>
                            <tr>
                                <th>Start Date:</th>
                                <td><%= program.getStartDate() %></td>
                            </tr>
                            <tr>
                                <th>End Date:</th>
                                <td><%= program.getEndDate() %></td>
                            </tr>
                            <tr>
                                <th>Status:</th>
                                <td>
                                    <span class="badge fs-6
                                        <% if ("UPCOMING".equals(program.getStatus())) { %>bg-info
                                        <% } else if ("ONGOING".equals(program.getStatus())) { %>bg-success
                                        <% } else if ("COMPLETED".equals(program.getStatus())) { %>bg-secondary
                                        <% } else { %>bg-danger<% } %>">
                                        <%= program.getStatus() %>
                                    </span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="card bg-light">
                            <div class="card-body text-center">
                                <h5>Participation</h5>
                                <div class="display-4 text-primary">
                                    <%= program.getCurrentParticipants() %> / <%= program.getMaxParticipants() %>
                                </div>
                                <p class="text-muted">Participants</p>
                                
                                <% 
                                    int spotsLeft = program.getMaxParticipants() - program.getCurrentParticipants();
                                    if (spotsLeft > 0) {
                                %>
                                    <p class="text-success"><strong><%= spotsLeft %> spots remaining</strong></p>
                                <% } else { %>
                                    <p class="text-danger"><strong>Program is full</strong></p>
                                <% } %>
                                
                                <c:if test="${not empty sessionScope.user}">
                                    <% if (spotsLeft > 0 && "UPCOMING".equals(program.getStatus())) { %>
                                        <a href="participation?action=join&programId=<%= program.getProgramId() %>" 
                                           class="btn btn-primary btn-lg w-100">
                                            Join Program
                                        </a>
                                    <% } %>
                                </c:if>
                                
                                <c:if test="${empty sessionScope.user}">
                                    <p class="text-muted mt-3">Please <a href="login">login</a> to join this program</p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <% 
                } else {
        %>
            <div class="alert alert-danger">Program not found!</div>
        <% 
                }
            } else {
        %>
            <div class="alert alert-danger">Invalid program ID!</div>
        <% } %>
    </div>
    
    <jsp:include page="includes/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>