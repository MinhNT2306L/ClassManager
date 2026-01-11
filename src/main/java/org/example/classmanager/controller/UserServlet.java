package org.example.classmanager.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classmanager.dao.UserDAO;
import org.example.classmanager.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            listUsers(req, resp);
        } else if ("view".equals(action)) {
            viewUser(req, resp);
        } else if ("edit".equals(action)) {
            showEditForm(req, resp);
        } else if ("delete".equals(action)) {
            deleteUser(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("create".equals(action)) {
            createUser(req, resp);
        } else if ("update".equals(action)) {
            updateUser(req, resp);
        }
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> listUser = userDAO.findAll();
        req.setAttribute("listUser", listUser);
        req.getRequestDispatcher("users.jsp").forward(req, resp);
    }

    private void viewUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        User user = userDAO.findById(id);
        req.setAttribute("userItem", user);
        req.getRequestDispatcher("user-detail.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        User user = userDAO.findById(id);
        req.setAttribute("userItem", user);
        req.getRequestDispatcher("user-form.jsp").forward(req, resp);
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser.getRole() != User.Role.TEACHER) {
            resp.sendRedirect("users");
            return;
        }

        User newUser = new User();
        newUser.setUsername(req.getParameter("username"));
        newUser.setPassword(req.getParameter("password"));
        newUser.setFullName(req.getParameter("fullName"));
        newUser.setEmail(req.getParameter("email"));
        newUser.setPhone(req.getParameter("phone"));
        newUser.setRole(User.Role.valueOf(req.getParameter("role")));

        userDAO.save(newUser);
        resp.sendRedirect("users");
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        User existingUser = userDAO.findById(id);

        User currentUser = (User) req.getSession().getAttribute("user");

        if (currentUser.getRole() == User.Role.STUDENT && !currentUser.getId().equals(id)) {
            resp.sendRedirect("users");
            return; // Unauthorized
        }

        // Apply changes
        existingUser.setFullName(req.getParameter("fullName"));
        existingUser.setEmail(req.getParameter("email"));
        existingUser.setPhone(req.getParameter("phone"));
        existingUser.setPhone(req.getParameter("phone"));

        userDAO.update(existingUser);
        resp.sendRedirect("users?action=view&id=" + id);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser.getRole() != User.Role.TEACHER) {
            resp.sendRedirect("users");
            return;
        }
        Long id = Long.parseLong(req.getParameter("id"));
        userDAO.delete(id);
        resp.sendRedirect("users");
    }
}
