package com.epf.rentmanager.servlet;

import com.epf.rentmanager.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.context.support.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	@Autowired
	VehicleService vehicleService;

	@Autowired
	ReservationService reservationService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Serial
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("nb",String.valueOf(vehicleService.count()));
		request.setAttribute("nb_res", String.valueOf(reservationService.count()));
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);


	}

}

