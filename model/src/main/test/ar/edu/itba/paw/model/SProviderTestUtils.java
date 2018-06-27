package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SProviderTestUtils {

    public SProviderTestUtils(){

    }

    public static SProvider dummySProvider(){
        Random r = new Random();
        return new SProvider(UserTestUtils.dummyUser(),r.toString(),new HashSet<Aptitude>(),new HashSet<WorkingZone>());
    }

    public static List<SProvider> dummySProviders(int size){
        List<SProvider> sProviderList = new ArrayList<>();

        for(int i=0; i < size ; i++){
            sProviderList.add(dummySProvider());
        }

        return sProviderList;
    }

    public static void assertEqualsSProviders(SProvider expected, SProvider actual){
        assertEquals(expected,actual);
        assertEquals(expected.getDescription(),actual.getDescription());
        assertEquals(expected.getAptitudes(),actual.getAptitudes());
        assertEquals(expected.getWorkingZones(),actual.getWorkingZones());
        assertEquals(expected.getId(),actual.getId());
        UserTestUtils.assertEqualsUsers(expected.getUser(),actual.getUser());
    }
}
