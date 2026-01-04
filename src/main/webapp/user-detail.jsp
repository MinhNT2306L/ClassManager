<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>${userItem.fullName} - Class Manager</title>
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
                        <a href="users" style="color: var(--primary-color);">Users</a>
                        <a href="assignments">Assignments</a>
                        <a href="challenges">Challenges</a>
                    </div>
                </nav>

                <div class="card" style="max-width: 600px; margin: 0 auto;">
                    <div style="text-align: center; margin-bottom: 2rem;">
                        <div
                            style="width: 80px; height: 80px; background: #e0e7ff; color: #4f46e5; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 2rem; font-weight: bold; margin: 0 auto 1rem;">
                            ${userItem.fullName.charAt(0)}
                        </div>
                        <h1>${userItem.fullName}</h1>
                        <p style="color: #6b7280;">${userItem.role}</p>
                    </div>

                    <div style="display: grid; gap: 1rem;">
                        <div>
                            <label style="color: #6b7280; font-size: 0.875rem;">Email</label>
                            <p style="font-weight: 500;">${userItem.email}</p>
                        </div>
                        <div>
                            <label style="color: #6b7280; font-size: 0.875rem;">Phone</label>
                            <p style="font-weight: 500;">${userItem.phone}</p>
                        </div>
                        <div>
                            <label style="color: #6b7280; font-size: 0.875rem;">Username</label>
                            <p style="font-weight: 500;">${userItem.username}</p>
                        </div>
                    </div>

                    <div style="margin-top: 2rem; border-top: 1px solid #e5e7eb; padding-top: 1rem;">
                        <c:if test="${sessionScope.user.role == 'TEACHER' || sessionScope.user.id == userItem.id}">
                            <a href="users?action=edit&id=${userItem.id}" class="btn">Edit Profile</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </body>

        </html>