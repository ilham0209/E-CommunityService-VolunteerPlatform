package com.ecommunity.servlet;

import com.ecommunity.dao.VolunteerProgramDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ProgramDeleteServlet - Handles soft delete of programs
 * Module 3: CRUD - Soft Delete
 */
@WebServlet(name = "ProgramDeleteServlet", urlPatterns = {"/program-delete"})
public class ProgramDeleteServlet extends HttpServlet {
    
    private VolunteerProgramDAO programDAO;
    
    @Override
    public void init() throws ServletException {
        programDAO = new VolunteerProgramDAO();
    }
    
    /**
     * POST - Soft delete program (set is_deleted = 1)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String programIdStr = request.getParameter("programId");
        
        if (programIdStr != null && !programIdStr.isEmpty()) {
            try {
                int programId = Integer.parseInt(programIdStr);
                
                // CRITICAL: Soft delete (set is_deleted = 1)
                boolean success = programDAO.deleteProgram(programId);
                
                if (success) {
                    response.sendRedirect("program-list.jsp?success=deleted");
                } else {
                    response.sendRedirect("program-list.jsp?error=deleteFailed");
                }
                
            } catch (NumberFormatException e) {
                response.sendRedirect("program-list.jsp?error=invalidId");
            }
        } else {
            response.sendRedirect("program-list.jsp?error=missingId");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("program-list.jsp");
    }
}