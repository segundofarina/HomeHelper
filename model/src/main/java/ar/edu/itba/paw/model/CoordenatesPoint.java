package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "coordenates")
public class CoordenatesPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coordenates_coordId_seq")
    @SequenceGenerator(sequenceName = "coordenates_coordId_seq", name = "coordenates_coordId_seq", allocationSize = 1)
    @Column(name = "coordId")
    private int id;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lng")
    private double lng;

    /* package */ CoordenatesPoint(){

    }

    public CoordenatesPoint(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoordenatesPoint)) return false;

        CoordenatesPoint that = (CoordenatesPoint) o;

        if (id != that.id) return false;
        if (Double.compare(that.getLat(), getLat()) != 0) return false;
        return Double.compare(that.getLng(), getLng()) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(getLat());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLng());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
