package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;

import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ClientService {
	@Autowired
	private ClientDao clientDao;
	@Autowired
	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
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
