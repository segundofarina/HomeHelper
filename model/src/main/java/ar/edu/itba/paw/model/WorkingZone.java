package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "workingzones")
public class WorkingZone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workingzones_workingZoneId_seq")
    @SequenceGenerator(sequenceName = "workingzones_workingZoneId_seq", name = "workingzones_workingZoneId_seq", allocationSize = 1)
    @Column(name = "workingZoneId")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ngid")
    private Neighborhood neighborhood;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private SProvider sProvider;

    /* package*/ WorkingZone(){
    }

    public WorkingZone(SProvider user, Neighborhood neighborhood){
        this.sProvider = user;
        this.neighborhood = neighborhood;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public SProvider getUser() {
        return sProvider;
    }

    public void setUser(SProvider user) {
        this.sProvider = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkingZone)) return false;

        WorkingZone that = (WorkingZone) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (getNeighborhood() != null ? getNeighborhood().hashCode() : 0);
        result = 31 * result + (sProvider != null ? sProvider.hashCode() : 0);
        return result;
    }
}