package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.ServiceType;
import org.springframework.context.MessageSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ServiceTypesListDto {
    private List<ServiceTypeDto> serviceTypesList;

    public ServiceTypesListDto() {
    }

    public ServiceTypesListDto(List<ServiceType> serviceTypesList, Locale locale, MessageSource messageSource) {
        this.serviceTypesList = new LinkedList<>();
        for(ServiceType serviceType : serviceTypesList) {
            this.serviceTypesList.add(new ServiceTypeDto(serviceType,locale,messageSource));
        }
    }

    public List<ServiceTypeDto> getServiceTypesList() {
        return serviceTypesList;
    }

    public void setServiceTypesList(List<ServiceTypeDto> serviceTypesList) {
        this.serviceTypesList = serviceTypesList;
    }
}
