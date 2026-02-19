<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Leave Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>

<nav class="navbar-custom d-flex justify-content-between align-items-center">
    <div class="d-flex align-items-center gap-3">
        <span class="navbar-brand">Leave Management</span>
    </div>
    <div class="d-flex align-items-center gap-3">
        <button id="theme-toggle" class="btn btn-dark btn-sm rounded-circle d-flex align-items-center justify-content-center" style="width: 32px; height: 32px;"></button>
        <span class="text-muted fw-medium">Welcome, <%= session.getAttribute("userName") != null ? session.getAttribute("userName") : "User" %></span>
        <a href="login.jsp" class="btn btn-danger btn-sm rounded-pill px-3">Logout</a>
    </div>
</nav>

<script src="js/theme.js"></script>

<%@ page import="java.sql.*, dao.DBConnection" %>
<div class="container mt-5 fade-in">

    <% 
        String status = request.getParameter("status");
        if("success".equals(status)) {
    %>
        <div class="alert-custom alert-success-custom">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path><polyline points="22 4 12 14.01 9 11.01"></polyline></svg>
            Leave applied successfully!
        </div>
    <% } else if("error".equals(status) || "failed".equals(status)) { %>
        <div class="alert-custom alert-error-custom">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><line x1="15" y1="9" x2="9" y2="15"></line><line x1="9" y1="9" x2="15" y2="15"></line></svg>
            Failed to apply leave. Please try again.
        </div>
    <% } %>

    <div class="row mb-5">
        <div class="col-md-12">
            <div class="dashboard-card d-flex justify-content-between align-items-center">
                <div>
                    <h2 class="mb-1">Your Dashboard</h2>
                    <p class="text-muted mb-0">Manage your leaves and view history.</p>
                </div>
                <a href="applyLeave.jsp" class="btn btn-primary-custom">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="me-2"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg>
                    Apply New Leave
                </a>
            </div>
        </div>
    </div>

    <div class="dashboard-card">
        <h4 class="mb-4">Leave History</h4>
        <div class="table-responsive">
            <table class="table-custom">
                <thead>
                    <tr>
                        <th>From Date</th>
                        <th>To Date</th>
                        <th>Reason</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    try {
                        Integer userId = (Integer) session.getAttribute("userId");
                        if(userId != null) {
                            Connection con = DBConnection.getConnection();
                            PreparedStatement ps = con.prepareStatement("SELECT * FROM leaves WHERE user_id = ? ORDER BY from_date DESC");
                            ps.setInt(1, userId);
                            ResultSet rs = ps.executeQuery();

                            while(rs.next()){
                %>
                    <tr>
                        <td><%= rs.getString("from_date") %></td>
                        <td><%= rs.getString("to_date") %></td>
                        <td><%= rs.getString("reason") %></td>
                        <% 
                            String leaveStatus = rs.getString("status");
                            String badgeClass = "status-pending";
                            if("Approved".equalsIgnoreCase(leaveStatus)) {
                                badgeClass = "status-approved";
                            } else if("Rejected".equalsIgnoreCase(leaveStatus)) {
                                badgeClass = "status-rejected";
                            }
                            if(leaveStatus == null) leaveStatus = "Pending";
                        %>
                        <td><span class="status-badge <%= badgeClass %>"><%= leaveStatus %></span></td>
                    </tr>
                <% 
                            }
                            con.close();
                        } else {
                            response.sendRedirect("login.jsp");
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                %>
                    <tr><td colspan="4" class="text-danger">Error loading leaves: <%= e.getMessage() %></td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
