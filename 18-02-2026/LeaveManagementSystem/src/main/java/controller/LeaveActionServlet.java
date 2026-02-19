package controller;

import dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/leaveAction")
public class LeaveActionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String status = request.getParameter("status");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE leaves SET status=? WHERE id=?");
            ps.setString(1, status);
            ps.setString(2, id);

            ps.executeUpdate();

            response.sendRedirect("adminDashboard.jsp?status=updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("adminDashboard.jsp?status=error");
        }
    }
}
