package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.service.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.time.*;
import java.time.LocalDate;
import java.time.format.*;
import java.util.*;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    ReservationService resService = new ReservationService();
    ClientService clientService = new ClientService();
    VehicleService vehicleService = new VehicleService();

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Client> listClient = clientService.findAll();
            List<Vehicle> listVehicle = vehicleService.findAll();
            //System.out.println(list.get(0).id());
            request.setAttribute("users", listClient);
            request.setAttribute("vehicles", listVehicle);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String car = request.getParameter("car");
        String client = request.getParameter("client");

        System.out.println(car);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate debut = LocalDate.parse(request.getParameter("begin"), formatter);
        LocalDate fin = LocalDate.parse(request.getParameter("end"), formatter);

//        String[] model = car.split(" ");
//        String[] name = client.split(" ");

        try {
            resService.create(new Reservation(0, clientService.findById(Long.valueOf(client)), vehicleService.findById(Long.valueOf(car)), debut, fin));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/rents");
    }
}