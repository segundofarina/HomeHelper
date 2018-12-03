package ar.edu.itba.paw.homehelper.api;

import ar.edu.itba.paw.homehelper.dto.ProviderDto;
import ar.edu.itba.paw.homehelper.dto.ProvidersListDto;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("/providers")
@Controller
public class ProvidersController {

    @Autowired
    SProviderService sProviderService;

    @Context
    private UriInfo uriInfo;

    private final static String CURRENT_PAGE = "1";
    private final static String PAGE_SIZE = "100";

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getProviders(
            @QueryParam("st") final Integer serviceTypeId,
            @QueryParam("lat") final Double latitude,
            @QueryParam("lng") final Double longitude,
            @QueryParam("page") @DefaultValue(CURRENT_PAGE) final int page,
            @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) final int pageSize) {

        if(latitude == null && longitude != null || latitude != null && longitude == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(page < 1 || pageSize < 1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Set<SProvider> providers = sProviderService.getServiceProviders(); // TODO: get sorted and paginated providers, procesing all params (st, lat, lng, page, pageSize)

        final int maxPage = (int) Math.ceil((double) providers.size() / pageSize); // TODO: get max page from sProviderService

        if(page > maxPage) { // TODO: this should be before searching for providers
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Link[] links = getPaginationLinks(page, maxPage);

        return Response.ok(new ProvidersListDto(providers, page, pageSize, maxPage)).links(links).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getProviderById(@PathParam("id") final int id) {
        final SProvider provider = sProviderService.getServiceProviderWithUserId(id);

        if(provider == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new ProviderDto(provider)).build();
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
