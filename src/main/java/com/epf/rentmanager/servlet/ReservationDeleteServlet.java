package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.*;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

@WebServlet("/rents/delete")
public class ReservationDeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            reservationService.delete(reservationService.findById(id));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/rents");
    }
}