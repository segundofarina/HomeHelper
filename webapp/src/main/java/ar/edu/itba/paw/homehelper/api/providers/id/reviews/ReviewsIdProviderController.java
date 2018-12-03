package ar.edu.itba.paw.homehelper.api.providers.id.reviews;


import ar.edu.itba.paw.homehelper.dto.ReviewDto;
import ar.edu.itba.paw.homehelper.dto.ReviewsListDto;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.Review;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/providers/{id}/reviews")
public class ReviewsIdProviderController {


    private final int loggedinUser= 1;

    @Autowired
    SProviderService sProviderService;

    @Autowired
    AppointmentService appointmentService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviews(@PathParam("id") final int id) {
        List<Review> reviews = new ArrayList<>(sProviderService.getReviewsOfServiceProvider(id));

        return Response.ok(new ReviewsListDto(reviews)).build(); /* TODO: this should be paginated */
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReview(@QueryParam("appId") final Integer appointmentId,final ReviewDto review) {
        /* TODO check if user is allowed to make the review" */

        appointmentService.reviewAppointment(
                appointmentId,
                loggedinUser,
                1,
                (int) review.getScores().getQuality(),
                (int) review.getScores().getCleanness(),
                (int) review.getScores().getPrice(),
                (int) review.getScores().getPunctuality(),
                (int) review.getScores().getTreatment(),
                review.getComment()
                );
        final Review newReview = null;
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(/*newReview.getId()*/"1")).build();

        return Response.created(uri).build();
    }

    @GET
    @Path("/{reviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReview(@PathParam("reviewId") final Integer reviewId){

        /*TODO get review by id*/
        return Response.ok(new ReviewDto()).build();
    }
}
