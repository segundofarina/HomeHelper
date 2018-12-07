package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.SProvider;
import org.springframework.context.MessageSource;

import java.util.*;

public class ProvidersListDto {
    private List<ProviderDto> providers;
    private int page;
    private int pageSize;
    private int maxPage;

    public ProvidersListDto() {
    }

    public ProvidersListDto(List<SProvider> providers, int page, int pageSize, int maxPage, Locale locale, MessageSource messageSource) {
        this.page = page;
        this.pageSize = pageSize;
        this.maxPage = maxPage;

        this.providers = new ArrayList<>();
        for(SProvider p : providers) {
            this.providers.add(new ProviderDto(p,locale,messageSource));
        }

        providers.sort(new Comparator<SProvider>() {
            @Override
            public int compare(SProvider o1, SProvider o2) {
                return (int) (o2.getGeneralCalification()-o1.getGeneralCalification());
            }
        });
    }

    public List<ProviderDto> getProviders() {
        return providers;
    }

    public void setProviders(List<ProviderDto> providers) {
        this.providers = providers;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }
}
