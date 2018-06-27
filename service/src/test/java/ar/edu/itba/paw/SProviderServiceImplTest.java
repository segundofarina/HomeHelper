package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.service.SProviderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SProviderServiceImplTest {

    @Mock
    private SProviderDao sProviderDao;

    @InjectMocks
    private SProviderServiceImpl sProviderService;

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


}
