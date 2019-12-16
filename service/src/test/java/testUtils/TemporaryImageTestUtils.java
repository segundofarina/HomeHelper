package testUtils;

import ar.edu.itba.paw.model.TemporaryImage;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TemporaryImageTestUtils {

    public TemporaryImageTestUtils() {

    }

    public static TemporaryImage dummyTemporaryImage() {
        return new TemporaryImage(new Random().toString().getBytes());
    }

    public static Set<TemporaryImage> dummyTemporaryImages(int size) {
        Set<TemporaryImage> temporaryImages = new HashSet<>();

        for (int i = 0; i < size; i++) {
            temporaryImages.add(dummyTemporaryImage());
        }

        return temporaryImages;
    }

    public static void assertEqualsTemporaryImages(TemporaryImage expected, TemporaryImage actual) {
        assertEquals(expected, actual);
        assertEquals(expected.getImageId(), actual.getImageId());
        assertEquals(expected.getImage(), actual.getImage());
    }
}
