package com.ecommunity.servlet;

import com.ecommunity.bean.User;
import com.ecommunity.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LoginServlet - Handles user authentication
 * Module 1: Login with session-based authentication
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    /**
     * Handles GET request - Display login page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect("dashboard");
            return;
        }
        
        // Forward to login page
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    
    /**
     * Handles POST request - Process login authentication
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Server-side validation
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "Email and password are required!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // Validate email format
        if (!isValidEmail(email)) {
            request.setAttribute("errorMessage", "Invalid email format!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // Authenticate user
        User user = userDAO.getUserByEmail(email);
        
        if (user != null && user.getPassword().equals(password)) {
            // Authentication successful
            
            // Create session
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userName", user.getFullName());
            session.setAttribute("userType", user.getUserType());
            session.setAttribute("isAdmin", user.isAdmin()); // NEW: Store admin status
            
            // Set session timeout (30 minutes)
            session.setMaxInactiveInterval(30 * 60);
            
            // Log activity (optional - can be implemented later)
            // activityLogDAO.logActivity(user.getUserId(), "LOGIN", "User logged in", request.getRemoteAddr());
            
            // Redirect to dashboard
            response.sendRedirect("dashboard");
            
        } else {
            // Authentication failed
            request.setAttribute("errorMessage", "Invalid email or password!");
            request.setAttribute("email", email); // Keep email in form
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    /**
     * Validate email format using regex
     * @param email Email to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    @Override
    public String getServletInfo() {
        return "Login Servlet - Handles user authentication";
    }
}