package ar.edu.itba.paw.homehelper.dto;

public class CoordenateDto {
    private Double lat;
    private Double lng;

    public CoordenateDto(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public CoordenateDto() {

    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
