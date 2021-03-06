package com.it.academy.controllers.rooms;

import com.it.academy.common.ControllerUrls;
import com.it.academy.common.ObjContainer;
import com.it.academy.constants.*;
import com.it.academy.controllers.RequestValidator;
import com.it.academy.common.ViewUrls;
import com.it.academy.dto.BookingUserDto;
import com.it.academy.dto.CollectionDto;
import com.it.academy.dto.LoginDto;
import com.it.academy.dto.RoomDto;
import com.it.academy.service.BookingService;
import com.it.academy.service.PaginationService;
import com.it.academy.service.RoomService;
import com.it.academy.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class RoomServlet configures bookings in particular room
 */
@WebServlet({"/room"})  //  ROOM_SERVLET
public class RoomServlet extends HttpServlet {

    private static final long serialVersionUID = 5L;
    private BookingService bookingService;
    private RoomService roomService;
    private PaginationService<BookingUserDto> paginationService;
    private RequestValidator requestValidator;

    public RoomServlet() {
        super();
        bookingService = ObjContainer.getInstance().getBookingService();
        roomService = ObjContainer.getInstance().getRoomService();
        paginationService =  ObjContainer.getInstance().getPaginationServices().get(PaginationConstants.BOOKING_USER_PAGE.toString());
        requestValidator = ObjContainer.getInstance().getRequestValidator();
    }

    /**
     * Shows the bookings in particular room
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (requestValidator.isValid(request)) {
            RoomDto roomDto = new RoomDto();
            String number = request.getParameter(RoomConstants.NUMBER.toString());
            if (number == null){
                number = (String) request.getSession().getAttribute(RoomConstants.NUMBER.toString());
            }
            roomDto.setNumber(number);
            roomDto = roomService.fillRoomDtoInfo(roomDto);

            CollectionDto<BookingUserDto> bookings = bookingService.getFutureBookingUserCollection(roomDto);

            if(bookings == null)
                request.setAttribute(ErrorConstants.ERROR.toString(), ErrorConstants.NO_BOOKINGS.toString());
            else {
                String pageOffset = request.getParameter(PaginationConstants.PAGE_OFFSET.toString());
                String page = request.getParameter(PaginationConstants.PAGE.toString());

                bookings = paginationService.updateCollection(bookings, pageOffset, page);
                request.setAttribute(BookingConstants.BOOKINGS.toString(), bookings);
            }

            request.setAttribute(RoomConstants.ROOM_DTO.toString(), roomDto);

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
