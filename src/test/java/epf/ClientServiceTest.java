package epf;

import com.epf.rentmanager.dao.*;
import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.service.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @Test
    public void findById_should_fail_when_dao_throw_exception() throws DaoException, ServiceException {
        Client client = new Client(0,"Longuet", "Elio", "elio.longuet@xxx.com", LocalDate.of(2002, 4, 29));
        //When
        when(clientDao.findById(-1)).thenThrow(ServiceException.class);

        //Then
        assertThrows(ServiceException.class, () -> clientService.findById(-1));
    }
}
