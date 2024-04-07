package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.*;
import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.time.temporal.*;
import java.util.*;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public long create(Reservation reservation) throws ServiceException, DaoException {
        for (Reservation resa : findAll()){
            if (resa.vehicle().equals(reservation.vehicle())){
                if(!reservation.fin().isBefore(resa.debut()) && reservation.debut().isAfter(resa.fin())){
                    throw new ServiceException("Cette voiture est déjà réservée sur cet intervalle de temps");
                }
            }
        }

        if (ChronoUnit.DAYS.between(reservation.debut(), reservation.fin())>30){
            throw new ServiceException("Une voiture ne peux pas être réservé 30 jours de suite sans pause");
        }

        if (reservation.vehicle().id() <= 0 || reservation.client().id() <= 0) {
            throw new ServiceException("erreur service");
        }
        return reservationDao.create(reservation);
    }

    public List<Reservation> findByClientId(long id) throws ServiceException, DaoException, SQLException {
        return reservationDao.findResaByClientId(id);
    }

    public List<Reservation> findByVehicleId(long id) throws ServiceException, DaoException {
        return reservationDao.findResaByVehicleId(id);
    }

    public List<Reservation> findAll() throws ServiceException, DaoException {
        return reservationDao.findAll();
    }

    public void delete(int id) throws ServiceException, DaoException, SQLException {
        reservationDao.delete(id);
    }

    public int count(){
        return reservationDao.count();
    }

    public int delete(Reservation reservation) throws SQLException, DaoException {
        reservationDao.delete(reservation.id());
        return 0;
    }
}
