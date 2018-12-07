package ar.edu.itba.paw.homehelper.api.providers;
import ar.edu.itba.paw.homehelper.api.PaginationController;
import ar.edu.itba.paw.homehelper.dto.*;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.CoordenatesPoint;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.utils.SizeListTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.*;

@Path("/providers")
@Controller
public class ProvidersController {


    @Autowired
    LoggedUser loggedUser;

    @Autowired
    SProviderService sProviderService;

    @Context
    private UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    private final Logger LOGGER = LoggerFactory.getLogger(ProvidersController.class);

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getProviders(
            @QueryParam("st") final Integer serviceTypeId,
            @QueryParam("lat") final Double latitude,
            @QueryParam("lng") final Double longitude,
            @QueryParam("page") @DefaultValue(PaginationController.CURRENT_PAGE) final int page,
            @QueryParam("pageSize") @DefaultValue(PaginationController.PAGE_SIZE) final int pageSize) {

        if(page < 1 || pageSize < 1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

         // TODO: get sorted and paginated providers, procesing all params (st, lat, lng, page, pageSize)

        Locale locale = request.getLocale();


        SizeListTuple<SProvider> providers;

        int loggedUserId = loggedUser.id().orElse(-1);

        if(latitude == null && longitude == null && serviceTypeId!= null){
            providers = sProviderService.getServiceProvidersByServiceType(serviceTypeId,loggedUserId,page,pageSize);
        } else if(latitude != null && longitude != null && serviceTypeId == null){
            providers = sProviderService.getServiceProvidersByNeighborhood(latitude,longitude,loggedUserId,page,pageSize);
        } else if(latitude !=null && longitude != null && serviceTypeId != null){
            providers = sProviderService.getServiceProvidersByNeighborhoodAndServiceType(latitude,longitude,serviceTypeId,loggedUserId,page,pageSize);
        }else if(latitude == null && longitude == null && serviceTypeId == null) {
            providers = sProviderService.getServiceProviders(loggedUserId,page,pageSize);
        }else{
                return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final int maxPage = (int) Math.ceil((double) providers.getSize() / pageSize);

        if(page > maxPage && maxPage != 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOGGER.debug("Genero los links");

        LOGGER.debug("Return");

        final Link[] links = PaginationController.getPaginationLinks(uriInfo,page, maxPage);

        return Response.ok(new ProvidersListDto(new ArrayList<>(providers.getList()), page, pageSize, maxPage,locale,messageSource)).links(links).build();

    }

    @POST
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProvider(CreateProviderDto providerDto){

        if(loggedUser.isProvider().orElse(true) || !loggedUser.id().isPresent() ){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(providerDto == null || providerDto.getWorkingZone()== null || providerDto.getAptitudes() == null || providerDto.getDescription() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Map<Integer,String> aptitudes = new HashMap<>();

        for(BasicAptitudeDto aptitudeDto: providerDto.getAptitudes()){
            aptitudes.put(aptitudeDto.getServiceTypeId(),aptitudeDto.getDescription());
        }

        final int loggedUserId = loggedUser.id().get();

        List<CoordenatesPoint> coordenates = new ArrayList<>();

        int i = 0;

        for(CoordenateDto coordenateDto: providerDto.getWorkingZone()){
            coordenates.add(new CoordenatesPoint(loggedUserId,i++,coordenateDto.getLat(),coordenateDto.getLng()));
        }

        Optional<SProvider> provider = sProviderService.create(loggedUserId,providerDto.getDescription(),aptitudes,coordenates);

        if(!provider.isPresent()){ return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); }

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(provider.get().getId())).build();

        return Response.created(uri).build();

    }

}
