<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - E-Community Platform</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }
        .register-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            padding: 40px;
        }
        .form-label {
            font-weight: 600;
            color: #333;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-md-8">
                <div class="register-card">
                    <h2 class="text-center mb-4">Register Account</h2>
                    <p class="text-center text-muted mb-4">Join our community service platform</p>
                    
                    <!-- Error Message -->
                    <% if (request.getAttribute("errorMessage") != null) { %>
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <%= request.getAttribute("errorMessage") %>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    <% } %>
                    
                    <!-- Registration Form -->
                    <form action="register" method="POST" id="registerForm">
                        
                        <!-- Email -->
                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address *</label>
                            <input type="email" class="form-control" id="email" name="email" 
                                   value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                                   placeholder="example@email.com" required>
                        </div>
                        
                        <!-- Full Name -->
                        <div class="mb-3">
                            <label for="fullName" class="form-label">Full Name *</label>
                            <input type="text" class="form-control" id="fullName" name="fullName" 
                                   value="<%= request.getAttribute("fullName") != null ? request.getAttribute("fullName") : "" %>"
                                   placeholder="Your full name" required>
                        </div>
                        
                        <!-- User Type -->
                        <div class="mb-3">
                            <label for="userType" class="form-label">User Type *</label>
                            <select class="form-select" id="userType" name="userType" required>
                                <option value="">-- Select User Type --</option>
                                <option value="STUDENT" <%= "STUDENT".equals(request.getAttribute("userType")) ? "selected" : "" %>>
                                    Student UiTM
                                </option>
                                <option value="PUBLIC" <%= "PUBLIC".equals(request.getAttribute("userType")) ? "selected" : "" %>>
                                    Public / Orang Luar
                                </option>
                            </select>
                        </div>
                        
                        <!-- Matric Number (for STUDENT only) -->
                        <div class="mb-3" id="matricNumberField" style="display: none;">
                            <label for="matricNumber" class="form-label">Matric Number *</label>
                            <input type="text" class="form-control" id="matricNumber" name="matricNumber" 
                                   value="<%= request.getAttribute("matricNumber") != null ? request.getAttribute("matricNumber") : "" %>"
                                   placeholder="e.g., 2022123456" maxlength="10">
                            <small class="text-muted">10-digit matric number</small>
                        </div>
                        
                        <!-- IC Number (for PUBLIC only) -->
                        <div class="mb-3" id="icNumberField" style="display: none;">
                            <label for="icNumber" class="form-label">IC Number *</label>
                            <input type="text" class="form-control" id="icNumber" name="icNumber" 
                                   value="<%= request.getAttribute("icNumber") != null ? request.getAttribute("icNumber") : "" %>"
                                   placeholder="e.g., 901231-14-5678" maxlength="14">
                            <small class="text-muted">Format: YYMMDD-PB-####</small>
                        </div>
                        
                        <!-- Phone Number -->
                        <div class="mb-3">
                            <label for="phoneNumber" class="form-label">Phone Number</label>
                            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" 
                                   value="<%= request.getAttribute("phoneNumber") != null ? request.getAttribute("phoneNumber") : "" %>"
                                   placeholder="0123456789" maxlength="11">
                        </div>
                        
                        <!-- Password -->
                        <div class="mb-3">
                            <label for="password" class="form-label">Password *</label>
                            <input type="password" class="form-control" id="password" name="password" 
                                   placeholder="Minimum 6 characters" required>
                            <small class="text-muted">Must be at least 6 characters</small>
                        </div>
                        
                        <!-- Confirm Password -->
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Confirm Password *</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
                                   placeholder="Re-enter password" required>
                        </div>
                        
                        <!-- Submit Button -->
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary btn-lg">Register</button>
                        </div>
                        
                        <!-- Login Link -->
                        <div class="text-center mt-3">
                            <p>Already have an account? <a href="login.jsp">Login here</a></p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Client-Side JavaScript for UI ONLY (NOT for validation logic) -->
    <script>
        // Show/hide fields based on user type selection
        document.getElementById('userType').addEventListener('change', function() {
            var userType = this.value;
            var matricField = document.getElementById('matricNumberField');
            var icField = document.getElementById('icNumberField');
            var matricInput = document.getElementById('matricNumber');
            var icInput = document.getElementById('icNumber');
            
            if (userType === 'STUDENT') {
                matricField.style.display = 'block';
                icField.style.display = 'none';
                matricInput.required = true;
                icInput.required = false;
                icInput.value = ''; // Clear IC field
            } else if (userType === 'PUBLIC') {
                matricField.style.display = 'none';
                icField.style.display = 'block';
                matricInput.required = false;
                icInput.required = true;
                matricInput.value = ''; // Clear matric field
            } else {
                matricField.style.display = 'none';
                icField.style.display = 'none';
                matricInput.required = false;
                icInput.required = false;
            }
        });
        
        // Trigger change event on page load if user type is already selected
        window.addEventListener('load', function() {
            var userType = document.getElementById('userType').value;
            if (userType) {
                document.getElementById('userType').dispatchEvent(new Event('change'));
            }
        });
        
        // Client-side password match check (UI enhancement only)
        document.getElementById('registerForm').addEventListener('submit', function(e) {
            var password = document.getElementById('password').value;
            var confirmPassword = document.getElementById('confirmPassword').value;
            
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('Passwords do not match!');
                return false;
            }
        });
    </script>
</body>
</html>