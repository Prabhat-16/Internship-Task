<!DOCTYPE html>
<html>
<head>
    <title>Apply Leave - Leave Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>

<nav class="navbar-custom d-flex justify-content-between align-items-center mb-5">
    <div class="d-flex align-items-center gap-3">
        <span class="navbar-brand">Leave Management</span>
    </div>
    <div class="d-flex align-items-center gap-3">
        <button id="theme-toggle" class="btn btn-dark btn-sm rounded-circle d-flex align-items-center justify-content-center" style="width: 32px; height: 32px;"></button>
        <a href="dashboard.jsp" class="btn btn-secondary-custom btn-sm rounded-pill px-3">Back to Dashboard</a>
        <a href="login.jsp" class="btn btn-danger btn-sm rounded-pill px-3">Logout</a>
    </div>
</nav>

<script src="js/theme.js"></script>

<div class="container fade-in" style="max-width: 800px;">
    <% 
        String status = request.getParameter("status");
        if("failed".equals(status)) {
    %>
        <div class="alert-custom alert-error-custom">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><line x1="15" y1="9" x2="9" y2="15"></line><line x1="9" y1="9" x2="15" y2="15"></line></svg>
            Failed to apply leave.
        </div>
    <% } else if("error".equals(status)) { %>
        <div class="alert-custom alert-error-custom">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><line x1="15" y1="9" x2="9" y2="15"></line><line x1="9" y1="9" x2="15" y2="15"></line></svg>
            An error occurred! Please try again.
        </div>
    <% } %>

    <div class="dashboard-card">
        <h3 class="mb-4">Apply for Leave</h3>
        <hr class="mb-4" style="border-color: var(--border-color);">

        <form action="applyLeave" method="post">
            <div class="row">
                <div class="col-md-6 mb-4">
                    <label class="form-label">From Date</label>
                    <input type="date" name="from" class="form-control" required>
                </div>

                <div class="col-md-6 mb-4">
                    <label class="form-label">To Date</label>
                    <input type="date" name="to" class="form-control" required>
                </div>
            </div>

            <div class="mb-4">
                <label class="form-label">Reason for Leave</label>
                <textarea name="reason" class="form-control" rows="4" placeholder="Please describe the reason for your leave..." required></textarea>
            </div>

            <div class="d-flex justify-content-end gap-3">
                <a href="dashboard.jsp" class="btn btn-secondary-custom">Cancel</a>
                <button class="btn btn-primary-custom px-5">Submit Request</button>
            </div>
        </form>
    </div>
</div>

</body>

</body>
</html>
