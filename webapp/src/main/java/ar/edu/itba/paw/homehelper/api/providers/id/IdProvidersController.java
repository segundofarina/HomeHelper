package ar.edu.itba.paw.homehelper.api.providers.id;


import ar.edu.itba.paw.homehelper.dto.AptitudeDto;
import ar.edu.itba.paw.homehelper.dto.CoordenateDto;
import ar.edu.itba.paw.homehelper.dto.ProviderDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.CoordenatesPoint;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Path("/providers/{id}")
@Controller
public class IdProvidersController {

    @Autowired
    SProviderService sProviderService;

    @Context
    HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    LoggedUser loggedUser;

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getProviderById(@PathParam("id") final int id) {
        Locale locale = request.getLocale();

        final SProvider provider = sProviderService.getServiceProviderWithUserId(id);

        if(provider == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new ProviderDto(provider,locale,messageSource)).build();
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProvider(@PathParam("id") final Integer id, ProviderDto providerDto){

        if(!loggedUser.id().isPresent() || loggedUser.id().get() != id || !loggedUser.isProvider().isPresent() || !loggedUser.isProvider().get()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(providerDto.getAptitudes() != null){
            for(AptitudeDto aptitude : providerDto.getAptitudes()) {
                if(aptitude.getDescription() != null){
                    sProviderService.updateDescriptionOfAptitude(aptitude.getId(),aptitude.getDescription());
                }
                if(aptitude.getServiceType() != null) {
                    if (!sProviderService.updateServiceTypeOfAptitude(aptitude.getId(), aptitude.getServiceType().getId())) {
                        sProviderService.addAptitude(loggedUser.id().get(), aptitude.getServiceType().getId(), aptitude.getDescription());
                    }
                }
            }
        }
        if(providerDto.getCoordenates() != null) {
            int i = 0;
            List<CoordenatesPoint> coordenateList = new ArrayList<>();
            for (CoordenateDto coordenateDto : providerDto.getCoordenates()) {
                coordenateList.add(new CoordenatesPoint(loggedUser.id().get(), i++, coordenateDto.getLat(), coordenateDto.getLng()));
            }
            sProviderService.addCoordenates(loggedUser.id().get(), coordenateList);
        }
        if(providerDto.getDescription() != null){
            sProviderService.updateDescriptionOfServiceProvider(id,providerDto.getDescription());
        }

        return Response.ok().build();
    }

    @DELETE
    @Path("/aptitudes/{stId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAptitude(@PathParam("id") final Integer id, @PathParam("stId") final Integer serviceTypeId){

        if(!loggedUser.id().isPresent() || loggedUser.id().get() != id || !loggedUser.isProvider().isPresent() || !loggedUser.isProvider().get()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        sProviderService.removeAptitude(id,serviceTypeId);
        return Response.ok().build();
    }
}
