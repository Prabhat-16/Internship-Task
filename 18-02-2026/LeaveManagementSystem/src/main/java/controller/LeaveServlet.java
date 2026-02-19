package controller;

import dao.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/applyLeave")
public class LeaveServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String reason = request.getParameter("reason");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO leaves(user_id,from_date,to_date,reason) VALUES(?,?,?,?)");

            ps.setInt(1, userId);
            ps.setString(2, from);
            ps.setString(3, to);
            ps.setString(4, reason);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                response.sendRedirect("dashboard.jsp?status=success");
            } else {
                response.sendRedirect("applyLeave.jsp?status=failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("applyLeave.jsp?status=error");
        }
    }
}
