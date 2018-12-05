package ar.edu.itba.paw.homehelper.api.providers.id.reviews;


import ar.edu.itba.paw.homehelper.dto.CalificationDto;
import ar.edu.itba.paw.homehelper.dto.ReviewDto;
import ar.edu.itba.paw.homehelper.dto.ReviewsListDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Path("/providers/{id}/reviews")
public class ReviewsIdProviderController {


    @Autowired
    LoggedUser loggedUser;

    @Autowired
    SProviderService sProviderService;

    @Autowired
    AppointmentService appointmentService;

    @Context
    private UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    //TODO add st parameter
    public Response getReviews(@PathParam("id") final int id) {

        Locale locale = request.getLocale();

        List<Review> reviews = new ArrayList<>(sProviderService.getReviewsOfServiceProvider(id));

        return Response.ok(new ReviewsListDto(reviews,locale,messageSource)).build(); /* TODO: this should be paginated */
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReview(@QueryParam("appId") final Integer appointmentId,final ReviewDto review) {
        /* TODO check if user is allowed to make the review" */

        if(appointmentId == null || review == null || review.getComment() == null || review.getScores() ==null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if(review.getScores().getQuality() == 0 || review.getScores().getCleanness()== 0 ||
                review.getScores().getPrice()== 0 || review.getScores().getPunctuality()== 0 || review.getScores().getTreatment() == 0 ){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

            appointmentService.reviewAppointment(
                appointmentId,
                loggedUser.id(),
                -1,//TODO review only by appointment id
                    (int) review.getScores().getQuality(),
                    (int) review.getScores().getCleanness(),
                    (int) review.getScores().getPrice(),
                    (int) review.getScores().getPunctuality(),
                    (int) review.getScores().getTreatment(),
                review.getComment()
                );
        final Review newReview = null;//TODO esto
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
