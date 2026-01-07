package org.example.classmanager.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.classmanager.dao.ChallengeDAO;
import org.example.classmanager.model.Challenge;
import org.example.classmanager.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/challenges")
@MultipartConfig
public class ChallengeServlet extends HttpServlet {
    private ChallengeDAO challengeDAO = new ChallengeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            listChallenges(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("create".equals(action)) {
            createChallenge(req, resp);
        } else if ("solve".equals(action)) {
            solveChallenge(req, resp);
        }
    }

    private void listChallenges(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Challenge> challenges = challengeDAO.findAll();
        req.setAttribute("challenges", challenges);
        req.getRequestDispatcher("challenges.jsp").forward(req, resp);
    }

    private void createChallenge(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() != User.Role.TEACHER) {
            resp.sendRedirect("challenges");
            return;
        }

        String hint = req.getParameter("hint");
        Part filePart = req.getPart("file");

        if (filePart != null && filePart.getSize() > 0) {
            String originalName = getFileName(filePart);
            // Remove extension for normalization if needed, but we keep it or just
            // normalize the name part
            // Requirement: "Tên file được viết dưới định dạng không dấu và các từ cách nhau
            // bởi 1 khoảng trắng"
            // Example: "Chiếc Thuyền.txt" -> "chiec thuyen.txt" or just "chiec thuyen" as
            // answer?
            // "Đáp án chính là tên file mà giáo viên upload lên"
            // Usually assumes the name *without extension* is the answer, or the full name.
            // Let's assume name without extension is the answer to be typed.

            String nameWithoutExt = originalName.contains(".")
                    ? originalName.substring(0, originalName.lastIndexOf('.'))
                    : originalName;
            String ext = originalName.contains(".") ? originalName.substring(originalName.lastIndexOf('.')) : "";

            String normalizedName = removeAccents(nameWithoutExt).toLowerCase().trim().replaceAll("\\s+", " ");
            String savedFileName = normalizedName + ext;

            saveFile(filePart, savedFileName);

            Challenge challenge = new Challenge();
            challenge.setHint(hint);
            challenge.setFilePath(savedFileName);
            challengeDAO.createChallenge(challenge);
        }
        resp.sendRedirect("challenges");
    }

    private void solveChallenge(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String answer = req.getParameter("answer");
        if (answer != null) {
            String normalizedAnswer = removeAccents(answer).toLowerCase().trim().replaceAll("\\s+", " ");
            // Check if file exists. We try common text extensions or just append .txt if
            // implied
            // The requirement says "upload lên 1 file txt". So we verify .txt

            // Security Fix: Prevent path traversal
            // 1. Sanitize the answer to ensure it's just a filename component
            String safeAnswer = Paths.get(normalizedAnswer).getFileName().toString();
            String filename = safeAnswer + ".txt";

            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator
                    + "challenges";
            File uploadDir = new File(uploadPath);
            File file = new File(uploadDir, filename);

            // 2. Strong verification of path containment
            if (!file.getCanonicalPath().startsWith(uploadDir.getCanonicalPath())) {
                req.setAttribute("error", "Invalid path!");
                listChallenges(req, resp);
                return;
            }

            if (file.exists()) {
                // Return content
                String content = Files.readString(file.toPath());
                req.setAttribute("success", true);
                req.setAttribute("content", content);
                req.setAttribute("answer", answer);
            } else {
                req.setAttribute("error", "Incorrect answer!");
            }
        }
        listChallenges(req, resp);
    }

    private void saveFile(Part filePart, String fileName) throws IOException {
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator
                + "challenges";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdirs();
        filePart.write(uploadPath + File.separator + fileName);
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "unknown";
    }

    public static String removeAccents(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ', 'd').replace('Đ', 'D');
    }
}
