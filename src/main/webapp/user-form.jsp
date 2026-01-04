<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>${userItem != null ? 'Edit User' : 'New User'} - Class Manager</title>
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

                <div class="card" style="max-width: 500px; margin: 0 auto;">
                    <h1>${userItem != null ? 'Edit User' : 'New User'}</h1>

                    <form action="users" method="post">
                        <input type="hidden" name="action" value="${userItem != null ? 'update' : 'create'}">
                        <c:if test="${userItem != null}">
                            <input type="hidden" name="id" value="${userItem.id}">
                        </c:if>

                        <div style="margin-bottom: 1rem;">
                            <label>Full Name</label>
                            <input type="text" name="fullName" value="${userItem.fullName}" required>
                        </div>

                        <div style="margin-bottom: 1rem;">
                            <label>Email</label>
                            <input type="email" name="email" value="${userItem.email}">
                        </div>

                        <div style="margin-bottom: 1rem;">
                            <label>Phone</label>
                            <input type="text" name="phone" value="${userItem.phone}">
                        </div>

                        <%-- Readonly fields for student when editing self --%>
                            <c:choose>
                                <c:when test="${userItem == null || sessionScope.user.role == 'TEACHER'}">
                                    <div style="margin-bottom: 1rem;">
                                        <label>Username</label>
                                        <%-- Username readonly on edit --%>
                                            <input type="text" name="username" value="${userItem.username}" ${userItem
                                                !=null ? 'readonly' : '' } required>
                                    </div>
                                    <c:if test="${userItem == null}">
                                        <div style="margin-bottom: 1rem;">
                                            <label>Password</label>
                                            <input type="password" name="password" required>
                                        </div>
                                    </c:if>
                                    <div style="margin-bottom: 1rem;">
                                        <label>Role</label>
                                        <select name="role" ${userItem !=null ? 'disabled' : '' }>
                                            <option value="STUDENT" ${userItem.role=='STUDENT' ? 'selected' : '' }>
                                                Student</option>
                                            <option value="TEACHER" ${userItem.role=='TEACHER' ? 'selected' : '' }>
                                                Teacher</option>
                                        </select>
                                        <c:if test="${userItem != null}">
                                            <input type="hidden" name="role" value="${userItem.role}">
                                        </c:if>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <%-- Student Self Edit --%>
                                        <input type="hidden" name="role" value="${userItem.role}">
                                </c:otherwise>
                            </c:choose>

                            <button type="submit" class="btn">Save Changes</button>
                    </form>
                </div>
            </div>
        </body>

        </html>