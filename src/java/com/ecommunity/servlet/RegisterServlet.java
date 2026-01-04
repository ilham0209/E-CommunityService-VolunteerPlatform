package com.ecommunity.servlet;

import com.ecommunity.bean.User;
import com.ecommunity.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RegisterServlet - Handles user registration with validation
 * Module 2: Registration with server-side validation
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    /**
     * Handles GET request - Display registration page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    
    /**
     * Handles POST request - Process registration with SERVER-SIDE validation
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String userType = request.getParameter("userType"); // STUDENT or PUBLIC
        String matricNumber = request.getParameter("matricNumber");
        String icNumber = request.getParameter("icNumber");
        String phoneNumber = request.getParameter("phoneNumber");
        
        // ============= SERVER-SIDE VALIDATION (CRITICAL) =============
        
        // 1. Check required fields
        if (isNullOrEmpty(email) || isNullOrEmpty(password) || 
            isNullOrEmpty(confirmPassword) || isNullOrEmpty(fullName) || 
            isNullOrEmpty(userType)) {
            
            setErrorAndForward(request, response, "All required fields must be filled!");
            return;
        }
        
        // 2. Email format validation
        if (!isValidEmail(email)) {
            setErrorAndForward(request, response, "Invalid email format! Example: user@example.com");
            return;
        }
        
        // 3. Email uniqueness check
        if (userDAO.emailExists(email)) {
            setErrorAndForward(request, response, "Email already registered! Please use different email.");
            return;
        }
        
        // 4. Password length validation (minimum 6 characters)
        if (password.length() < 6) {
            setErrorAndForward(request, response, "Password must be at least 6 characters long!");
            return;
        }
        
        // 5. Password confirmation validation
        if (!password.equals(confirmPassword)) {
            setErrorAndForward(request, response, "Passwords do not match!");
            return;
        }
        
        // 6. Conditional validation based on user type
        if ("STUDENT".equals(userType)) {
            if (isNullOrEmpty(matricNumber)) {
                setErrorAndForward(request, response, "Matric number is required for students!");
                return;
            }
            // Validate matric number format (optional: e.g., 2022123456)
            if (!matricNumber.matches("\\d{10}")) {
                setErrorAndForward(request, response, "Invalid matric number format! Must be 10 digits.");
                return;
            }
        } else if ("PUBLIC".equals(userType)) {
            if (isNullOrEmpty(icNumber)) {
                setErrorAndForward(request, response, "IC number is required for public users!");
                return;
            }
            // Validate IC format (e.g., 901231-14-5678)
            if (!icNumber.matches("\\d{6}-\\d{2}-\\d{4}")) {
                setErrorAndForward(request, response, "Invalid IC number format! Use format: YYMMDD-PB-####");
                return;
            }
        } else {
            setErrorAndForward(request, response, "Invalid user type selected!");
            return;
        }
        
        // 7. Phone number validation (optional but recommended)
        if (!isNullOrEmpty(phoneNumber)) {
            if (!phoneNumber.matches("\\d{10,11}")) {
                setErrorAndForward(request, response, "Invalid phone number! Must be 10-11 digits.");
                return;
            }
        }
        
        // ============= ALL VALIDATIONS PASSED - CREATE USER =============
        
        // Create User object
        User newUser = new User();
        newUser.setEmail(email.toLowerCase().trim());
        newUser.setPassword(password); // In production: use password hashing (BCrypt/SHA-256)
        newUser.setFullName(fullName.trim());
        newUser.setUserType(userType);
        newUser.setPhoneNumber(phoneNumber);
        
        // Set conditional fields
        if ("STUDENT".equals(userType)) {
            newUser.setMatricNumber(matricNumber.trim());
            newUser.setIcNumber(null);
        } else {
            newUser.setIcNumber(icNumber.trim());
            newUser.setMatricNumber(null);
        }
        
        // Save to database
        boolean success = userDAO.createUser(newUser);
        
        if (success) {
            // Registration successful
            request.setAttribute("successMessage", "Registration successful! Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // Database error
            setErrorAndForward(request, response, "Registration failed! Please try again.");
        }
    }
    
    /**
     * Helper method: Check if string is null or empty
     */
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Helper method: Validate email format
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Helper method: Set error message and forward back to registration page
     */
    private void setErrorAndForward(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        
        // Keep form data
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("email", request.getParameter("email"));
        request.setAttribute("fullName", request.getParameter("fullName"));
        request.setAttribute("userType", request.getParameter("userType"));
        request.setAttribute("matricNumber", request.getParameter("matricNumber"));
        request.setAttribute("icNumber", request.getParameter("icNumber"));
        request.setAttribute("phoneNumber", request.getParameter("phoneNumber"));
        
        // Forward back to registration page
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Registration Servlet - Handles user registration with server-side validation";
    }
}