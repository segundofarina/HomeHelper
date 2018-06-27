package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.service.SProviderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;
import java.util.Set;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static testUtils.SProviderTestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class SProviderServiceImplTest{

    @Mock
    private SProviderDao sProviderDaoMock;

    @InjectMocks
    private SProviderServiceImpl sProviderService;

    @Test
    public void createTest(){
        SProvider expected = dummySProvider();
        when(sProviderDaoMock.create(expected.getId(),expected.getDescription()))
                .thenReturn(Optional.ofNullable(expected))
                .thenReturn(Optional.empty());

        SProvider actual = sProviderService.create(expected.getId(),expected.getDescription());

        assertEqualsSProviders(expected, actual);

        SProvider shouldBeNull = sProviderService.create(4,expected.getDescription());
        assertNull(shouldBeNull);

        verify(sProviderDaoMock, times(2)).create(anyInt(),anyString());
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

        assertEqualsSProviders(actual,expected);

        verify(sProviderDaoMock, times(1)).getServiceProviderWithUserId(anyInt());

    }


    @Test
    public void getServiceProvidersWorkingIn() {
        //Set<SProvider> expected = dummySProviders(15);

        //when(sProviderDaoMock.getServiceProviders()).thenReturn(expected);

        //SProvider actual = sProviderService.getServiceProvidersWorkingIn();

        //assertEqualsSProviders(actual,expected);

        //verify(sProviderDaoMock, times(1)).getServiceProviderWithUserId(anyInt());
    }

    @Test
    public void getServiceProvidersByNeighborhoodAndServiceType() {
    }

    @Test
    public void getReviewsOfServiceProvider() {
    }

    @Test
    public void getAptitudesOfUser() {
    }

    @Test
    public void getLatestReviewsOfServiceProvider() {
    }
}
