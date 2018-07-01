package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.daos.WZoneDao;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
    private WZoneDao wZoneDaoMock;

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
    public void getServiceProviders() {
        Set<SProvider> sProviderSet = dummySProviders(10);

        when(sProviderDaoMock.getServiceProviders()).thenReturn(sProviderSet);

        Set<SProvider> actual = sProviderService.getServiceProviders();

        assertTrue(actual.containsAll(sProviderSet));
        assertTrue(sProviderSet.containsAll(actual));

        verify(sProviderDaoMock, times(1)).getServiceProviders();

    }


    @Test
    public void getServiceProviderWithUserId() {
        SProvider expected = dummySProvider();

        when(sProviderDaoMock.getServiceProviderWithUserId(expected.getId())).thenReturn(Optional.ofNullable(expected));

        SProvider actual = sProviderService.getServiceProviderWithUserId(expected.getId());

        assertEqualsSProviders(actual, expected);

        verify(sProviderDaoMock, times(1)).getServiceProviderWithUserId(anyInt());

    }


    @Test
    public void getServiceProvidersWorkingIn() {
        List<SProvider> expected = new ArrayList<>();
        expected.addAll(dummySProviders(15));

        when(wZoneDaoMock.getServiceProvidersWorkingIn(1)).thenReturn(expected);

        List<SProvider> actual = sProviderService.getServiceProvidersWorkingIn(1);

        for (int i = 0; i < expected.size(); i++) {
            assertEqualsSProviders(actual.get(i), expected.get(i));
        }

        verify(wZoneDaoMock, times(1)).getServiceProvidersWorkingIn(anyInt());
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
