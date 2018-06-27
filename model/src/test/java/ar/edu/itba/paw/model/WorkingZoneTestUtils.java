package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WorkingZoneTestUtils {

    public WorkingZoneTestUtils(){

    }

    public static WorkingZone dummyWorkingZone(){
        return new WorkingZone(SProviderTestUtils.dummySProvider(),NeighborhoodTestUtils.dummyNeighborhood());
    }

    public static List<WorkingZone> dummyWorkingZones(int size) {
        List<WorkingZone> workingZoneList = new ArrayList<WorkingZone>(size);

        for (int i = 0; i < size; i++)
            workingZoneList.add(dummyWorkingZone());

        return workingZoneList;
    }

    public static void assertEqualsWorkingZones(WorkingZone expected, WorkingZone actual) {
        assertEquals(expected, actual);
        NeighborhoodTestUtils.assertEqualsNeighborhoods(expected.getNeighborhood(),actual.getNeighborhood());
        SProviderTestUtils.assertEqualsSProviders(expected.getUser(),actual.getUser());
    }
}
