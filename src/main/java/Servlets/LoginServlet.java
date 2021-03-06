package Servlets;

import Accounts.AccountService;
import Accounts.UserAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        if (login == null || pass == null){
            resp.sendRedirect("login.jsp");
            return;
        }

        UserAccount profile = AccountService.getUserByLogin(login);
        if (profile == null || !profile.getPass().equals(pass)){
            resp.sendRedirect("login.jsp");
            return;
        }

        AccountService.addSession(req.getSession().getId(), profile);
        req.getSession().setAttribute("login", login);
        resp.sendRedirect(String.format("/my-app/files?path=C:\\my_users\\%s", login));
    }
}
