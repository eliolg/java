package com.epf.rentmanager.servlet;

import com.epf.rentmanager.service.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	VehicleService vehicleService = new VehicleService();
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

