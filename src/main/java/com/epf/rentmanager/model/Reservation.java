package com.epf.rentmanager.model;

//import jdk.internal.net.http.*;

import java.time.*;

public record Reservation (int id, Client client, Vehicle vehicle, LocalDate debut, LocalDate fin) {}
