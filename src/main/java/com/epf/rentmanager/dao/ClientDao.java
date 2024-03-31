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
import java.util.Optional;

import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.*;
import org.springframework.stereotype.*;

import java.time.LocalDateTime.*;

@Repository
public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";

	private static final String FIND_CLIENT_BY_NAME_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE nom=? AND prenom=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException{
		int id = 0;
		try (Connection connexion = ConnectionManager.getConnection();
			PreparedStatement stmt = connexion.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)){
			stmt.setString(1, client.nom());
			stmt.setString(2, client.prenom());
			stmt.setString(3, client.email());
			stmt.setDate(4, Date.valueOf(client.naissance()));
			stmt.execute();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()){
				id = resultSet.getInt(1);
			}
		}catch(SQLException e){
			throw new DaoException(e.getMessage());
			}
		return id;
	}
	
	public long delete(Client client) throws DaoException {
		int nb_modified_ligns = 0;
		try (Connection connexion = ConnectionManager.getConnection();
			PreparedStatement stmt = connexion.prepareStatement(DELETE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)){
			stmt.setInt(1, client.id());
			//mouaisssss
			nb_modified_ligns = stmt.executeUpdate(); //nombre de ligne modifié
		}catch(Exception e){
		}
		return nb_modified_ligns;
	}

	public Optional<Client> findById(long id) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			PreparedStatement stmt = connexion.prepareStatement(FIND_CLIENT_QUERY)){
			stmt.setLong(1, id);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()){
				return Optional.of(new Client((int)id,
				resultSet.getString("nom"),
				resultSet.getString("prenom"),
				resultSet.getString("email"),
						resultSet.getDate("naissance").toLocalDate()));

			}
		}catch(Exception e){
		}
		return Optional.empty();
	}

	public Optional<Client> findByName(String nom, String prenom) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement stmt = connexion.prepareStatement(FIND_CLIENT_BY_NAME_QUERY)){
			stmt.setString(1, nom);
			stmt.setString(2, prenom);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()){
				return Optional.of(new Client(resultSet.getInt("id"),
						resultSet.getString("nom"),
						resultSet.getString("prenom"),
						resultSet.getString("email"),
						resultSet.getDate("naissance").toLocalDate()));

			}
		}catch(Exception e){
		}
		return Optional.empty();
	}

	public List<Client> findAll() throws DaoException {
		List<Client> list = new ArrayList<>();
		try (Connection connexion = ConnectionManager.getConnection();
			PreparedStatement stmt = connexion.prepareStatement(FIND_CLIENTS_QUERY)){
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()){

				list.add(new Client(resultSet.getInt("id"),
						resultSet.getString("nom"),
						resultSet.getString("prenom"),
						resultSet.getString("email"),
						resultSet.getDate("naissance").toLocalDate()));

			}
		}catch(Exception e){
		}
		return list;
	}

}
