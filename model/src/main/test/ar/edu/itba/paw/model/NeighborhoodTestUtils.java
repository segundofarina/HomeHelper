package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class NeighborhoodTestUtils {

    public  NeighborhoodTestUtils(){

    }

    public static Neighborhood dummyNeighborhood(){
        return new Neighborhood(new Random().toString());
    }

    public static List<Neighborhood> dummyNeighborhoods(int size) {
        List<Neighborhood> neighborhoodList = new ArrayList<Neighborhood>(size);

        for (int i = 0; i < size; i++)
            neighborhoodList.add(dummyNeighborhood());

        return neighborhoodList;
    }

    public static void assertEqualsNeighborhoods(Neighborhood expected, Neighborhood actual) {
        assertEquals(expected, actual);
        assertEquals(expected.getNgId(), actual.getNgId());
        assertEquals(expected.getNgName(), actual.getNgName());
    }
}
