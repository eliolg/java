package com.epf.rentmanager.service;

import java.sql.*;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class VehicleService {

	private final VehicleDao vehicleDao;
	@Autowired
	public VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	@Autowired
	private ReservationService reservationService;

	
	
	public long create(Vehicle vehicle) throws ServiceException, DaoException {
		if (vehicle.constructeur().isEmpty() || vehicle.modele().isEmpty() || vehicle.nb_places() <= 1 || vehicle.nb_places() > 9 ){
			throw new ServiceException("Une voiture doit avoir un modèle et un constructeur, son nombre de place doit être compris entre 2 et 9");
		}
		return vehicleDao.create(vehicle);
	}

	public Vehicle findById(long id) throws ServiceException, DaoException {
		if(vehicleDao.findById(id).isPresent()){
			return vehicleDao.findById(id).get();
		} else {
			throw new ServiceException("erreur service");
		}
	}

	public Vehicle findByModele(String modele, String constructeur) throws ServiceException, DaoException {
		if(vehicleDao.findByModele(modele, constructeur).isPresent()){
			return vehicleDao.findByModele(modele, constructeur).get();
		} else {
			throw new ServiceException("erreur service");
		}
	}

	public List<Vehicle> findAll() throws ServiceException, DaoException {
		return vehicleDao.findAll();
	}

	public int count(){
		return vehicleDao.count();
	}

	public int delete(Vehicle vehicle) throws ServiceException, DaoException, SQLException {
		for (Reservation reservation : reservationService.findAll()){
			if (reservation.vehicle().id() == vehicle.id()){
				reservationService.delete(reservation);
			}
		}

		vehicleDao.delete(vehicle);
		return 0;
	}
	
}
