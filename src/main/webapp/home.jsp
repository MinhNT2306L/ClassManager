<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ page import="org.example.classmanager.model.User" %>
            <% User user=(User) session.getAttribute("user"); if (user==null) { response.sendRedirect("login.jsp");
                return; } %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>Dashboard - Class Manager</title>
                    <link rel="stylesheet" href="css/style.css">
                </head>

                <body>
                    <div class="container">
                        <nav class="navbar">
                            <div class="brand">
                                <h2>Class Manager</h2>
                            </div>
                            <div class="nav-links">
                                <a href="home.jsp">Dashboard</a>
                                <a href="users">Users</a>
                                <a href="assignments">Assignments</a>
                                <a href="challenges">Challenges</a>
                                <span style="border-left: 2px solid #ddd; margin-left: 1rem; padding-left: 1rem;">
                                    Hello, <%= user.getFullName() %>
                                </span>
                                <form action="auth" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="logout">
                                    <button type="submit"
                                        style="background: none; border: none; color: #ef4444; cursor: pointer; margin-left: 1rem; font-weight: 500;">Logout</button>
                                </form>
                            </div>
                        </nav>

                        <div class="card">
                            <h1>Welcome, <%= user.getRole() %>
                                    <%= user.getFullName() %>
                            </h1>
                            <p style="margin-top: 1rem; color: var(--text-muted);">
                                Select a section from the navigation bar to get started.
                            </p>

                            <div
                                style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1.5rem; margin-top: 2rem;">
                                <div class="card"
                                    style="box-shadow: none; border: 1px solid #e5e7eb; display: flex; flex-direction: column; justify-content: space-between; height: 100%;">
                                    <div>
                                        <h3>Users</h3>
                                        <p>View classmates and teachers.</p>
                                    </div>
                                    <a href="users" class="btn" style="margin-top: 1rem;">View Users</a>
                                </div>
                                <div class="card"
                                    style="box-shadow: none; border: 1px solid #e5e7eb; display: flex; flex-direction: column; justify-content: space-between; height: 100%;">
                                    <div>
                                        <h3>Assignments</h3>
                                        <p>Manage assignments and submissions.</p>
                                    </div>
                                    <a href="assignments" class="btn" style="margin-top: 1rem;">View Assignments</a>
                                </div>
                                <div class="card"
                                    style="box-shadow: none; border: 1px solid #e5e7eb; display: flex; flex-direction: column; justify-content: space-between; height: 100%;">
                                    <div>
                                        <h3>Challenges</h3>
                                        <p>Solve puzzles and earn recognition.</p>
                                    </div>
                                    <a href="challenges" class="btn" style="margin-top: 1rem;">Play Challenges</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </body>

                </html>