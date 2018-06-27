package testUtils;

import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.Review;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class AptitudeTestUtils {

    public AptitudeTestUtils(){

    }

    public static Aptitude dummyAptitude(){
        Random r = new Random();
        return new Aptitude(SProviderTestUtils.dummySProvider(),ServiceTypeTestUtils.dummyServiceType(),r.toString(),new HashSet<Review>());
    }

    public static List<Aptitude> dummyAptitudes(int size){
        List<Aptitude> aptitudeList = new ArrayList<>();
        for(int i = 0 ; i < size ; i ++){
            aptitudeList.add(dummyAptitude());
        }
        return aptitudeList;
    }

    public static void assertEqualsAptitudes(Aptitude expected, Aptitude actual){
        assertEquals(expected,actual);
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getDescription(),actual.getDescription());
        assertEquals(expected.getReviews(),actual.getReviews());
        ServiceTypeTestUtils.assertEqualsServiceTypes(expected.getService(),actual.getService());
        SProviderTestUtils.assertEqualsSProviders(expected.getsProvider(),actual.getsProvider());
    }
}
