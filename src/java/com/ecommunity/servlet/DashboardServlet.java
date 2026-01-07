package com.ecommunity.servlet;

import com.ecommunity.bean.User;
import com.ecommunity.bean.VolunteerProgram;
import com.ecommunity.dao.UserDAO;
import com.ecommunity.dao.VolunteerProgramDAO;
import com.ecommunity.dao.ParticipationDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * DashboardServlet - Displays dashboard with summary information
 * Module 4: Dashboard showing statistics
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {
    
    private UserDAO userDAO;
    private VolunteerProgramDAO programDAO;
    private ParticipationDAO participationDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        programDAO = new VolunteerProgramDAO();
        participationDAO = new ParticipationDAO();
    }
    
    /**
     * Handles GET request - Display dashboard
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        
        // Get user from session
        User currentUser = (User) session.getAttribute("user");
        
        // Fetch dashboard statistics
        try {
            // Total counts
            int totalUsers = userDAO.getTotalUserCount();
            int totalPrograms = programDAO.getTotalProgramCount();
            int totalParticipations = participationDAO.getTotalParticipationCount();
            
            // User-specific statistics
            int myParticipations = participationDAO.getParticipationCountByUser(currentUser.getUserId());
            double myTotalHours = participationDAO.getTotalHoursByUser(currentUser.getUserId());
            
            // Recent programs (limit to 5)
            List<VolunteerProgram> recentPrograms = programDAO.getRecentPrograms(5);
            
            // Upcoming programs count
            int upcomingPrograms = programDAO.getUpcomingProgramCount();
            
            // Set attributes for JSP
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalPrograms", totalPrograms);
            request.setAttribute("totalParticipations", totalParticipations);
            request.setAttribute("myParticipations", myParticipations);
            request.setAttribute("myTotalHours", myTotalHours);
            request.setAttribute("recentPrograms", recentPrograms);
            request.setAttribute("upcomingPrograms", upcomingPrograms);
            
            // Forward to dashboard JSP
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error loading dashboard: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading dashboard data");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Dashboard Servlet - Displays system statistics";
    }
}