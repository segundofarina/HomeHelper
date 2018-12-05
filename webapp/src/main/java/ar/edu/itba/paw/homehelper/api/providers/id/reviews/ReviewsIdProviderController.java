package ar.edu.itba.paw.homehelper.api.providers.id.reviews;


import ar.edu.itba.paw.homehelper.dto.CalificationDto;
import ar.edu.itba.paw.homehelper.dto.ReviewDto;
import ar.edu.itba.paw.homehelper.dto.ReviewsListDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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

    private final static String CURRENT_PAGE = "1";
    private final static String PAGE_SIZE = "100";

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviews(@QueryParam("st") final Integer serviceTypeId,
                               @QueryParam("page") @DefaultValue(CURRENT_PAGE) final int page,
                               @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) final int pageSize) {

        if(page < 1 || pageSize < 1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(serviceTypeId == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Locale locale = request.getLocale();

        List<Review> reviews = sProviderService.getReviewsOfServiceProvider(loggedUser.id(),serviceTypeId,page,pageSize);

        final int maxPage = (int) Math.ceil((double) reviews.size() / pageSize); // TODO: get max page from sProviderService

        if(page > maxPage && maxPage != 0) { // TODO: this should be before searching for providers

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Link[] links = getPaginationLinks(page, maxPage);

        return Response.ok(new ReviewsListDto(reviews, page, pageSize, maxPage,locale,messageSource)).links(links).build(); /* TODO: this should be paginated */
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

        Appointment appointment = appointmentService.getAppointment(appointmentId);

        if(appointment.getClient().getId() != loggedUser.id()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        final Review newReview =  appointmentService.reviewAppointment(
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
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newReview.getId())).build();

        return Response.created(uri).build();
    }

    @GET
    @Path("/{reviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReview(@PathParam("reviewId") final Integer reviewId){

        return Response.ok(new ReviewDto()).build();
    }

    private Link[] getPaginationLinks(final int page, final int maxPage) {
        List<Link> links = new ArrayList<>();

        if(page > 1) {
            links.add(getLink("prev", page - 1));
        }

        if(page < maxPage) {
            links.add(getLink("next", page + 1));
        }

        links.add(getLink("first", 1));
        links.add(getLink("last", maxPage));

        return links.toArray(new Link[0]);
    }

    private Link getLink(final String rel, final int idx) {
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        uriBuilder.replaceQueryParam("page", idx);
        return Link.fromUriBuilder(uriBuilder).rel(rel).build();
    }
}
