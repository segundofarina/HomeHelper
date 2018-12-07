package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.service.SProviderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static testUtils.AptitudeTestUtils.dummyAptitudes;
import static testUtils.SProviderTestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class SProviderServiceImplTest {

    @Mock
    private SProviderDao sProviderDaoMock;


    @Mock
    private AptitudeDao aptitudeDaoMock;


    @InjectMocks
    private SProviderServiceImpl sProviderService;

    @Test
    public void createTest() {
        SProvider expected = dummySProvider();
        when(sProviderDaoMock.create(expected.getId(), expected.getDescription()))
                .thenReturn(Optional.ofNullable(expected))
                .thenReturn(Optional.empty());

        SProvider actual = sProviderService.create(expected.getId(), expected.getDescription());

        assertEqualsSProviders(expected, actual);

        SProvider shouldBeNull = sProviderService.create(4, expected.getDescription());
        assertNull(shouldBeNull);

        verify(sProviderDaoMock, times(2)).create(anyInt(), anyString());
    }

    @Test
    public void getBylatLng(){
        when(sProviderDaoMock.getServiceProviders())
                .thenReturn(dummySProviders(5))
                .thenReturn(dummySProviders(5));
        assertEquals(5, sProviderService.getServiceProvidersByNeighborhood(-34.577873 , -58.421153,100,1,10).getList().size());

        assertEquals(0, sProviderService.getServiceProvidersByNeighborhood(-34.524597 , -58.502428,100,1,10).getList().size());
    }

//    @Test
//    public void getServiceProviders() {
//        List<SProvider> sProviderSet = dummySProviders(10);
//
//        when(sProviderDaoMock.getServiceProviders()).thenReturn(sProviderSet);
//
//        List<SProvider> actual = sProviderService.getServiceProviders();
//
//        assertTrue(actual.containsAll(sProviderSet));
//        assertTrue(sProviderSet.containsAll(actual));
//
//        verify(sProviderDaoMock, times(1)).getServiceProviders();
//
//    }


    @Test
    public void getServiceProviderWithUserId() {
        SProvider expected = dummySProvider();

        when(sProviderDaoMock.getServiceProviderWithUserId(expected.getId())).thenReturn(Optional.ofNullable(expected));

        SProvider actual = sProviderService.getServiceProviderWithUserId(expected.getId());

        assertEqualsSProviders(actual, expected);

        verify(sProviderDaoMock, times(1)).getServiceProviderWithUserId(anyInt());

    }



    @Test
    public void getReviewsOfServiceProvider() {

        SProvider sProvider = dummySProvider();

        Set<Aptitude> aptitudes = dummyAptitudes(10);

        when(sProviderDaoMock.getServiceProviderWithUserId(sProvider.getId())).thenReturn(Optional.ofNullable(sProvider));

        when(aptitudeDaoMock.getAptitudesOfUser(sProvider.getId())).thenReturn(aptitudes);

        Set<Review> expected = new HashSet<>();

        for (Aptitude aptitude : aptitudes) {
            expected.addAll(aptitude.getReviews());
        }

        Set<Review> actual = sProviderService.getReviewsOfServiceProvider(sProvider.getId());

        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));

        verify(aptitudeDaoMock, times(1)).getAptitudesOfUser(anyInt());

        verify(sProviderDaoMock, times(1)).getServiceProviderWithUserId(sProvider.getId());

    }

    @Test
    public void getAptitudesOfUser() {

        SProvider sProvider = dummySProvider();

        Set<Aptitude> expected = dummyAptitudes(20);

        when(aptitudeDaoMock.getAptitudesOfUser(sProvider.getId())).thenReturn(expected);

        Set<Aptitude> actual = sProviderService.getAptitudesOfUser(sProvider.getId());

        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));

        verify(aptitudeDaoMock, times(1)).getAptitudesOfUser(sProvider.getId());

    }

}
