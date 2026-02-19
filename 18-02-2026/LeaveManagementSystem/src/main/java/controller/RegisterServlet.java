package controller;

import dao.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)");

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, "employee"); // Default role

            int rows = ps.executeUpdate();

            if (rows > 0) {
                response.sendRedirect("login.jsp?status=registered");
            } else {
                response.sendRedirect("register.jsp?status=failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?status=error"); // Redirect on error
        }
    }
}
