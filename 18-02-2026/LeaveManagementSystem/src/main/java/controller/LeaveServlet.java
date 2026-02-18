package controller;

import dao.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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

        int userId = (int) request.getSession().getAttribute("userId");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO leaves(user_id,from_date,to_date,reason) VALUES(?,?,?,?)");

            ps.setInt(1, userId);
            ps.setString(2, from);
            ps.setString(3, to);
            ps.setString(4, reason);

            ps.executeUpdate();

            response.sendRedirect("dashboard.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
