<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Challenges - Class Manager</title>
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
                        <a href="assignments">Assignments</a>
                        <a href="challenges" style="color: var(--primary-color);">Challenges</a>
                    </div>
                </nav>

                <c:if test="${sessionScope.user.role == 'TEACHER'}">
                    <div class="card" style="margin-bottom: 2rem;">
                        <h2>Create New Challenge</h2>
                        <form action="challenges" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="create">
                            <div style="margin-bottom: 1rem;">
                                <input type="text" name="hint" placeholder="Hint (e.g., Book Title, context...)"
                                    required>
                            </div>
                            <div style="margin-bottom: 1rem;">
                                <label>Upload Text File (The filename will be the answer)</label>
                                <input type="file" name="file" accept=".txt" required
                                    style="border: none; padding-left: 0;">
                            </div>
                            <button type="submit" class="btn">Create Challenge</button>
                        </form>
                    </div>
                </c:if>

                <div class="card">
                    <h1>Challenges</h1>

                    <%-- Result Display --%>
                        <c:if test="${not empty success}">
                            <div
                                style="background: #ecfdf5; color: #065f46; padding: 1rem; border-radius: 8px; margin-bottom: 1.5rem; border: 1px solid #a7f3d0;">
                                <h3>Correct! Here is the content:</h3>
                                <pre
                                    style="white-space: pre-wrap; margin-top: 1rem; font-family: monospace; background: white; padding: 1rem; border-radius: 4px;">${content}</pre>
                            </div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="error-msg">${error}</div>
                        </c:if>

                        <div style="display: grid; gap: 1.5rem; margin-top: 1.5rem;">
                            <c:forEach var="c" items="${challenges}">
                                <div class="card" style="box-shadow: none; border: 1px solid #e5e7eb; padding: 1.5rem;">
                                    <h3 style="margin-bottom: 0.5rem;">Hint: ${c.hint}</h3>
                                    <p style="color: #6b7280; font-size: 0.875rem;">Created at: ${c.createdAt}</p>

                                    <c:if test="${sessionScope.user.role == 'STUDENT'}">
                                        <form action="challenges" method="post"
                                            style="margin-top: 1rem; display: flex; gap: 1rem;">
                                            <input type="hidden" name="action" value="solve">
                                            <input type="hidden" name="id" value="${c.id}">
                                            <input type="text" name="answer" placeholder="Enter answer..." required
                                                style="margin-bottom: 0; flex-grow: 1;">
                                            <button type="submit" class="btn" style="width: auto;">Submit
                                                Answer</button>
                                        </form>
                                    </c:if>
                                </div>
                            </c:forEach>
                            <c:if test="${empty challenges}">
                                <p style="text-align: center; color: #6b7280;">No challenges available.</p>
                            </c:if>
                        </div>
                </div>
            </div>
        </body>

        </html>