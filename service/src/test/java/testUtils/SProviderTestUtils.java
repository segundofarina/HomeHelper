package testUtils;

import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.CoordenatesPoint;
import ar.edu.itba.paw.model.SProvider;

import java.util.*;
import static testUtils.UserTestUtils.*;

import static org.junit.Assert.assertEquals;

public class SProviderTestUtils {

    public SProviderTestUtils() {

    }

    public static SProvider dummySProvider() {
        Random r = new Random();
        return new SProvider(dummyUser(), r.toString(), new HashSet<Aptitude>(), new TreeSet<>(dummyCoordenates()));
    }

    public static List<SProvider> dummySProviders(int size) {
        List<SProvider> sProviderList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            sProviderList.add(dummySProvider());
        }

        return sProviderList;
    }


    public static List<CoordenatesPoint> dummyCoordenates(){
        List<CoordenatesPoint> list = new ArrayList<>();
        //list.add(new CoordenatesPoint(1,0,-34.557176,-58.430436));
        //list.add(new CoordenatesPoint(1,1,-34.575376,-58.403839));
        //list.add(new CoordenatesPoint(1,2,-34.588696,-58.431428));

        list.add(new CoordenatesPoint(1,0,-34.53512,-58.46305));
        list.add(new CoordenatesPoint(1,1,-34.58442,-58.38118));
        list.add(new CoordenatesPoint(1,2,-34.62515,-58.37993));
        list.add(new CoordenatesPoint(1,3,-34.63073,-58.43643));
        list.add(new CoordenatesPoint(1,4,-34.64877,-58.46587));
        list.add(new CoordenatesPoint(1,5,-34.63407,-58.53004));
        list.add(new CoordenatesPoint(1,6,-34.61556,-58.53166));
        list.add(new CoordenatesPoint(1,7,-34.56878,-58.50925));
        return list;
    }
    public static void assertEqualsSProviders(SProvider expected, SProvider actual) {
        assertEquals(expected, actual);
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getAptitudes(), actual.getAptitudes());
        assertEquals(expected.getId(), actual.getId());
        UserTestUtils.assertEqualsUsers(expected.getUser(), actual.getUser());
    }
}
