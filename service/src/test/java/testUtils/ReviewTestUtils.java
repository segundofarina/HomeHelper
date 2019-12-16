package testUtils;

import ar.edu.itba.paw.model.Review;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class ReviewTestUtils {

    public ReviewTestUtils() {

    }

    public static Review dummyReview() {
        Random r = new Random();
        return new Review(r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt(), r.toString(), new Date(), UserTestUtils.dummyUser(), AptitudeTestUtils.dummyAptitude(),1);
    }

    public static Set<Review> dummyReviews(int size) {
        Set<Review> reviewsSet = new HashSet<>(size);

        for (int i = 0; i < size; i++)
            reviewsSet.add(dummyReview());

        return reviewsSet;
    }

    public static void AssertEquals(Review expected, Review actual) {
        assertEquals(expected, actual);
        assertEquals(expected.getGeneralCalification(), actual.getGeneralCalification());
        assertEquals(expected.getComment(), actual.getComment());
        UserTestUtils.assertEqualsUsers(expected.getUser(), actual.getUser());
        AptitudeTestUtils.assertEqualsAptitudes(expected.getAptitude(), actual.getAptitude());
    }
}
