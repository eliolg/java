package com.epf.rentmanager.ui.cli.main;

import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.service.*;
import com.epf.rentmanager.utils.*;

import java.sql.*;
import java.time.*;

public class cli extends IOUtils {
    private ClientService clientService = new ClientService();
    private VehicleService vehicleService = new VehicleService();

    private ReservationService reservationService = new ReservationService();

    public void createClient() throws ServiceException, DaoException {
        print("Veuillez saisir les paramètres de saisies du client suivant :");

        print("Nom :");
        String name = readString();

        print("Prénom :");
        String prenom = readString();

        print("email :");
        String email = readString();

        LocalDate date = readDate("Veuillez saisir la date de naissance", true);

        clientService.create(new Client(0, name, prenom, email,  date));
        print("Client créé avec succès");
    }

    public void listClient() throws ServiceException, DaoException {
        print("Liste des clients :");
        for (Client client : clientService.findAll()) {
            print(client.toString());
        }
    }

    public void createVehicle() throws ServiceException, DaoException {
        print("Veuillez saisir les paramètres de saisies du véhicule suivant :");

        print("Constructeur :");
        String constructeur = readString();

        print("Modèle :");
        String model = readString();

        print("Nombre de places :");
        int nb_places = readInt();

        vehicleService.create(new Vehicle(0, constructeur, model, nb_places));
        print("Véhicule créé avec succès");
    }

    public void listVehicle() throws ServiceException, DaoException {
        print("Liste des véhicules :");
        for (Vehicle vehicle : vehicleService.findAll()) {
            print(vehicle.toString());
        }
    }

    public void createReservation(){
        print("Veuillez saisir les paramètres de saisies de la réservation suivante :");
        print("Id du client :");
        int id_client = readInt();

        print("Id du véhicule :");
        int id_vehicule = readInt();

        LocalDate date_debut = readDate("Veuillez saisir la date de début", true);

        LocalDate date_fin = readDate("Veuillez saisir la date de fin", true);

        try {
            reservationService.create(new Reservation(0, clientService.findById(id_client), vehicleService.findById(id_vehicule), date_debut, date_fin));
            print("Réservation créée avec succès");
        } catch (ServiceException | DaoException e) {
            print("Erreur lors de la création de la réservation");
        }
    }

    public void listAllReservation() throws ServiceException, DaoException {
        print("Liste des réservations :");
        for (Reservation reservation : reservationService.findAll()) {
            print(reservation.toString());
        }
    }

    public void listReservationByClient() throws ServiceException, DaoException, SQLException {
        print("Veuillez saisir l'id du client :");
        int id_client = readInt();
        print("Liste des réservations du client :");
        for (Reservation reservation : reservationService.findByClientId(id_client)) {
            print(reservation.toString());
            print("Client associé :");
            print(reservation.client().toString());
        }
    }

    public void listReservationByVehicle() throws ServiceException, DaoException {
        print("Veuillez saisir l'id du véhicule :");
        int id_vehicule = readInt();
        print("Liste des réservations du véhicule :");
        for (Reservation reservation : reservationService.findByVehicleId(id_vehicule)) {
            print(reservation.toString());
        }
    }

    public void deleteReservation() throws ServiceException, DaoException, SQLException {
        print("Voici la liste des réservations :");
        listAllReservation();
        print("Veuillez saisir l'id de la réservation à supprimer :");
        int id_resa = readInt();
        reservationService.delete(id_resa);
        print("Réservation supprimée avec succès");

        //Quand client delete, delete aussi la reservation associée ?? -> Choix laissé aux étudiants
    }

    public void count(){
        print(String.valueOf(vehicleService.count()));
    }

    public void menu(){
        int choice;
        do {
            System.out.println("Choisissez une option: ");
            print("1. Créer un client");
            print("2. Lister les clients");
            print("3. Créer un véhicule");
            print("4. Lister les véhicules");
            print("5. Créer une réservation");
            print("6. Lister toutes les réservations");
            print("7. Lister les réservations d'un client");
            print("8. Lister les réservations d'un véhicule");
            print("9. Supprimer une réservation");
            print("10. Quitter");
            choice = readInt();

            switch (choice) {
                case 1 -> {
                    try {
                        createClient();
                    } catch (ServiceException | DaoException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    try {
                        listClient();
                    } catch (ServiceException | DaoException e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> {
                    try {
                        createVehicle();
                    } catch (ServiceException | DaoException e) {
                        e.printStackTrace();
                    }
                }
                case 4 -> {
                    try {
                        listVehicle();
                    } catch (ServiceException | DaoException e) {
                        e.printStackTrace();
                    }
                }
                case 5 -> createReservation();
                case 6 -> {
                    try {
                        listAllReservation();
                    } catch (ServiceException | DaoException e) {
                        e.printStackTrace();
                    }
                }
                case 7 -> {
                    try {
                        listReservationByClient();
                    } catch (ServiceException | DaoException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 8 -> {
                    try {
                        listReservationByVehicle();
                    } catch (ServiceException | DaoException e) {
                        e.printStackTrace();
                    }
                }
                case 9 -> {
                    try {
                        deleteReservation();
                    } catch (ServiceException | DaoException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 10 -> System.out.println("Sortie du menu");
                case 11 -> count();


                default -> print("Choix invalide");
            }
        }while (choice != 10);
    }

}
