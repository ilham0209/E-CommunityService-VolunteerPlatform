package com.ecommunity.servlet;

import com.ecommunity.bean.ActivityLog;
import com.ecommunity.dao.ActivityLogDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ActivityLogServlet - Display activity logs (ADMIN ONLY)
 */
@WebServlet(name = "ActivityLogServlet", urlPatterns = {"/activity-log"})
public class ActivityLogServlet extends HttpServlet {
    
    private ActivityLogDAO activityLogDAO;
    
    @Override
    public void init() throws ServletException {
        activityLogDAO = new ActivityLogDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        
        // Check if user is admin
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("dashboard?error=accessDenied");
            return;
        }
        
        // Get all activity logs
        List<ActivityLog> logs = activityLogDAO.getAllLogs();
        request.setAttribute("logs", logs);
        
        request.getRequestDispatcher("activity-log.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}