package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.*;
import org.springframework.stereotype.*;

@Repository
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";

	private static final String FIND_VEHICLE_BY_MODELE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE modele=? AND constructeur=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle;";

	
	public long create(Vehicle vehicle) throws DaoException {
		int id = 0;
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement stmt = connexion.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)){
			stmt.setString(1, vehicle.constructeur());
			stmt.setString(2, vehicle.modele());
			stmt.setInt(3, vehicle.nb_places());
			stmt.execute();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()){
				id = resultSet.getInt(1);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return id;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		int nb_modified_ligns = 0;
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement stmt = connexion.prepareStatement(DELETE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)){
			stmt.setInt(1, vehicle.id());
			//mouaisssss
			nb_modified_ligns = stmt.executeUpdate(); //nombre de ligne modifié
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return nb_modified_ligns;
	}


	public Optional<Vehicle> findById(long id) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement stmt = connexion.prepareStatement(FIND_VEHICLE_QUERY)){
			stmt.setLong(1, id);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()){
				return Optional.of(new Vehicle(resultSet.getInt("id"), resultSet.getString("constructeur"), resultSet.getString("modele"), resultSet.getInt("nb_places")));
			}
		}catch(Exception e){
			throw new DaoException(e.getMessage());
		}
		return Optional.empty();

	}

	public Optional<Vehicle> findByModele(String modele, String constructeur) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement stmt = connexion.prepareStatement(FIND_VEHICLE_BY_MODELE_QUERY)){
			stmt.setString(1, modele);
			stmt.setString(2, constructeur);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()){
				return Optional.of(new Vehicle(resultSet.getInt("id"), resultSet.getString("constructeur"), resultSet.getString("modele"), resultSet.getInt("nb_places")));
			}
		}catch(Exception e){
			throw new DaoException(e.getMessage());
		}
		return Optional.empty();

	}


	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> list = new ArrayList<>();
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement stmt = connexion.prepareStatement(FIND_VEHICLES_QUERY)){
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()){
				list.add(new Vehicle(resultSet.getInt("id"), resultSet.getString("constructeur"), resultSet.getString("modele"), resultSet.getInt("nb_places")));

			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return list;
	}

	public int count(){
		int count= 0;
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement stmt = connexion.prepareStatement(COUNT_VEHICLES_QUERY, Statement.RETURN_GENERATED_KEYS)){
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()){
				count = resultSet.getInt(1);
			}

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return count;
		}
		
	}

	


