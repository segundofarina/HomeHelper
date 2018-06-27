//package ar.edu.itba.paw;
//
//import ar.edu.itba.paw.interfaces.daos.WZoneDao;
//import ar.edu.itba.paw.interfaces.services.WorkingZonesService;
//import ar.edu.itba.paw.model.*;
//import ar.edu.itba.paw.service.NeighborhoodServiceImpl;
//import ar.edu.itba.paw.service.WorkingZonesServiceImpl;
//import jdk.nashorn.internal.runtime.WithObject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static ar.edu.itba.paw.model.NeighborhoodTestUtils.assertEqualsNeighborhoods;
//import static ar.edu.itba.paw.model.NeighborhoodTestUtils.dummyNeighborhoods;
//import static ar.edu.itba.paw.model.WorkingZoneTestUtils.dummyWorkingZone;
//import static ar.edu.itba.paw.model.WorkingZoneTestUtils.dummyWorkingZones;
//import static junit.framework.TestCase.assertTrue;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class WorkingZoneServiceImplTest {
//
//    @Mock
//    private WZoneDao workingZoneDaoMock;
//
//    @InjectMocks
//    private WorkingZonesServiceImpl workingZonesService;
//
//
//    @Test
//    public void insertWorkingZoneOfProvider() {
//        WorkingZone expected = dummyWorkingZone();
//
//        when(workingZoneDaoMock.insertWorkingZoneOfProvider(expected.getUser().getId(),expected.getNeighborhood().getNgId())).thenReturn(true);
//
//        workingZonesService.insertWorkingZoneOfProvider(expected.getUser().getId(),expected.getNeighborhood().getNgId());
//
//        verify(workingZoneDaoMock, times(1)).insertWorkingZoneOfProvider(anyInt(),anyInt());
//    }
//
//    @Test
//    public void getWorkingZonesOfProvider() {
//        List<Neighborhood> neighborhoodList = dummyNeighborhoods(20);
//        when(workingZoneDaoMock.getWorkingZonesOfProvider(1)).thenReturn(neighborhoodList);
//
//        List<Neighborhood> actual = workingZonesService.getWorkingZonesOfProvider(1);
//
//        for(int i = 0 ; i < neighborhoodList.size(); i ++){
//            assertEqualsNeighborhoods(actual.get(i),neighborhoodList.get(i));
//        }
//
//        verify(workingZoneDaoMock, times(1)).getWorkingZonesOfProvider(anyInt());
//
//    }
//
//    @Test
//    public void removeWorkingZoneOfProvider() {
//        WorkingZone expected = dummyWorkingZone();
//        when(workingZoneDaoMock.removeWorkingZoneOfProvider(expected.getUser().getId(),expected.getNeighborhood().getNgId())).thenReturn(true);
//
//        boolean actual = workingZonesService.removeWorkingZoneOfProvider(expected.getUser().getId(),expected.getNeighborhood().getNgId());
//
//        assertTrue(actual);
//
//        verify(workingZoneDaoMock, times(1)).removeWorkingZoneOfProvider(expected.getUser().getId(),expected.getNeighborhood().getNgId());
//
//    }
//}
