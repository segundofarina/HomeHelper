package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Aptitude;

public class AptitudeDto {
     private int id;
        private ServiceTypeDto serviceType;
        private String description;
        private CalificationDto calification;

        public AptitudeDto() {
        }

        public AptitudeDto(Aptitude aptitude) {
            this.id = aptitude.getId();
            this.serviceType = new ServiceTypeDto(aptitude.getService());
            this.description = aptitude.getDescription();
            this.calification = new CalificationDto(aptitude);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ServiceTypeDto getServiceType() {
            return serviceType;
        }

        public void setServiceType(ServiceTypeDto serviceType) {
            this.serviceType = serviceType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public CalificationDto getCalification() {
            return calification;
        }

        public void setCalification(CalificationDto calification) {
            this.calification = calification;
        }

}
