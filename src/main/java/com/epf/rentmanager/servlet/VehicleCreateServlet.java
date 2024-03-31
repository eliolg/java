package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.service.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {

    VehicleService vehicleService = new VehicleService();

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String marque = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        int nb_places = Integer.valueOf(request.getParameter("seats"));

        try {
            vehicleService.create(new Vehicle(0, marque, modele, nb_places));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/cars");
    }


}
