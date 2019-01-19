package com.it.academy.controllers;

import com.it.academy.common.ControllerUrls;
import com.it.academy.common.ObjContainer;
import com.it.academy.common.RequestValidator;
import com.it.academy.common.ViewUrls;
import com.it.academy.constants.BookingConstants;
import com.it.academy.constants.RoomConstants;
import com.it.academy.constants.UserConstants;
import com.it.academy.dto.BookingUserDto;
import com.it.academy.dto.CollectionDto;
import com.it.academy.dto.RoomDto;
import com.it.academy.entity.Room;
import com.it.academy.service.BookingService;
import com.it.academy.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * RoomServlet configures bookings in particular room
 */
@WebServlet({"/room"})  //  ROOM_SERVLET
public class RoomServlet extends HttpServlet {

    private static final long serialVersionUID = 5L;
    private BookingService bookingService;
    private RoomService roomService;

    public RoomServlet() {
        super();
        bookingService = ObjContainer.getInstance().getBookingService();
        roomService = ObjContainer.getInstance().getRoomService();
    }

    /**
     * Shows the bookings in particular room
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    /**
     * Shows the bookings in particular room
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (RequestValidator.isValid(request)) {
            RoomDto roomDto = new RoomDto();
            roomDto.setIdRoom(Long.parseLong(request.getParameter(RoomConstants.ID.toString())));
            roomDto = roomService.fillRoomDtoInfo(roomDto);

            CollectionDto<BookingUserDto> bookings = bookingService.getFutureBookingUserCollection(roomDto);
            request.setAttribute(BookingConstants.BOOKINGS.toString(), bookings);
            request.setAttribute(RoomConstants.ROOM_DTO.toString(), roomDto);

            if(bookings == null)
                request.setAttribute("error", "There are no bookings!");
            
            getServletConfig()
                    .getServletContext()
                    .getRequestDispatcher(ViewUrls.ROOM_JSP.toString())
                    .forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath()
                    + ControllerUrls.LOGIN_SERVLET);
        }
    }

}
