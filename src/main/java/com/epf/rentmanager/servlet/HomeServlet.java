package com.epf.rentmanager.servlet;

import com.epf.rentmanager.*;
import com.epf.rentmanager.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	@Autowired
	VehicleService vehicleService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("nb",String.valueOf(vehicleService.count()));
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);


	}

}

