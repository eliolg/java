package com.epf.rentmanager.servlet;

import com.epf.rentmanager.configuration.*;
import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.web.context.support.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet {

    @Autowired
    ReservationService resService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Reservation> list = resService.findAll();
            //System.out.println(list.get(0).id());
            request.setAttribute("rents", resService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
    }
}




