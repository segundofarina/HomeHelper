package ar.edu.itba.paw.homehelper.api.providers;

import ar.edu.itba.paw.homehelper.dto.*;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.CoordenatesPoint;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
//import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("providers")
@Controller
public class ProvidersController {


    private final int loggedInUser= 40;
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

    @POST
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProvider(CreateProviderDto providerDto){
        SProvider provider = sProviderService.create(loggedInUser,providerDto.getDescription());

        providerDto.getAptitudes().stream().forEach( apt -> sProviderService.addAptitude(provider.getId(),apt.getServiceTypeId(), apt.getDescription()));

        List<CoordenateDto> workingZone = providerDto.getWorkingZone();

        sProviderService.addCoordenates(loggedInUser,
                IntStream.range(0,workingZone.size())
                        .mapToObj(i -> new CoordenatesPoint(i,workingZone.get(i).getLat(),workingZone.get(i).getLat())).collect(Collectors.toSet())
        );
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(provider.getId())).build();
        return Response.created(uri).build();

    }

//    @POST
//    @Path("/")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response test(ProviderDto providerDto) {
//        final URI uri = uriInfo.getAbsolutePathBuilder().path("/10").build();
//        return Response.created(uri).build();
//    }


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
