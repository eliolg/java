package com.epf.rentmanager.model;

import java.time.*;

public record Vehicle (int id, String constructeur, String modele, int nb_places) {}
