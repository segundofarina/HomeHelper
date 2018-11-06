package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.ServiceType;

import java.util.LinkedList;
import java.util.List;

public class ServiceTypesListDTO {
    private List<ServiceTypeDTO> serviceTypesList;

    public ServiceTypesListDTO() {
    }

    public ServiceTypesListDTO(List<ServiceType> serviceTypesList) {
        this.serviceTypesList = new LinkedList<>();
        for(ServiceType serviceType : serviceTypesList) {
            this.serviceTypesList.add(new ServiceTypeDTO(serviceType));
        }
    }

    public List<ServiceTypeDTO> getServiceTypesList() {
        return serviceTypesList;
    }

    public void setServiceTypesList(List<ServiceTypeDTO> serviceTypesList) {
        this.serviceTypesList = serviceTypesList;
    }
}
