package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "workingzones")
public class WorkingZone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workingzones_workingId_seq")
    @SequenceGenerator(sequenceName = "workingzones_workingId_seq", name = "workingzones_workingId_seq", allocationSize = 1)
    @Column(name = "workingZoneId")
    private int workingId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ngid")
    private Neighborhood neighborhood;

    /* package*/ WorkingZone(){
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }
}