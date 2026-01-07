<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - E-Community Platform</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }
        .login-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            padding: 40px;
        }
        .logo-section {
            text-align: center;
            margin-bottom: 30px;
        }
        .logo-section h1 {
            color: #667eea;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-5 col-md-7">
                <div class="login-card">
                    <div class="logo-section">
                        <img src="images/uitm-logo.png" alt="UiTM Logo" style="max-width: 150px; margin-bottom: 20px;">
                        <h1>E-Community</h1>
                        <p class="text-muted">Volunteer & Service Platform</p>
                    </div>
                    
                    <!-- Success Message (from logout or registration) -->
                    <% 
                        String logoutParam = request.getParameter("logout");
                        if ("success".equals(logoutParam)) {
                    %>
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            You have been logged out successfully!
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    <% } %>
                    
                    <% if (request.getAttribute("successMessage") != null) { %>
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <%= request.getAttribute("successMessage") %>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    <% } %>
                    
                    <!-- Error Message -->
                    <% if (request.getAttribute("errorMessage") != null) { %>
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <%= request.getAttribute("errorMessage") %>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    <% } %>
                    
                    <!-- Login Form -->
                    <form action="login" method="POST">
                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <input type="email" class="form-control" id="email" name="email" 
                                   value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                                   placeholder="Enter your email" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" 
                                   placeholder="Enter your password" required>
                        </div>
                        
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary btn-lg">Login</button>
                        </div>
                    </form>
                    
                    <hr class="my-4">
                    
                    <div class="text-center">
                        <p>Don't have an account? <a href="register">Register here</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>