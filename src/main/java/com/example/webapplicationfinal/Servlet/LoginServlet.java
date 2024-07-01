/*
package com.example.webapplicationfinal.Servlet;

import com.example.webapplicationfinal.Model.Utente;
import com.example.webapplicationfinal.Database.DBManager;
import com.example.webapplicationfinal.Database.dao.UtenteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/doLogin")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UtenteDAO utenteDAO = DBManager.getInstance().utenteDAO();
        Utente utente = utenteDAO.findByEmailAndPassword(email, password);

        if (utente != null) {
            request.getSession().setAttribute("utente", utente);
            response.sendRedirect("home.html"); // Redirect to home page after successful login
        } else {
            response.sendRedirect("accesso.html?error=invalid_credentials");
        }
    }
}
*/

