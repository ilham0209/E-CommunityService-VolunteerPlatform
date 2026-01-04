package com.ecommunity.servlet;

import com.ecommunity.bean.VolunteerProgram;
import com.ecommunity.dao.VolunteerProgramDAO;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ProgramServlet - Handles program create and update operations
 * Module 3: CRUD Operations
 */
@WebServlet(name = "ProgramServlet", urlPatterns = {"/program"})
public class ProgramServlet extends HttpServlet {
    
    private VolunteerProgramDAO programDAO;
    
    @Override
    public void init() throws ServletException {
        programDAO = new VolunteerProgramDAO();
    }
    
    /**
     * GET - Display program form (create or edit)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("edit".equals(action)) {
            // Edit existing program
            int programId = Integer.parseInt(request.getParameter("id"));
            VolunteerProgram program = programDAO.getProgramById(programId);
            
            if (program != null) {
                request.setAttribute("program", program);
                request.setAttribute("action", "edit");
            } else {
                request.setAttribute("errorMessage", "Program not found");
            }
        } else {
            // Create new program
            request.setAttribute("action", "create");
        }
        
        request.getRequestDispatcher("program-form.jsp").forward(request, response);
    }
    
    /**
     * POST - Handle program create/update
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
        
        String action = request.getParameter("action");
        
        // Get form parameters
        String programName = request.getParameter("programName");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String maxParticipantsStr = request.getParameter("maxParticipants");
        String category = request.getParameter("category");
        String status = request.getParameter("status");
        
        // Server-side validation
        if (isNullOrEmpty(programName) || isNullOrEmpty(description) || 
            isNullOrEmpty(location) || isNullOrEmpty(startDateStr) || 
            isNullOrEmpty(endDateStr) || isNullOrEmpty(maxParticipantsStr) || 
            isNullOrEmpty(category)) {
            
            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("program-form.jsp").forward(request, response);
            return;
        }
        
        try {
            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);
            int maxParticipants = Integer.parseInt(maxParticipantsStr);
            
            // Validate dates
            if (endDate.before(startDate)) {
                request.setAttribute("errorMessage", "End date must be after start date!");
                request.getRequestDispatcher("program-form.jsp").forward(request, response);
                return;
            }
            
            if ("create".equals(action)) {
                // Create new program
                int organizerId = (Integer) session.getAttribute("userId");
                
                VolunteerProgram program = new VolunteerProgram();
                program.setProgramName(programName);
                program.setDescription(description);
                program.setLocation(location);
                program.setStartDate(startDate);
                program.setEndDate(endDate);
                program.setMaxParticipants(maxParticipants);
                program.setCategory(category);
                program.setOrganizerId(organizerId);
                
                boolean success = programDAO.createProgram(program);
                
                if (success) {
                    response.sendRedirect("program-list.jsp?success=created");
                } else {
                    request.setAttribute("errorMessage", "Failed to create program");
                    request.getRequestDispatcher("program-form.jsp").forward(request, response);
                }
                
            } else if ("edit".equals(action)) {
                // Update existing program
                int programId = Integer.parseInt(request.getParameter("programId"));
                
                VolunteerProgram program = programDAO.getProgramById(programId);
                if (program != null) {
                    program.setProgramName(programName);
                    program.setDescription(description);
                    program.setLocation(location);
                    program.setStartDate(startDate);
                    program.setEndDate(endDate);
                    program.setMaxParticipants(maxParticipants);
                    program.setCategory(category);
                    program.setStatus(status != null ? status : "UPCOMING");
                    
                    boolean success = programDAO.updateProgram(program);
                    
                    if (success) {
                        response.sendRedirect("program-list.jsp?success=updated");
                    } else {
                        request.setAttribute("errorMessage", "Failed to update program");
                        request.getRequestDispatcher("program-form.jsp").forward(request, response);
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("program-form.jsp").forward(request, response);
        }
    }
    
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}