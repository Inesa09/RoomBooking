package com.it.academy.controllers;

import com.it.academy.common.ViewUrls;
import com.it.academy.constants.UserConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LogoutServlet configures logout button
 */
@WebServlet({"/logout"})  // LOGOUT_SERVLET
public class LogoutServlet extends HttpServlet{

    private static final long serialVersionUID = 3L;

    public LogoutServlet() {
        super();
    }

    /**
     * Logs out the user
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.isRequestedSessionIdFromCookie()
                && request.isRequestedSessionIdValid()
                && request.getSession().getAttribute(UserConstants.LOGIN_DTO.toString()) != null) {

            HttpSession session = request.getSession();
            session.invalidate();
        }

//        PrintWriter out = new PrintWriter(response.getWriter());
//        out.println("This a test!!!");

        getServletConfig()
                .getServletContext()
                .getRequestDispatcher(ViewUrls.LOGIN_JSP.toString())
                .include(request, response);
    }

}