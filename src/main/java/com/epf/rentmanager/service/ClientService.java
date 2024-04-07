package com.epf.rentmanager.service;

import java.sql.*;
import java.time.*;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;

import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.context.support.*;

import javax.servlet.*;

@Service
public class ClientService {
	@Autowired
	private ClientDao clientDao;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	
	public long create(Client client) throws ServiceException, DaoException {
		LocalDate currentDate = LocalDate.now();
		if (ChronoUnit.YEARS.between(client.naissance(), currentDate) < 18 ){
			throw new ServiceException("L'age d'un client doit être supérieur à 18 ans");
		}

		for (Client clients : findAll()){
			if (client.email().equals(clients.email())){
				throw new ServiceException("Email déjà utilisé");
			}
		}

		if (client.nom().length() < 2 || client.prenom().length() < 2){
			throw new ServiceException("Le nom et le prénom d'un client doivent faire au moins 3 caractères");
		}

		if (client.nom().isEmpty() || client.prenom().isEmpty()) {
			throw new ServiceException("erreur service");
		}

		return clientDao.create(new Client(client.id(), client.nom().toUpperCase(), client.prenom(), client.email(), client.naissance()));
	}

	public Client findById(long id) throws ServiceException, DaoException {
		if (clientDao.findById(id).isPresent()){
			return clientDao.findById(id).get();
		} else {
			throw new ServiceException("erreur service");
		}
	}

	public Client findByName(String nom, String prenom) throws ServiceException, DaoException {
		if (clientDao.findByName(nom, prenom).isPresent()){
			return clientDao.findByName(nom, prenom).get();
		} else {
			throw new ServiceException("erreur service");
		}
	}

	public List<Client> findAll() throws ServiceException, DaoException {
		return clientDao.findAll();
	}

	public int count(){
		return clientDao.count();
	}

	public int delete(Client client) throws DaoException, ServiceException, SQLException {
		for (Reservation reservation : reservationService.findAll()){
			if (reservation.client().id() == client.id()){
				reservationService.delete(reservation);
			}
		}

		clientDao.delete(client);
		return 0;
	}
	
}
