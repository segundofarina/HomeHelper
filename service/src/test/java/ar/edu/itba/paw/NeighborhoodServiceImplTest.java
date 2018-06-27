package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.NeighborhoodDao;
import ar.edu.itba.paw.model.Neighborhood;
import ar.edu.itba.paw.service.NeighborhoodServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static testUtils.NeighborhoodTestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class NeighborhoodServiceImplTest {

    @Mock
    private NeighborhoodDao neighborhoodDaoMock;

    @InjectMocks
    private NeighborhoodServiceImpl neighborhoodService;

    @Test
    public void insertNeighborhood() {
        Neighborhood expected = dummyNeighborhood();
        when(neighborhoodDaoMock.insertNeighborhood(expected.getNgname())).thenReturn(expected.getNgId());

        int actual = neighborhoodService.insertNeighborhood(expected.getNgname());

        assertEquals(actual,expected.getNgId());

        verify(neighborhoodDaoMock, times(1)).insertNeighborhood(anyString());
    }

    @Test
    public void getAllNeighborhoods() {
        List<Neighborhood> neighborhoodList = dummyNeighborhoods(20);
        when(neighborhoodDaoMock.getAllNeighborhoods()).thenReturn(neighborhoodList);

        List<Neighborhood> actual = neighborhoodService.getAllNeighborhoods();

        for(int i = 0 ; i < neighborhoodList.size(); i ++){
            assertEqualsNeighborhoods(actual.get(i),neighborhoodList.get(i));
        }
    }
}
