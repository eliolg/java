package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;

import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;


public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;

	public ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}

		return instance;
	}
	
	
	public long create(Client client) throws ServiceException, DaoException {
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
	
}
