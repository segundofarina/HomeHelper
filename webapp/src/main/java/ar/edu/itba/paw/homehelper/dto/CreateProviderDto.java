package ar.edu.itba.paw.homehelper.dto;

import java.util.List;

public class CreateProviderDto {
    private String description;
    private List<BasicAptitudeDto> aptitudes;
    private List<CoordenateDto> workingZone;

    public CreateProviderDto(String description, List<BasicAptitudeDto> aptitudes, List<CoordenateDto> workingZone) {
        this.description = description;
        this.aptitudes = aptitudes;
        this.workingZone = workingZone;
    }

    public CreateProviderDto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BasicAptitudeDto> getAptitudes() {
        return aptitudes;
    }

    public void setAptitudes(List<BasicAptitudeDto> aptitudes) {
        this.aptitudes = aptitudes;
    }

    public List<CoordenateDto> getWorkingZone() {
        return workingZone;
    }

    public void setWorkingZone(List<CoordenateDto> workingZone) {
        this.workingZone = workingZone;
    }

}
