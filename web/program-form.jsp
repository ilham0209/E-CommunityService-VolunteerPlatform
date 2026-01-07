<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${action == 'edit' ? 'Edit' : 'Create'} Program - E-Community</title>
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
                        <h3>${action == 'edit' ? 'Edit Program' : 'Create New Program'}</h3>
                    </div>
                    <div class="card-body">
                        <% if (request.getAttribute("errorMessage") != null) { %>
                            <div class="alert alert-danger">
                                <%= request.getAttribute("errorMessage") %>
                            </div>
                        <% } %>
                        
                        <form action="program" method="POST">
                            <input type="hidden" name="action" value="${action}">
                            <c:if test="${action == 'edit'}">
                                <input type="hidden" name="programId" value="${program.programId}">
                            </c:if>
                            
                            <div class="mb-3">
                                <label class="form-label">Program Name *</label>
                                <input type="text" class="form-control" name="programName" 
                                       value="${program.programName}" required>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Description *</label>
                                <textarea class="form-control" name="description" rows="4" required>${program.description}</textarea>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Location *</label>
                                <input type="text" class="form-control" name="location" 
                                       value="${program.location}" required>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Start Date *</label>
                                    <input type="date" class="form-control" name="startDate" 
                                           value="${program.startDate}" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">End Date *</label>
                                    <input type="date" class="form-control" name="endDate" 
                                           value="${program.endDate}" required>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Max Participants *</label>
                                    <input type="number" class="form-control" name="maxParticipants" 
                                           value="${program.maxParticipants}" min="1" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Category *</label>
                                    <select class="form-select" name="category" required>
                                        <option value="">-- Select Category --</option>
                                        <option value="Environment" ${program.category == 'Environment' ? 'selected' : ''}>Environment</option>
                                        <option value="Education" ${program.category == 'Education' ? 'selected' : ''}>Education</option>
                                        <option value="Health" ${program.category == 'Health' ? 'selected' : ''}>Health</option>
                                        <option value="Community" ${program.category == 'Community' ? 'selected' : ''}>Community</option>
                                    </select>
                                </div>
                            </div>
                            
                            <c:if test="${action == 'edit'}">
                                <div class="mb-3">
                                    <label class="form-label">Status</label>
                                    <select class="form-select" name="status">
                                        <option value="UPCOMING" ${program.status == 'UPCOMING' ? 'selected' : ''}>Upcoming</option>
                                        <option value="ONGOING" ${program.status == 'ONGOING' ? 'selected' : ''}>Ongoing</option>
                                        <option value="COMPLETED" ${program.status == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                                        <option value="CANCELLED" ${program.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                                    </select>
                                </div>
                            </c:if>
                            
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="program-list.jsp" class="btn btn-secondary">Cancel</a>
                                <button type="submit" class="btn btn-primary">
                                    ${action == 'edit' ? 'Update Program' : 'Create Program'}
                                </button>
                            </div>
                        </form>
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