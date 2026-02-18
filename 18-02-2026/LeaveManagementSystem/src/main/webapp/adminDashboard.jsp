<%@ page import="java.sql.*, dao.DBConnection" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-dark bg-dark px-3">
    <span class="navbar-brand">Admin Panel</span>
    <a href="login.jsp" class="btn btn-danger btn-sm">Logout</a>
</nav>

<div class="container mt-4">

    <div class="card shadow p-4">
        <h3>Leave Requests</h3>
        <hr>

        <table class="table table-bordered table-striped">
            <tr>
                <th>User ID</th>
                <th>From</th>
                <th>To</th>
                <th>Reason</th>
            </tr>

            <%
                Connection con = DBConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM leaves");

                while(rs.next()){
            %>

            <tr>
                <td><%= rs.getInt("user_id") %></td>
                <td><%= rs.getDate("from_date") %></td>
                <td><%= rs.getDate("to_date") %></td>
                <td><%= rs.getString("reason") %></td>
            </tr>

            <% } %>

        </table>
    </div>

</div>

</body>
</html>
