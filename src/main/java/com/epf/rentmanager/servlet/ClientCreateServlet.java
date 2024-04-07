package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.web.context.support.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.time.LocalDate;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {

    @Autowired
    ClientService clientService;

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

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String prenom = request.getParameter("first_name");
        String nom = request.getParameter("last_name");
        String email = request.getParameter("email");

        try {
            clientService.create(new Client(0, nom, prenom, email, LocalDate.of(2000, 1, 1)));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/users");
    }
    }

