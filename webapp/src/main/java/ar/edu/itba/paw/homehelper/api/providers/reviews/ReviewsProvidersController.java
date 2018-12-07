package ar.edu.itba.paw.homehelper.api.providers.reviews;

import ar.edu.itba.paw.homehelper.api.PaginationController;
import ar.edu.itba.paw.homehelper.dto.ReviewDto;
import ar.edu.itba.paw.homehelper.dto.ReviewsListDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.utils.SizeListTuple;
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

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviews(@QueryParam("st") final Integer serviceTypeId,
                               @QueryParam("page") @DefaultValue(PaginationController.CURRENT_PAGE) final int page,
                               @QueryParam("pageSize") @DefaultValue(PaginationController.PAGE_SIZE) final int pageSize) {

        if(page < 1 || pageSize < 1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(!loggedUser.id().isPresent()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        int loggedUserId = loggedUser.id().get();

        SizeListTuple<Review> reviews;

        if(serviceTypeId == null){
            reviews = sProviderService.getReviewsOfServiceProvider(loggedUserId,-1,page,pageSize);
        }else {
            reviews = sProviderService.getReviewsOfServiceProvider(loggedUserId, serviceTypeId, page, pageSize);
        }

        final int maxPage = (int) Math.ceil((double) reviews.getSize() / pageSize);

        if(page > maxPage && maxPage != 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Link[] links = PaginationController.getPaginationLinks(uriInfo,page, maxPage);


        return Response.ok(new ReviewsListDto(reviews.getList(), page, pageSize, maxPage,uriInfo.getBaseUri().getRawPath())).links(links).build();

    }



}
