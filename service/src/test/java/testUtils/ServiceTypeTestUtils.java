package testUtils;

import ar.edu.itba.paw.model.ServiceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ServiceTypeTestUtils {

    public ServiceTypeTestUtils() {

    }

    public static ServiceType dummyServiceType() {
        Random r = new Random();
        return new ServiceType(r.toString());
    }

    public static List<ServiceType> dummyServiceTypes(int size) {
        List<ServiceType> serviceTypeList = new ArrayList<ServiceType>(size);

        for (int i = 0; i < size; i++)
            serviceTypeList.add(dummyServiceType());

        return serviceTypeList;
    }

    public static void assertEqualsServiceTypes(ServiceType expected, ServiceType actual) {
        if (expected == null && actual == null) {
            return;
        }
        assertEquals(expected, actual);
        assertEquals(expected.getName(), expected.getName());
        assertEquals(expected.getId(), expected.getId());
    }

}
