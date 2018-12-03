package ar.edu.itba.paw.homehelper.api;

import ar.edu.itba.paw.homehelper.dto.ReviewDto;
import ar.edu.itba.paw.homehelper.dto.ReviewsListDto;
import ar.edu.itba.paw.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/reviews")
@Controller
public class ReviewsController {

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviews() {
        List<Review> reviews = dummyReviews();

        return Response.ok(new ReviewsListDto(reviews)).build(); /* TODO: this should be paginated */
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReview(@PathParam("id") final int id) {
        Review review = dummyReview();
        return Response.ok(new ReviewDto(review)).build();
    }

    /* POST on / creates a new review */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReview(final ReviewDto review) {
        System.out.println(review.getComment());
        final Review newReview = dummyReview();

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newReview.getId())).build();

        return Response.created(uri).build();
    }

    /* PUT on /{id} replaces the review id */


    private List<Review> dummyReviews() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(dummyReview());
        reviews.add(dummyReview());
        reviews.add(dummyReview());
        reviews.add(dummyReview());
        reviews.add(dummyReview());
        return reviews;
    }

    private Review dummyReview() {
        return new Review(1,2,3,4,5,"Comment", new Date(), null, null);
    }
}
