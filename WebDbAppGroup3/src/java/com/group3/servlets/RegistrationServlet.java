/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.servlets;

import com.group3.util.DbManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sheik.Mamun
 */
public class RegistrationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        
        ServletContext context = getServletContext();
        
        DbManager dbMgr = (DbManager)context.getAttribute("DbMgr");
        Connection conn = dbMgr.getConnection();
        
        try {
            //// starts our database transaction
            conn.setAutoCommit(false);
            insertIntoUsersTable(conn, userName, password, email, country);
            insertIntoLogTable(conn, userName);
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
        }finally{
            conn.close();
        }
        
        
        
        
        
        RequestDispatcher rd 
                     = context.getRequestDispatcher("/login.html");
             PrintWriter out = response.getWriter();
              out.println("<font color=green> Registration complete! "
                      + "please login</font>");
              rd.include(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void insertIntoUsersTable(Connection conn, String userName, String password, String email, String country) throws SQLException {
        String insertQuery = "insert into MYAPP.users"
                + "(user_id, user_name, email, country, user_password) "
                + "   values(?,?,?,?,?)";

        
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, userName+email);
            ps.setString(2, userName);
            ps.setString(3, email);
            ps.setString(4, country);
            ps.setString(5, password);
            ps.executeUpdate();
        
      }

    private void insertIntoLogTable(Connection conn, String userName) throws SQLException {
        String insertQuery = "insert into MYAPP.USER_LOG"
                + "(user_name, log_info) "
                + "   values(?,?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, userName);
            ps.setString(2, "its a long line to force it to throw error");

            ps.executeUpdate();
    
    }

}
