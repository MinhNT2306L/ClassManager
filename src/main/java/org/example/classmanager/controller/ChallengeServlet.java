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
import java.util.List;

import java.util.UUID;

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
            String originalName = org.example.classmanager.util.FileUtil.getFileName(filePart);

            if (!originalName.toLowerCase().endsWith(".txt")) {
                req.setAttribute("error", "Only .txt files are allowed!");
                listChallenges(req, resp);
                return;
            }

            String nameWithoutExt = originalName.substring(0, originalName.lastIndexOf('.'));
            String normalizedName = org.example.classmanager.util.FileUtil.removeAccents(nameWithoutExt).toLowerCase()
                    .trim().replaceAll("\\s+", " ");
            String savedFileName = normalizedName + ".txt";

            String uuid = UUID.randomUUID().toString();
            String relativePath = uuid + File.separator + savedFileName;

            saveFile(filePart, uuid, savedFileName);

            Challenge challenge = new Challenge();
            challenge.setHint(hint);
            challenge.setFilePath(relativePath);
            challengeDAO.createChallenge(challenge);
        }
        resp.sendRedirect("challenges");
    }

    private void solveChallenge(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() != User.Role.STUDENT) {
            resp.sendRedirect("challenges");
            return;
        }

        String answer = req.getParameter("answer");
        String idParam = req.getParameter("id");

        if (answer != null && idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                Challenge challenge = challengeDAO.findById(id);

                if (challenge == null) {
                    req.setAttribute("error", "Challenge not found!");
                    listChallenges(req, resp);
                    return;
                }

                String normalizedAnswer = org.example.classmanager.util.FileUtil.removeAccents(answer).toLowerCase()
                        .trim()
                        .replaceAll("\\s+", " ");

                String safeAnswer = Paths.get(normalizedAnswer).getFileName().toString();
                String submittedFilename = safeAnswer + ".txt";

                Path storedPath = Paths.get(challenge.getFilePath());
                String storedFilename = storedPath.getFileName().toString();

                if (!submittedFilename.equals(storedFilename)) {
                    req.setAttribute("error", "Incorrect answer!");
                    listChallenges(req, resp);
                    return;
                }

                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator
                        + "challenges";
                File challengeFile = new File(uploadPath, challenge.getFilePath());

                if (challengeFile.exists()) {
                    long fileSize = Files.size(challengeFile.toPath());
                    String content;
                    if (fileSize > 10240) {
                        byte[] bytes = new byte[2048];
                        try (java.io.InputStream is = Files.newInputStream(challengeFile.toPath())) {
                            int read = is.read(bytes);
                            content = new String(bytes, 0, read) + "\n... (File is too large to display fully)";
                        }
                    } else {
                        content = Files.readString(challengeFile.toPath());
                    }

                    req.setAttribute("success", true);
                    req.setAttribute("content", content);
                    req.setAttribute("answer", answer);
                } else {
                    req.setAttribute("error", "Challenge file missing on server!");
                }
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Invalid ID!");
            }
        }
        listChallenges(req, resp);
    }

    private void saveFile(Part filePart, String subDir, String fileName) throws IOException {
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator
                + "challenges" + File.separator + subDir;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdirs();
        filePart.write(uploadPath + File.separator + fileName);
    }
}
