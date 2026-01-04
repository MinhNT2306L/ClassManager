<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Class Manager</title>
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <div class="login-card card">
        <h1>Welcome Back</h1>
        <p style="color: #6b7280; margin-bottom: 2rem;">Sign in to your account</p>
        
        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) { 
        %>
            <div class="error-msg"><%= error %></div>
        <% } %>

        <form action="auth" method="post">
            <input type="hidden" name="action" value="login">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit" class="btn">Sign In</button>
        </form>
    </div>
</body>
</html>
