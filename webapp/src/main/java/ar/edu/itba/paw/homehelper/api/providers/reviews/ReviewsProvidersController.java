package ar.edu.itba.paw.homehelper.api.providers.reviews;

import ar.edu.itba.paw.homehelper.api.PaginationController;
import ar.edu.itba.paw.homehelper.dto.ReviewDto;
import ar.edu.itba.paw.homehelper.dto.ReviewsListDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Path("/providers/reviews")
@Controller
public class ReviewsProvidersController {

    @Autowired
    LoggedUser loggedUser;

    @Context
    private UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SProviderService sProviderService;

    private final static String CURRENT_PAGE = "1";
    private final static String PAGE_SIZE = "100";

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviews(@QueryParam("page") @DefaultValue(CURRENT_PAGE) final int page,
                               @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) final int pageSize) {

        Locale locale = request.getLocale();

        List<Review> reviews = sProviderService.getReviewsOfServiceProvider(loggedUser.id(),page,pageSize);

        final int maxPage = (int) Math.ceil((double) reviews.size() / pageSize); // TODO: get max page from sProviderService

        if(page > maxPage && maxPage != 0) { // TODO: this should be before searching for providers

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Link[] links = PaginationController.getPaginationLinks(uriInfo,page, maxPage);

        return Response.ok(new ReviewsListDto(reviews,page,pageSize,maxPage,locale,messageSource)).links(links).build(); /* TODO: this should be paginated */
    }

//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getReview(@PathParam("id") final int id) {
//        Review review = dummyReview();
//        return Response.ok(new ReviewDto(review)).build();
//    }


}
