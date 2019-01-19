package com.it.academy.controllers.common;

import com.it.academy.common.ControllerUrls;
import com.it.academy.controllers.RequestValidator;
import com.it.academy.common.ViewUrls;

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
        if (RequestValidator.isValid(request)) {
            HttpSession session = request.getSession();
            session.invalidate();
        }

        response.sendRedirect(request.getContextPath()
                + ControllerUrls.LOGIN_SERVLET);
    }

}
