package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.SProviderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SProviderServiceTest {

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
    @Test
    public void getServiceProvidersWithServiceTypeTest() {
     when(sProviderDao.getServiceProviders()).thenReturn(getSproviderList());


     List<SProvider> list=sProviderService.getServiceProvidersWithServiceType(1);
     assertEquals(0,list.size());

    }


    private SProvider getDummySProvider(){
       return new SProvider(getDummyUser(), "My description", new ArrayList<>(),new ArrayList<>());
    }

    private User getDummyUser(){
      return new User("username",10,"password","Name","Lastanme",
              "mail@homtail.com","5491134859403","Siemrevivia 623",new byte[1],true);
    }
    private User getDummyUser(int id){
      return new User("username",id,"password","Name","Lastanme",
          "mail@homtail.com","5491134859403","Siemrevivia 623",new byte[1],true);
    }

    private List<SProvider> getSproviderList(){
     ArrayList<SProvider> list = new ArrayList<>();
      for(int i =0;i<10;i++){
       list.add(getDummySProvider());
      }
      return list;
    }

}
