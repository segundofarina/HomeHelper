package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "coordenates")
public class CoordenatesPoint implements Comparable<CoordenatesPoint> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coordenates_coordId_seq")
    @SequenceGenerator(sequenceName = "coordenates_coordId_seq", name = "coordenates_coordId_seq", allocationSize = 1)
    @Column(name = "coordId")
    private int id;

    @Column(name = "userId")
    private int userId;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lng")
    private double lng;

    @Column(name = "pos")
    private int position;

    /* package */ CoordenatesPoint() {

    }

//    public CoordenatesPoint(int position, double lat, double lng) {
//        this.lat = lat;
//        this.lng = lng;
//        this.position = position;
//    }

    public CoordenatesPoint(int userId, int position, double lat, double lng) {
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
        this.position = position;
    }

    public int getUserId() {
        return userId;
    }

    public int getId(){
        return id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getPosition() {
        return position;
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

    @Override
    public int compareTo(CoordenatesPoint o) {
        return Integer.compare(this.position, o.position);
    }

    @Override
    public String toString() {
        return "CoordenatesPoint{" +
                "id=" + id +
                ", userId=" + userId +
                ", lat=" + lat +
                ", lng=" + lng +
                ", position=" + position +
                '}';
    }
}
