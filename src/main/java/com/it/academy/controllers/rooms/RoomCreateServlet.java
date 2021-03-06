package com.it.academy.controllers.rooms;

import com.it.academy.common.ControllerUrls;
import com.it.academy.common.ObjContainer;
import com.it.academy.common.ViewUrls;
import com.it.academy.constants.BookingConstants;
import com.it.academy.constants.ErrorConstants;
import com.it.academy.constants.RoomConstants;
import com.it.academy.constants.UserConstants;
import com.it.academy.controllers.RequestValidator;
import com.it.academy.dto.BookingRoomDto;
import com.it.academy.dto.LoginDto;
import com.it.academy.dto.RoomDto;
import com.it.academy.service.BookingService;
import com.it.academy.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class RoomCreateServlet configures page of room creation
 */
@WebServlet({"/room-create"})
public class RoomCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 16L;
    private RoomService roomService;
    private RequestValidator requestValidator;

    public RoomCreateServlet() {
        super();
        roomService = ObjContainer.getInstance().getRoomService();
        requestValidator = ObjContainer.getInstance().getRequestValidator();
    }

    /**
     * Shows the form to create room
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (requestValidator.isValid(request)) {
            request.setAttribute(BookingConstants.URL_TO_POST.toString(), ControllerUrls.ROOM_CREATE_SERVLET.toString());

            getServletConfig()
                    .getServletContext()
                    .getRequestDispatcher(ViewUrls.ROOM_PROFILE_JSP.toString())
                    .forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath()
                    + ControllerUrls.LOGIN_SERVLET);
        }
    }


    /**
     * Reads data from creation form and add room to DB
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = request.getParameter(RoomConstants.NUMBER.toString());
        String type = request.getParameter(RoomConstants.TYPE.toString());

        RoomDto roomDto = new RoomDto();
        roomDto.setNumber(number);
        roomDto.setType(type);

        if (!roomService.isExist(roomDto)) {
            roomService.createRoom(roomDto);
            response.sendRedirect(request.getContextPath()
                    + ControllerUrls.ADMIN_ROOMS_SERVLET.toString());
        } else {
            request.setAttribute(ErrorConstants.ERROR.toString(), ErrorConstants.ROOM_EXIST.toString());
        }

        // Show Error Validator
        if (request.getAttribute(ErrorConstants.ERROR.toString()) != null) {
            doGet(request, response);
        }
    }
}
