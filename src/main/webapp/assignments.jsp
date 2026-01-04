<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Assignments - Class Manager</title>
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

                <c:if test="${sessionScope.user.role == 'TEACHER'}">
                    <div class="card" style="margin-bottom: 2rem;">
                        <h2>Create New Assignment</h2>
                        <form action="assignments" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="create">
                            <input type="text" name="title" placeholder="Assignment Title" required>
                            <textarea name="description" placeholder="Description" rows="3"></textarea>
                            <div style="margin-bottom: 1rem;">
                                <label>Attach File</label>
                                <input type="file" name="file" style="border: none; padding-left: 0;">
                            </div>
                            <button type="submit" class="btn">Upload Assignment</button>
                        </form>
                    </div>
                </c:if>

                <div class="card">
                    <h1>All Assignments</h1>
                    <div style="display: grid; gap: 1.5rem; margin-top: 1.5rem;">
                        <c:forEach var="a" items="${assignments}">
                            <div class="card" style="box-shadow: none; border: 1px solid #e5e7eb; padding: 1.5rem;">
                                <div style="display: flex; justify-content: space-between; align-items: start;">
                                    <div>
                                        <h3 style="margin-bottom: 0.5rem;">${a.title}</h3>
                                        <p style="color: #6b7280; margin-bottom: 0.5rem;">${a.description}</p>
                                        <c:if test="${a.filePath != null}">
                                            <a href="assignments?action=download&type=assignments&file=${a.filePath}"
                                                style="color: var(--primary-color); text-decoration: none; font-size: 0.9rem;">
                                                Download Attachment
                                            </a>
                                        </c:if>
                                    </div>
                                    <c:if test="${sessionScope.user.role == 'TEACHER'}">
                                        <a href="assignments?action=view_submissions&id=${a.id}" class="btn"
                                            style="width: auto; padding: 0.5rem 1rem; font-size: 0.9rem;">View
                                            Submissions</a>
                                    </c:if>
                                </div>

                                <c:if test="${sessionScope.user.role == 'STUDENT'}">
                                    <div style="margin-top: 1.5rem; border-top: 1px dashed #e5e7eb; padding-top: 1rem;">
                                        <h4 style="margin-bottom: 0.5rem; font-size: 1rem;">Submit Your Work</h4>
                                        <form action="assignments" method="post" enctype="multipart/form-data"
                                            style="display: flex; gap: 1rem; align-items: center;">
                                            <input type="hidden" name="action" value="submit">
                                            <input type="hidden" name="assignmentId" value="${a.id}">
                                            <input type="file" name="file" required
                                                style="margin-bottom: 0; width: auto; flex-grow: 1;">
                                            <button type="submit" class="btn" style="width: auto;">Submit</button>
                                        </form>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                        <c:if test="${empty assignments}">
                            <p style="text-align: center; color: #6b7280;">No assignments available.</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </body>

        </html>