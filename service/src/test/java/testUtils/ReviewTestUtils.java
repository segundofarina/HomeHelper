package testUtils;

import ar.edu.itba.paw.model.Review;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ReviewTestUtils {

    public ReviewTestUtils(){

    }

    public static Review dummyReview(){
        Random r = new Random();
        return new Review(r.nextInt(),r.nextInt(),r.nextInt(),r.nextInt(),r.nextInt(),r.toString(),new Date(),UserTestUtils.dummyUser(),AptitudeTestUtils.dummyAptitude());
    }

    public static List<Review> dummyReviews(int size) {
        List<Review> reviewsList = new ArrayList<Review>(size);

        for (int i = 0; i < size; i++)
            reviewsList.add(dummyReview());

        return reviewsList;
    }

    public static void AssertEquals(Review expected, Review actual){
        assertEquals(expected,actual);
        assertEquals(expected.getGeneralCalification(),actual.getGeneralCalification());
        assertEquals(expected.getComment(),actual.getComment());
        UserTestUtils.assertEqualsUsers(expected.getUser(),actual.getUser());
        AptitudeTestUtils.assertEqualsAptitudes(expected.getAptitude(),actual.getAptitude());
    }
}
