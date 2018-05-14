package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.service.SProviderServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;

import javax.sql.DataSource;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


public class SProviderServiceTest {

    @Mock
    private SProviderDao sProviderDao;

    @InjectMocks
   SProviderServiceImpl sProviderService;

    @Test
    public void createTest(){

    }
    @Test
    public void getServiceProvidersTest(){

    }
    @Test
    public void getServiceProviderWithUserIdTest(){

    }
    @Test
    public void getCalificationOfServiceProviderTest(){

    }
    @Test
    public void addReviewToAptitudeTest(){

    }
    @Test
    public void addAptitudeTest(){

    }
    @Test
    public void getServiceProvidersWithServiceTypeTest() {
//        sProviderService = new SProviderServiceImpl();
//        assertNotNull("Service Provier service is null",sProviderService);
//        List<SProvider> list = sProviderService.getServiceProvidersWithServiceType(1);
//        assertFalse(list.isEmpty());
    }

}
