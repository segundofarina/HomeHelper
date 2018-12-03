package ar.edu.itba.paw.homehelper.api.providers.reviews;

import ar.edu.itba.paw.homehelper.dto.ReviewDto;
import ar.edu.itba.paw.homehelper.dto.ReviewsListDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.SProviderService;
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
import java.util.stream.Collectors;

@Path("/reviews")
@Controller
public class ReviewsProvidersController {

    @Autowired
    LoggedUser loggedUser;

    @Context
    private UriInfo uriInfo;



    @Autowired
    private SProviderService sProviderService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviews() {
        List<Review> reviews = new ArrayList<>(sProviderService.getReviewsOfServiceProvider(loggedUser.id()));

        return Response.ok(new ReviewsListDto(reviews)).build(); /* TODO: this should be paginated */
    }



//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getReview(@PathParam("id") final int id) {
//        Review review = dummyReview();
//        return Response.ok(new ReviewDto(review)).build();
//    }




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
