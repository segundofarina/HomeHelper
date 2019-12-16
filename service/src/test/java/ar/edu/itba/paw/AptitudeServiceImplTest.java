package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.service.AptitudeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static testUtils.AptitudeTestUtils.assertEqualsAptitudes;
import static testUtils.AptitudeTestUtils.dummyAptitude;

@RunWith(MockitoJUnitRunner.class)
public class AptitudeServiceImplTest {

    @Mock
    private AptitudeDao aptitudeDaoMock;

    @InjectMocks
    private AptitudeServiceImpl aptitudeService;


    @Test
    public void getAptitude() {
        Aptitude expected = dummyAptitude();

        when(aptitudeDaoMock.getAptitude(expected.getId())).thenReturn(Optional.ofNullable(expected));

        Optional<Aptitude> actual = aptitudeService.getAptitude(expected.getId());

        assertEqualsAptitudes(actual.get(), expected);

        verify(aptitudeDaoMock, times(1)).getAptitude(anyInt());
    }
}
