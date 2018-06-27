package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "neighborhoods")
public class Neighborhood {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "neighborhoods_ngid_seq")
    @SequenceGenerator(sequenceName = "neighborhoods_ngid_seq", name = "neighborhoods_ngid_seq", allocationSize = 1)
    @Column(name = "ngid")
    private int ngId;

    @Column(name ="ngname", length = 100)
    private String ngname;

    /* package */ Neighborhood(){
        // For Hibernate
    }


    public Neighborhood(int id){

    }

    public Neighborhood(String ngname){
        this.ngname = ngname;
    }

    public int getNgId() {
        return ngId;
    }

    public void setNgId(int ngId) {
        this.ngId = ngId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neighborhood)) return false;

        Neighborhood that = (Neighborhood) o;

        if (getNgId() != that.getNgId()) return false;
        return ngname != null ? ngname.equals(that.ngname) : that.ngname == null;
    }

    @Override
    public int hashCode() {
        int result = getNgId();
        result = 31 * result + (ngname != null ? ngname.hashCode() : 0);
        return result;
    }
}
