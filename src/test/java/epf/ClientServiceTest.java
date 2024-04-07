package epf;

import com.epf.rentmanager.dao.*;
import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.service.*;
import org.junit.*;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;

import java.sql.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @InjectMocks
    private ReservationService reservationService;

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private ClientDao clientDao;

    @Mock
    private ReservationDao reservationDao;

    @Mock
    private VehicleDao vehicleDao;




    @Test
    public void findById_should_fail_when_dao_throw_exception() throws DaoException, ServiceException {
        Client client = new Client(0,"Longuet", "Elio", "elio.longuet@xxx.com", LocalDate.of(2002, 4, 29));
        //When
        when(clientDao.findById(-1)).thenThrow(ServiceException.class);

        //Then
        assertThrows(ServiceException.class, () -> clientService.findById(-1));
    }

    @Test
    public void create_should_fail_if_age_under_18() throws ServiceException, DaoException {
        // Given
        Client client = new Client(0,"Longuet", "Elio", "elio.longuet@xxx.com", LocalDate.of(2022, 4, 29));

        // Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    public void create_should_fail_when_email_is_already_used() throws DaoException {
        // Given
        Client client = new Client(0, "Elio", "Longuet", "elio.longuet@xxx.com", LocalDate.of(2002, 4, 29));
        when(clientDao.findAll()).thenReturn(
                List.of(new Client(1, "Smith", "Alice", "elio.longuet@xxx.com", LocalDate.of(2002, 4, 29))));


        // Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    public void create_should_fail_when_name_or_firstname_is_too_short() {
        // Given
        Client client = new Client(0, "E", "L", "elio.longuet@xxx.com", LocalDate.of(2002, 4, 29));

        // Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }



}
