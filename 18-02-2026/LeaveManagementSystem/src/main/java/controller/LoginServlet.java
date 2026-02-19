package controller;

import dao.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND password=?");

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                HttpSession session = request.getSession();
                session.setAttribute("userId", rs.getInt("id"));
                session.setAttribute("userName", rs.getString("name"));
                String role = rs.getString("role");
                session.setAttribute("role", role);

                if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect("adminDashboard.jsp");
                } else {
                    response.sendRedirect("dashboard.jsp");
                }

            } else {
                response.sendRedirect("login.jsp?status=error");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
