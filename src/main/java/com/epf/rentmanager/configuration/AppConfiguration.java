package com.epf.rentmanager.configuration;

import com.epf.rentmanager.persistence.*;
import org.springframework.context.annotation.*;

import java.sql.*;

@Configuration
@ComponentScan({ "com.epf.rentmanager.service", "com.epf.rentmanager.dao",
        "com.epf.rentmanager.persistence" }) // packages dans lesquels chercher les beans
public class AppConfiguration {}

