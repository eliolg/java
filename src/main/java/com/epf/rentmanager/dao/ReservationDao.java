package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.service.*;
import org.springframework.stereotype.*;

@Repository
public class ReservationDao {

	private ClientService c_service = new ClientService();
	private VehicleService v_service = new VehicleService();

	private static ReservationDao instance = null;
	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		int id = 0;
		try (Connection connexion = ConnectionManager.getConnection();
			PreparedStatement stmt = connexion.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)){
			stmt.setInt(1, reservation.client().id());
			stmt.setInt(2, reservation.vehicle().id());
			stmt.setDate(3, Date.valueOf(reservation.debut()));
			stmt.setDate(4, Date.valueOf(reservation.fin()));
			stmt.execute();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()){
				id = resultSet.getInt(1);
			}
		}catch(Exception e){
			throw new DaoException(e.getMessage());
		}
		return id;
	}
	
	public long delete(int id) throws DaoException, SQLException {
		long nb_modified_ligns = 0;
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement stmt = connexion.prepareStatement(DELETE_RESERVATION_QUERY)) {
			stmt.setInt(1, id);
			nb_modified_ligns = stmt.executeUpdate();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return nb_modified_ligns;
	}


	
	public List<Reservation> findResaByClientId(long clientId) throws SQLException {
		List<Reservation> list = new ArrayList<>();
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement stmt = connexion.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {
			stmt.setInt(1, (int) clientId);
			ResultSet resultSet = stmt.executeQuery();
			while(resultSet.next()){
				list.add(new Reservation(resultSet.getInt("id"), c_service.findById((int) clientId), v_service.findById(resultSet.getInt("vehicle_id")), resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate()));
			}
		} catch (SQLException e) {
			throw new SQLException(e);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}

		return list;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> list = new ArrayList<>();
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement stmt = connexion.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {
			stmt.setInt(1, (int) vehicleId);
			ResultSet resultSet = stmt.executeQuery();
			while(resultSet.next()){
				list.add(new Reservation(resultSet.getInt("id"), c_service.findById(resultSet.getInt("client_id")), v_service.findById((int) vehicleId), resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate()));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}

		return list;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> list = new ArrayList<>();
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement stmt = connexion.prepareStatement(FIND_RESERVATIONS_QUERY)) {
			ResultSet resultSet = stmt.executeQuery();
			while(resultSet.next()){
				list.add(new Reservation(resultSet.getInt("id"), c_service.findById(resultSet.getInt("client_id")), v_service.findById(resultSet.getInt("vehicle_id")), resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate()));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}

		return list;
	}
	}

