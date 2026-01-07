<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Users - Class Manager</title>
            <link rel="stylesheet" href="css/style.css">
        </head>

        <body>
            <div class="container">
                <nav class="navbar">
                    <div class="brand">
                        <h2 onclick="location.href='home.jsp'" style="cursor:pointer">Class Manager</h2>
                    </div>
                    <div class="nav-links">
                        <a href="home.jsp">Dashboard</a>
                        <a href="users" class="active" style="color: var(--primary-color);">Users</a>
                        <a href="assignments">Assignments</a>
                        <a href="challenges">Challenges</a>
                    </div>
                </nav>

                <div class="card">
                    <div
                        style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                        <h1>User List</h1>
                        <c:if test="${sessionScope.user.role == 'TEACHER'}">
                            <a href="users?action=edit&id=0" class="btn" style="width: auto;">+ Add User</a>
                        </c:if>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Full Name</th>
                                <th>Role</th>
                                <th>Email</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${listUser}">
                                <tr>
                                    <td>${u.fullName}</td>
                                    <td>
                                        <span
                                            style="padding: 4px 8px; background: ${u.role == 'TEACHER' ? '#e0e7ff' : '#f3f4f6'}; color: ${u.role == 'TEACHER' ? '#4f46e5' : '#374151'}; border-radius: 4px; font-size: 0.875rem; font-weight: 500;">
                                            ${u.role}
                                        </span>
                                    </td>
                                    <td>${u.email}</td>
                                    <td>
                                        <a href="users?action=view&id=${u.id}"
                                            style="color: var(--primary-color); text-decoration: none; margin-right: 1rem;">View</a>
                                        <c:if test="${sessionScope.user.role == 'TEACHER'}">
                                            <a href="users?action=edit&id=${u.id}"
                                                style="color: #6b7280; text-decoration: none; margin-right: 1rem;">Edit</a>
                                            <a href="users?action=delete&id=${u.id}"
                                                style="color: #ef4444; text-decoration: none;"
                                                onclick="return confirm('Are you sure?')">Delete</a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </body>

        </html>