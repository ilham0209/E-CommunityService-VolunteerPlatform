package com.ecommunity.servlet;

import com.ecommunity.bean.Participation;
import com.ecommunity.bean.VolunteerProgram;
import com.ecommunity.dao.ParticipationDAO;
import com.ecommunity.dao.VolunteerProgramDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ParticipationServlet - Handles user participation in programs
 */
@WebServlet(name = "ParticipationServlet", urlPatterns = {"/participation"})
public class ParticipationServlet extends HttpServlet {
    
    private ParticipationDAO participationDAO;
    private VolunteerProgramDAO programDAO;
    
    @Override
    public void init() throws ServletException {
        participationDAO = new ParticipationDAO();
        programDAO = new VolunteerProgramDAO();
    }
    
    /**
     * GET - Handle join program action
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
        
        if ("join".equals(action)) {
            handleJoinProgram(request, response);
        } else if ("cancel".equals(action)) {
            handleCancelParticipation(request, response);
        } else {
            response.sendRedirect("program-list.jsp");
        }
    }
    
    /**
     * POST - Handle participation updates
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Handle user joining a program
     */
    private void handleJoinProgram(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String programIdStr = request.getParameter("programId");
        
        if (userId == null || programIdStr == null) {
            response.sendRedirect("program-list.jsp?error=invalidRequest");
            return;
        }
        
        try {
            int programId = Integer.parseInt(programIdStr);
            
            // Check if user already joined
            if (participationDAO.hasUserJoinedProgram(userId, programId)) {
                response.sendRedirect("program-detail.jsp?id=" + programId + "&error=alreadyJoined");
                return;
            }
            
            // Check if program is full
            VolunteerProgram program = programDAO.getProgramById(programId);
            if (program != null) {
                if (program.getCurrentParticipants() >= program.getMaxParticipants()) {
                    response.sendRedirect("program-detail.jsp?id=" + programId + "&error=programFull");
                    return;
                }
                
                // Create participation
                Participation participation = new Participation(userId, programId);
                boolean success = participationDAO.createParticipation(participation);
                
                if (success) {
                    // Update program participant count
                    program.setCurrentParticipants(program.getCurrentParticipants() + 1);
                    programDAO.updateProgram(program);
                    
                    response.sendRedirect("my-participations.jsp?success=joined");
                } else {
                    response.sendRedirect("program-detail.jsp?id=" + programId + "&error=joinFailed");
                }
            } else {
                response.sendRedirect("program-list.jsp?error=programNotFound");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("program-list.jsp?error=invalidId");
        }
    }
    
    /**
     * Handle canceling participation
     */
    private void handleCancelParticipation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String participationIdStr = request.getParameter("participationId");
        
        if (participationIdStr != null) {
            try {
                int participationId = Integer.parseInt(participationIdStr);
                
                // Get participation details before deleting
                Participation participation = participationDAO.getParticipationById(participationId);
                
                if (participation != null) {
                    // Soft delete participation
                    boolean success = participationDAO.deleteParticipation(participationId);
                    
                    if (success) {
                        // Update program participant count
                        VolunteerProgram program = programDAO.getProgramById(participation.getProgramId());
                        if (program != null && program.getCurrentParticipants() > 0) {
                            program.setCurrentParticipants(program.getCurrentParticipants() - 1);
                            programDAO.updateProgram(program);
                        }
                        
                        response.sendRedirect("my-participations.jsp?success=cancelled");
                    } else {
                        response.sendRedirect("my-participations.jsp?error=cancelFailed");
                    }
                } else {
                    response.sendRedirect("my-participations.jsp?error=notFound");
                }
                
            } catch (NumberFormatException e) {
                response.sendRedirect("my-participations.jsp?error=invalidId");
            }
        } else {
            response.sendRedirect("my-participations.jsp?error=missingId");
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Participation Servlet - Manages user participation in programs";
    }
}