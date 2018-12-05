package ar.edu.itba.paw.homehelper.api;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class PaginationController {

    public static Link[] getPaginationLinks(UriInfo uriInfo,final int page, final int maxPage) {
        List<Link> links = new ArrayList<>();

        if(page > 1) {
            links.add(getLink(uriInfo,"prev", page - 1));
        }

        if(page < maxPage) {
            links.add(getLink(uriInfo,"next", page + 1));
        }

        links.add(getLink(uriInfo,"first", 1));
        links.add(getLink(uriInfo,"last", maxPage));

        return links.toArray(new Link[0]);
    }

    private static Link getLink(UriInfo uriInfo, final String rel, final int idx) {
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        uriBuilder.replaceQueryParam("page", idx);
        return Link.fromUriBuilder(uriBuilder).rel(rel).build();
    }
}
