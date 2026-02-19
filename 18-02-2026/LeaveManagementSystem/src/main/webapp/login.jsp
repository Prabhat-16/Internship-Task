<!DOCTYPE html>
<html>
<head>
    <title>Login - Leave Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="auth-container">
        <div class="auth-card fade-in">
            <h2 class="text-center mb-4" style="font-weight: 700; color: var(--primary-color);">Welcome Back</h2>
            <p class="text-center text-muted mb-4">Please key in your credentials to sign in.</p>

            <% 
                String status = request.getParameter("status");
                if("registered".equals(status)) {
            %>
                <div class="alert-custom alert-success-custom">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path><polyline points="22 4 12 14.01 9 11.01"></polyline></svg>
                    Registration successful! Please login.
                </div>
            <% } else if("error".equals(status)) { %>
                 <div class="alert-custom alert-error-custom">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><line x1="15" y1="9" x2="9" y2="15"></line><line x1="9" y1="9" x2="15" y2="15"></line></svg>
                    Invalid email or password.
                </div>
            <% } %>

            <form action="login" method="post">
                <div class="mb-3">
                    <label class="form-label">Email Address</label>
                    <input type="email" name="email" class="form-control" placeholder="name@company.com" required>
                </div>

                <div class="mb-4">
                    <label class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" placeholder="••••••••" required>
                </div>

                <button class="btn btn-primary-custom w-100 mb-3">Login to Account</button>
            </form>

            <div class="text-center mt-4">
                <p class="text-muted">Don't have an account? <a href="register.jsp" style="color: var(--primary-color); font-weight: 600;">Create one</a></p>
            </div>
        </div>
    </div>
    
    <button id="theme-toggle" class="btn btn-dark theme-toggle-floating"></button>
    <script src="js/theme.js"></script>

</body>
</html>
