package com.ecommunity.servlet;

import com.ecommunity.dao.ActivityLogDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LogoutServlet - Handles user logout
 * Module 1: Logout with session invalidation
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {
    
    private ActivityLogDAO activityLogDAO;
    
    @Override
    public void init() throws ServletException {
        activityLogDAO = new ActivityLogDAO();
    }
    
    /**
     * Handles GET request - Process logout
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session (do not create new one)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Log activity before destroying session
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                String ipAddress = request.getRemoteAddr();
                activityLogDAO.logActivity(userId, "LOGOUT", 
                    "User logged out successfully", ipAddress);
                System.out.println("User " + userId + " logged out");
            }
            
            // Invalidate session - CRITICAL REQUIREMENT
            session.invalidate();
        }
        
        // Redirect to login page with success message
        response.sendRedirect("login.jsp?logout=success");
    }
    
    /**
     * Handles POST request - Same as GET
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Logout Servlet - Handles session invalidation";
    }
}