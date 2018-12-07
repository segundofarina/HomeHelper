package ar.edu.itba.paw.homehelper.api.providers.id.reviews;

import ar.edu.itba.paw.homehelper.api.PaginationController;
import ar.edu.itba.paw.homehelper.dto.CreateReviewDto;
import ar.edu.itba.paw.homehelper.dto.ReviewDto;
import ar.edu.itba.paw.homehelper.dto.ReviewsListDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.utils.SizeListTuple;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Optional;

@Path("/providers/{id}/reviews")
public class ReviewsIdProviderController {

    @Autowired
    LoggedUser loggedUser;

    @Autowired
    SProviderService sProviderService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    ReviewService reviewService;

    @Context
    private UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviews(@PathParam("id") final Integer id,
                               @QueryParam("st") final Integer serviceTypeId,
                               @QueryParam("page") @DefaultValue(PaginationController.CURRENT_PAGE) final int page,
                               @QueryParam("pageSize") @DefaultValue(PaginationController.PAGE_SIZE) final int pageSize) {

        if(id == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if(page < 1 || pageSize < 1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        SizeListTuple<Review> reviewsTuple;

        if(serviceTypeId == null){
            reviewsTuple = sProviderService.getReviewsOfServiceProvider(id,-1,page,pageSize);
        }else {
            reviewsTuple = sProviderService.getReviewsOfServiceProvider(id, serviceTypeId, page, pageSize);
        }

        final int maxPage = (int) Math.ceil((double) reviewsTuple.getSize() / pageSize);

        if(page > maxPage && maxPage != 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Link[] links = PaginationController.getPaginationLinks(uriInfo,page, maxPage);

        return Response.ok(new ReviewsListDto(reviewsTuple.getList(), page, pageSize, maxPage,uriInfo.getBaseUri().getRawPath())).links(links).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReview(final CreateReviewDto reviewContainer) {
        if(reviewContainer == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        int appointmentId = reviewContainer.getAppointmentId();
        ReviewDto review = reviewContainer.getReview();

        if(review == null || review.getComment() == null || review.getScores() == null || !loggedUser.id().isPresent()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        if(review.getScores().getQuality() == 0 || review.getScores().getCleanness() == 0 ||
                review.getScores().getPrice() == 0 || review.getScores().getPunctuality() == 0 || review.getScores().getTreatment() == 0 ){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        int loggedUserId = loggedUser.id().get();
        Appointment appointment = appointmentService.getAppointment(appointmentId);

        if(appointment.getClient().getId() != loggedUserId){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        final Optional<Review> newReview =  appointmentService.reviewAppointment(
                appointmentId,
                loggedUserId,
                    (int) review.getScores().getQuality(),
                    (int) review.getScores().getCleanness(),
                    (int) review.getScores().getPrice(),
                    (int) review.getScores().getPunctuality(),
                    (int) review.getScores().getTreatment(),
                    review.getComment()
                );
        if(!newReview.isPresent()){
            return Response.status(Response.Status.CONFLICT).build();
        }

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newReview.get().getId())).build();

        return Response.created(uri).build();
    }

    @GET
    @Path("/{reviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReview(@PathParam("reviewId") final Integer reviewId){


        Review review = reviewService.getReview(reviewId);

        if(review == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new ReviewDto(review)).build();
    }

}
