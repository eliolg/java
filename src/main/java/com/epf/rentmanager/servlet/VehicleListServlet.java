package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.service.*;
import com.epf.rentmanager.model.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet {

    VehicleService vehicleService = new VehicleService();
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Vehicle> list = vehicleService.findAll();
            System.out.println(list.get(0).id());
            request.setAttribute("vehicles", vehicleService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
    }


}

