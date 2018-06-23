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

    /* package */ Neighborhood(){ }

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
        if (o == null || getClass() != o.getClass()) return false;
        Neighborhood that = (Neighborhood) o;
        return ngId == that.ngId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(ngId);
    }

    public String getNgName() {
        return this.ngname;
    }
}
