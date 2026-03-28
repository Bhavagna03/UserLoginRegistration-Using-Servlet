package com.codegnan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.codegnan.dto.User;
import com.codegnan.service.UserService;
import com.codegnan.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("*.do")
public class UserControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserService service = new UserServiceImpl();

    // ===================== GET =====================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String uri = req.getRequestURI();
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        /* ========== LIST USERS ========== */
        if (uri.endsWith("list.do")) {

            List<User> list = service.getAllUsers();

            out.println("<html><head><title>All Users</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;background:#eef3ff}");
            out.println("table{border-collapse:collapse;margin:auto;background:white}");
            out.println("th,td{padding:12px 18px;border:1px solid #ccc}");
            out.println("th{background:#0b5ed7;color:white}");
            out.println("</style></head><body>");

            out.println("<h2 align='center'>📋 Registered Users</h2>");

            if (list.isEmpty()) {
                out.println("<p align='center'>No users found</p>");
            } else {
                out.println("<table>");
                out.println("<tr><th>ID</th><th>Username</th><th>Email</th></tr>");

                for (User u : list) {
                    out.println("<tr>");
                    out.println("<td>" + u.getId() + "</td>");
                    out.println("<td>" + u.getUsername() + "</td>");
                    out.println("<td>" + u.getEmail() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

            out.println("</body></html>");
        }

        /* ========== UPDATE FORM ========== */
        else if (uri.endsWith("updateform.do")) {

            int id = Integer.parseInt(req.getParameter("id"));
            User u = service.getUserById(id);

            if (u == null) {
                req.getRequestDispatcher("notexisted.html").forward(req, res);
            } else {
                out.println("<html><head><title>Update User</title></head>");
                out.println("<body style='font-family:Arial;background:#eef3ff'>");

                out.println("<h2 align='center'>✏️ Update User</h2>");

                out.println("<form action='update.do' method='post' style='width:350px;margin:auto;background:white;padding:25px;border-radius:10px'>");

                out.println("<input type='hidden' name='id' value='" + u.getId() + "'>");

                out.println("Username<br>");
                out.println("<input type='text' name='username' value='" + u.getUsername() + "' required><br><br>");

                out.println("Password<br>");
                out.println("<input type='password' name='password' value='" + u.getPassword() + "' required><br><br>");

                out.println("Email<br>");
                out.println("<input type='email' name='email' value='" + u.getEmail() + "' required><br><br>");

                out.println("<input type='submit' value='Update User'>");

                out.println("</form></body></html>");
            }
        }
    }

    // ===================== POST =====================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        /* ========== REGISTER ========== */
        if (uri.endsWith("register.do")) {

            User u = new User(
                    req.getParameter("username"),
                    req.getParameter("password"),
                    req.getParameter("email")
            );

            redirect(service.registerUser(u), req, res);
        }

        /* ========== LOGIN ========== */
        else if (uri.endsWith("login.do")) {

            User u = service.getUserByUsername(req.getParameter("username"));

            if (u != null && u.getPassword().equals(req.getParameter("password"))) {
                req.getRequestDispatcher("success.html").forward(req, res);
            } else {
                req.getRequestDispatcher("failure.html").forward(req, res);
            }
        }

        /* ========== UPDATE ========== */
        else if (uri.endsWith("update.do")) {

            User u = new User(
                    Integer.parseInt(req.getParameter("id")),
                    req.getParameter("username"),
                    req.getParameter("password"),
                    req.getParameter("email")
            );

            redirect(service.updateUser(u), req, res);
        }

        /* ========== DELETE ========== */
        else if (uri.endsWith("delete.do")) {

            int id = Integer.parseInt(req.getParameter("id"));
            redirect(service.deleteUser(id), req, res);
        }
    }

    // ===================== REDIRECT =====================
    private void redirect(String status, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        if ("success".equals(status))
            req.getRequestDispatcher("success.html").forward(req, res);
        else if ("existed".equals(status))
            req.getRequestDispatcher("existed.html").forward(req, res);
        else
            req.getRequestDispatcher("failure.html").forward(req, res);
    }
}
