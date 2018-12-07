package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.context.MessageSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProviderDto {
    private int id;
    private String firstName;
    private String lastName;
    private String description;
    private double generalCalification;
    private String pictureUrl;
    private List<AptitudeDto> aptitudes;
    private List<CoordenateDto> coordenates;

    public ProviderDto() {
    }

    public ProviderDto(SProvider provider, Locale locale, MessageSource messageSource) {
        this.id = provider.getId();
        this.description = provider.getDescription();
        this.generalCalification = provider.getGeneralCalification();

        User user = provider.getUser();

        this.firstName = user.getFirstname();
        this.lastName = user.getLastname();

        this.aptitudes = new LinkedList<>();
        for(Aptitude aptitude : provider.getAptitudes()) {
            this.aptitudes.add(new AptitudeDto(aptitude,locale,messageSource));
        }

        this.coordenates = provider.getCoordenates().stream().map(cor -> new CoordenateDto(cor.getLat(), cor.getLng())).collect(Collectors.toList());
        this.pictureUrl = "http://pawserver.it.itba.edu.ar/paw-2018a-4/api/"+"users/"+this.id+"/image";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getGeneralCalification() {
        return generalCalification;
    }

    public void setGeneralCalification(double generalCalification) {
        this.generalCalification = generalCalification;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<AptitudeDto> getAptitudes() {
        return aptitudes;
    }

    public void setAptitudes(List<AptitudeDto> aptitudes) {
        this.aptitudes = aptitudes;
    }

    public List<CoordenateDto> getCoordenates() {
        return coordenates;
    }

    public void setCoordenates(List<CoordenateDto> coordenates) {
        this.coordenates = coordenates;
    }
}
