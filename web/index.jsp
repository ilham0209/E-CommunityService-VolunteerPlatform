<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome - E-Community Platform</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 100px 0;
            text-align: center;
        }
        .feature-card {
            padding: 30px;
            text-align: center;
            border: 1px solid #e0e0e0;
            border-radius: 10px;
            transition: all 0.3s ease;
        }
        .feature-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .feature-icon {
            font-size: 3rem;
            margin-bottom: 20px;
            color: #667eea;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <jsp:include page="includes/navbar.jsp" />
    
    <!-- Hero Section -->
    <section class="hero-section">
        <div class="container">
            <h1 class="display-3 fw-bold mb-4">E-Community Platform</h1>
            <p class="lead fs-4 mb-5">Connect, Volunteer, Make a Difference</p>
            <p class="fs-5 mb-5">Join thousands of volunteers making an impact in the community</p>
            <div>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <a href="dashboard" class="btn btn-light btn-lg me-3">Go to Dashboard</a>
                        <a href="program-list.jsp" class="btn btn-outline-light btn-lg">Browse Programs</a>
                    </c:when>
                    <c:otherwise>
                        <a href="register" class="btn btn-light btn-lg me-3">Get Started</a>
                        <a href="login" class="btn btn-outline-light btn-lg">Login</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </section>
    
    <!-- Features Section -->
    <section class="py-5">
        <div class="container">
            <h2 class="text-center mb-5">Why Choose E-Community?</h2>
            <div class="row g-4">
                <div class="col-md-4">
                    <div class="feature-card">
                        <div class="feature-icon">🤝</div>
                        <h4>Easy Registration</h4>
                        <p>Quick and simple registration process for students and public members</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="feature-card">
                        <div class="feature-icon">📅</div>
                        <h4>Browse Programs</h4>
                        <p>Find volunteer programs that match your interests and schedule</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="feature-card">
                        <div class="feature-icon">⏱️</div>
                        <h4>Track Hours</h4>
                        <p>Keep track of your volunteer hours and contributions</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <!-- Categories Section -->
    <section class="bg-light py-5">
        <div class="container">
            <h2 class="text-center mb-5">Program Categories</h2>
            <div class="row text-center">
                <div class="col-md-3 mb-4">
                    <div class="p-4 bg-white rounded shadow-sm">
                        <h3>🌱</h3>
                        <h5>Environment</h5>
                        <p class="text-muted">Beach cleanups, tree planting</p>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="p-4 bg-white rounded shadow-sm">
                        <h3>📚</h3>
                        <h5>Education</h5>
                        <p class="text-muted">Tutoring, literacy programs</p>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="p-4 bg-white rounded shadow-sm">
                        <h3>🏥</h3>
                        <h5>Health</h5>
                        <p class="text-muted">Health camps, awareness</p>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="p-4 bg-white rounded shadow-sm">
                        <h3>🏘️</h3>
                        <h5>Community</h5>
                        <p class="text-muted">Food drives, support services</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <!-- Call to Action -->
    <section class="py-5 bg-gradient-primary text-white">
        <div class="container text-center">
            <h2 class="mb-4">Ready to Make a Difference?</h2>
            <p class="lead mb-4">Join our community of volunteers today</p>
            <c:choose>
                <c:when test="${empty sessionScope.user}">
                    <a href="register" class="btn btn-light btn-lg">Register Now</a>
                </c:when>
                <c:otherwise>
                    <a href="program-list.jsp" class="btn btn-light btn-lg">Browse Programs</a>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
    
    <!-- Footer -->
    <jsp:include page="includes/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>