package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.SProvider;
import org.springframework.context.MessageSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ProvidersListDto {
    private List<ProviderDto> providers;
    private int page;
    private int pageSize;
    private int maxPage;

    public ProvidersListDto() {
    }

    public ProvidersListDto(List<SProvider> providers, int page, int pageSize, int maxPage, Locale locale, MessageSource messageSource) { // TODO: providers should be sorted as list
        this.page = page;
        this.pageSize = pageSize;
        this.maxPage = maxPage;

        this.providers = new LinkedList<>();
        for(SProvider p : providers) {
            this.providers.add(new ProviderDto(p,locale,messageSource));
        }
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
