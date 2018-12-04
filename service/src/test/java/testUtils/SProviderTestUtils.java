package testUtils;

import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.SProvider;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class SProviderTestUtils {

    public SProviderTestUtils() {

    }

    public static SProvider dummySProvider() {
        Random r = new Random();
        return new SProvider(UserTestUtils.dummyUser(), r.toString(), new HashSet<Aptitude>(), new TreeSet<>());
    }

    public static List<SProvider> dummySProviders(int size) {
        List<SProvider> sProviderList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            sProviderList.add(dummySProvider());
        }

        return sProviderList;
    }

    public static void assertEqualsSProviders(SProvider expected, SProvider actual) {
        assertEquals(expected, actual);
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getAptitudes(), actual.getAptitudes());
        assertEquals(expected.getId(), actual.getId());
        UserTestUtils.assertEqualsUsers(expected.getUser(), actual.getUser());
    }
}
