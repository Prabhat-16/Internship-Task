<%@ page import="java.sql.*, dao.DBConnection" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Leave Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>

<nav class="navbar-custom d-flex justify-content-between align-items-center mb-5">
    <div class="d-flex align-items-center gap-3">
        <span class="navbar-brand">Leave Management</span>
        <span class="badge bg-primary rounded-pill">Admin</span>
    </div>
    <div class="d-flex align-items-center gap-3">
        <button id="theme-toggle" class="btn btn-dark btn-sm rounded-circle d-flex align-items-center justify-content-center" style="width: 32px; height: 32px;"></button>
        <span class="text-muted fw-medium">Welcome, <%= session.getAttribute("userName") != null ? session.getAttribute("userName") : "Admin" %></span>
        <a href="login.jsp" class="btn btn-danger btn-sm rounded-pill px-3">Logout</a>
    </div>
</nav>

<script src="js/theme.js"></script>

<div class="container fade-in">
    <div class="row mb-5">
        <div class="col-md-12">
            <h2 class="mb-1">Admin Dashboard</h2>
            <p class="text-muted">Overview of all employee leave requests.</p>
        </div>
    </div>

    <div class="dashboard-card">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h4 class="mb-0">All Leave Requests</h4>
        </div>
        
        <div class="table-responsive">
            <table class="table-custom">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>From Date</th>
                        <th>To Date</th>
                        <th>Reason</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    try {
                        Connection con = DBConnection.getConnection();
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM leaves ORDER BY from_date DESC");

                        while(rs.next()){
                %>
                    <tr>
                        <td><span class="fw-bold">#<%= rs.getInt("user_id") %></span></td>
                        <td><%= rs.getDate("from_date") %></td>
                        <td><%= rs.getDate("to_date") %></td>
                        <td><%= rs.getString("reason") %></td>
                        <td>
                        <%
                            String status = rs.getString("status");
                            if ("Pending".equalsIgnoreCase(status) || status == null) {
                        %>
                            <form action="leaveAction" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="<%= rs.getInt("id") %>">
                                <input type="hidden" name="status" value="Approved">
                                <button type="submit" class="btn btn-sm btn-success rounded-pill px-3">Approve</button>
                            </form>
                            <form action="leaveAction" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="<%= rs.getInt("id") %>">
                                <input type="hidden" name="status" value="Rejected">
                                <button type="submit" class="btn btn-sm btn-danger rounded-pill px-3">Reject</button>
                            </form>
                        <% } else { %>
                            <%
                                String badgeClass = "secondary";
                                if("Approved".equalsIgnoreCase(status)) badgeClass = "success";
                                else if("Rejected".equalsIgnoreCase(status)) badgeClass = "danger";
                            %>
                            <span class="badge bg-<%= badgeClass %> rounded-pill"><%= status %></span>
                        <% } %>
                        </td>
                    </tr>
                <% 
                        }
                        con.close();
                    } catch(Exception e) {
                        e.printStackTrace();
                %>
                    <tr><td colspan="5" class="text-danger">Error loading data: <%= e.getMessage() %></td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
