package org.example.classmanager.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.classmanager.dao.AssignmentDAO;
import org.example.classmanager.model.Assignment;
import org.example.classmanager.model.Submission;
import org.example.classmanager.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet("/assignments")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class AssignmentServlet extends HttpServlet {
    private AssignmentDAO assignmentDAO = new AssignmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            listAssignments(req, resp);
        } else if ("view_submissions".equals(action)) {
            viewSubmissions(req, resp);
        } else if ("download".equals(action)) {
            downloadFile(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("create".equals(action)) {
            createAssignment(req, resp);
        } else if ("submit".equals(action)) {
            submitAssignment(req, resp);
        }
    }

    private void listAssignments(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Assignment> assignments = assignmentDAO.findAll();
        req.setAttribute("assignments", assignments);
        req.getRequestDispatcher("assignments.jsp").forward(req, resp);
    }

    private void createAssignment(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() != User.Role.TEACHER) {
            resp.sendRedirect("assignments");
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Part filePart = req.getPart("file");

        String fileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            fileName = saveFile(filePart, "assignments");
        }

        Assignment assignment = new Assignment();
        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setFilePath(fileName);
        assignment.setTeacherId(user.getId());

        assignmentDAO.createAssignment(assignment);
        resp.sendRedirect("assignments");
    }

    private void submitAssignment(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() != User.Role.STUDENT) {
            resp.sendRedirect("assignments");
            return;
        }

        Long assignmentId = Long.parseLong(req.getParameter("assignmentId"));
        Part filePart = req.getPart("file");

        String fileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            fileName = saveFile(filePart, "submissions");
        }

        Submission submission = new Submission();
        submission.setAssignmentId(assignmentId);
        submission.setStudentId(user.getId());
        submission.setFilePath(fileName);

        assignmentDAO.submitAssignment(submission);
        resp.sendRedirect("assignments");
    }

    private void viewSubmissions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() != User.Role.TEACHER) {
            resp.sendRedirect("assignments");
            return;
        }

        Long assignmentId = Long.parseLong(req.getParameter("id"));
        Assignment assignment = assignmentDAO.findById(assignmentId);
        List<Submission> submissions = assignmentDAO.findSubmissionsByAssignmentId(assignmentId);

        req.setAttribute("assignment", assignment);
        req.setAttribute("submissions", submissions);
        req.getRequestDispatcher("submissions.jsp").forward(req, resp);
    }

    private String saveFile(Part filePart, String subDir) throws IOException {
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + subDir;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdirs();

        String fileName = UUID.randomUUID().toString() + "_" + getFileName(filePart);
        filePart.write(uploadPath + File.separator + fileName);
        return fileName;
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "unknown";
    }

    private void downloadFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type"); // assignments or submissions
        String fileName = req.getParameter("file");

        if (fileName == null || fileName.isEmpty())
            return;

        // Security Fix: Prevent path traversal
        // 1. Sanitize the filename to remove directory components
        String keyName = Paths.get(fileName).getFileName().toString();

        // 2. Resolve paths and verify containment
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + type;
        File uploadDir = new File(uploadPath);
        File file = new File(uploadDir, keyName);

        // Verify that the file is actually inside the uploadDir
        if (!file.getCanonicalPath().startsWith(uploadDir.getCanonicalPath())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid file path");
            return;
        }

        if (file.exists()) {
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + keyName + "\"");
            Files.copy(file.toPath(), resp.getOutputStream());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
