package testUtils;

import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.Review;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class AptitudeTestUtils {

    public AptitudeTestUtils() {

    }

    public static Aptitude dummyAptitude() {
        Random r = new Random();
        return new Aptitude(SProviderTestUtils.dummySProvider(), ServiceTypeTestUtils.dummyServiceType(), r.toString(), new HashSet<Review>());
    }

    public static Set<Aptitude> dummyAptitudes(int size) {
        Set<Aptitude> aptitudeSet = new HashSet<>();
        for (int i = 0; i < size; i++) {
            aptitudeSet.add(dummyAptitude());
        }
        return aptitudeSet;
    }

    public static void assertEqualsAptitudes(Aptitude expected, Aptitude actual) {
        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getReviews(), actual.getReviews());
        ServiceTypeTestUtils.assertEqualsServiceTypes(expected.getService(), actual.getService());
        SProviderTestUtils.assertEqualsSProviders(expected.getsProvider(), actual.getsProvider());
    }
}
