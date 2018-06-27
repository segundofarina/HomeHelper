package testUtils;

import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.CoordenatesPoint;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.WorkingZone;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class SProviderTestUtils {

    public SProviderTestUtils(){

    }

    public static SProvider dummySProvider(){
        Random r = new Random();
        return new SProvider(UserTestUtils.dummyUser(),r.toString(),new HashSet<Aptitude>(),new TreeSet<>());
    }

    public static Set<SProvider> dummySProviders(int size){
        Set<SProvider> sProviderList = new HashSet<>();

        for(int i=0; i < size ; i++){
            sProviderList.add(dummySProvider());
        }

        return sProviderList;
    }

    public static void assertEqualsSProviders(SProvider expected, SProvider actual){
        assertEquals(expected,actual);
        assertEquals(expected.getDescription(),actual.getDescription());
        assertEquals(expected.getAptitudes(),actual.getAptitudes());
       // assertEquals(expected.getWorkingZones(),actual.getWorkingZones());
        assertEquals(expected.getId(),actual.getId());
        UserTestUtils.assertEqualsUsers(expected.getUser(),actual.getUser());
    }
}
