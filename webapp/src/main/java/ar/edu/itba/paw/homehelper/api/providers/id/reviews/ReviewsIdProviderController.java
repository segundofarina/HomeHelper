package ar.edu.itba.paw.homehelper.api.providers.id.reviews;


import ar.edu.itba.paw.homehelper.dto.ReviewDto;
import ar.edu.itba.paw.homehelper.dto.ReviewsListDto;
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
import java.util.Map;

@Path("/providers/{id}/reviews")
public class ReviewsIdProviderController {


    private final int loggedinUser= 1;

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

        double [] score = new double [review.getScores().getCalifications().size()];

        int i = 0;

        for(Map.Entry<String,Double> calification : review.getScores().getCalifications().entrySet()){
            score[i++] = (double) calification.getValue();
        }

        appointmentService.reviewAppointment(
                appointmentId,
                loggedinUser,
                1,
                (int)score[1],
                (int)score[2],
                (int) score[3],
                (int)score[4],
                (int)score[5],
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
