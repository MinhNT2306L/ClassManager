<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Submissions - Class Manager</title>
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
                        <a href="users">Users</a>
                        <a href="assignments" style="color: var(--primary-color);">Assignments</a>
                        <a href="challenges">Challenges</a>
                    </div>
                </nav>

                <div class="card">
                    <h1>Submissions for: ${assignment.title}</h1>
                    <p style="margin-bottom: 2rem; color: #6b7280;">Total: ${submissions.size()}</p>

                    <table>
                        <thead>
                            <tr>
                                <th>Student ID</th>
                                <th>Submitted At</th>
                                <th>File</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${submissions}">
                                <tr>
                                    <td>Student #${s.studentId}</td>
                                    <td>${s.submittedAt}</td>
                                    <td>
                                        <a href="assignments?action=download&type=submissions&file=${s.filePath}"
                                            class="btn" style="width: auto; padding: 0.5rem 1rem; font-size: 0.8rem;">
                                            Download Work
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div style="margin-top: 2rem;">
                        <a href="assignments" style="color: var(--primary-color); text-decoration: none;">&larr; Back to
                            Assignments</a>
                    </div>
                </div>
            </div>
        </body>

        </html>