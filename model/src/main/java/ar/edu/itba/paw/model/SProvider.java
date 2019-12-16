package ar.edu.itba.paw.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Entity
@PrimaryKeyJoinColumn(name = "userid")
@Table(name = "serviceProviders")
public class SProvider {

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Id
    @Column(name = "userid")
    private int id;

    @OneToOne
    @JoinColumn(name = "userid")
    @Fetch(FetchMode.JOIN)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private Set<Aptitude> aptitudes;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    @OrderBy("position ASC")
    private SortedSet<CoordenatesPoint> coordenates;

    /*package*/ SProvider() {

    }

    public SProvider(User user, String description, Set<Aptitude> aptitudes, SortedSet<CoordenatesPoint> coordenates) {
        this.id = user.getId();
        this.user = user;
        this.description = description;
        this.aptitudes = aptitudes;
        this.coordenates = coordenates;
    }

    public String getDescription() {
        return description;
    }

    public Set<Aptitude> getAptitudes() {
        return aptitudes;
    }

    public User getUser() {
        return user;
    }

    public double getQualityCalification() {

        if (aptitudes.size() == 0) {
            return 0;
        }

        double quality = 0;

        int i = 0;

        for (Aptitude aptitude : aptitudes) {
            if(aptitude.getQualityCalification() != 0) {
                quality += aptitude.getQualityCalification();
                i++;
            }
        }
        if(i == 0){
            return 0;
        }
        return Math.floor((quality / i) * 100) / 100;
    }

    public double getCleannessCalification() {

        if (aptitudes.size() == 0) {
            return 0;
        }

        double cleanness = 0;

        int i = 0;

        for (Aptitude aptitude : aptitudes) {
            if(aptitude.getCleannessCalification() != 0) {
                cleanness += aptitude.getCleannessCalification();
                i++;
            }
        }
        if(i == 0){
            return 0;
        }
        return Math.floor((cleanness / i) * 100) / 100;
    }

    public double getPriceCalification() {

        if (aptitudes.size() == 0) {
            return 0;
        }

        double price = 0;

        int i = 0;

        for (Aptitude aptitude : aptitudes) {
            if(aptitude.getPriceCalification() != 0) {
                price += aptitude.getPriceCalification();
                i++;
            }
        }
        if(i == 0){
            return 0;
        }
        return Math.floor((price / i) * 100) / 100;
    }

    public double getPunctualityCalification() {

        if (aptitudes.size() == 0) {
            return 0;
        }

        double punctuality = 0;

        int i = 0;

        for (Aptitude aptitude : aptitudes) {
            if(aptitude.getPunctualityCalification() != 0) {
                punctuality += aptitude.getPunctualityCalification();
                i++;
            }
        }
        if(i == 0){
            return 0;
        }
        return Math.floor((punctuality / i) * 100) / 100;
    }

    public double getTreatmentCalification() {

        if (aptitudes.size() == 0) {
            return 0;
        }

        double treatment = 0;

        int i = 0;

        for (Aptitude aptitude : aptitudes) {
            if(aptitude.getTreatmentCalification() != 0) {
                treatment += aptitude.getTreatmentCalification();
                i++;
            }
        }

        if(i == 0){
            return 0;
        }
        return Math.floor((treatment / i) * 100) / 100;
    }

    public double getGeneralCalification() {

        if (aptitudes.size() == 0) {
            return 0;
        }

        double general = 0;

        int i = 0;

        for (Aptitude aptitude : aptitudes) {
            if(aptitude.getGeneralCalification() != 0) {
                general += aptitude.getGeneralCalification();
                i++;
            }
        }

        if(i == 0){
            return 0;
        }
        return Math.floor((general / i) * 100) / 100;
    }

    public boolean hasReviews() {

        for (Aptitude aptitude : aptitudes) {
            if (aptitude.hasReviews()) {
                return true;
            }
        }
        return false;
    }

    public Set<CoordenatesPoint> getCoordenates() {
        return coordenates;
    }

    public void setCoordenates(SortedSet<CoordenatesPoint> coordenates) {
        this.coordenates = coordenates;
    }

    public int getId() {
        return id;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setAptitudes(Set<Aptitude> aptitudes) {
        this.aptitudes = aptitudes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SProvider)) return false;

        SProvider sProvider = (SProvider) o;

        if (getId() != sProvider.getId()) return false;
        return getDescription() != null ? getDescription().equals(sProvider.getDescription()) : sProvider.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getDescription() != null ? getDescription().hashCode() : 0;
        result = 31 * result + getId();
        return result;
    }

    @Override
    public String toString() {
        return "SProvider{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", user=" + user +
                ", aptitudes=" + aptitudes +
                ", coordenates=" + coordenates +
                '}';
    }
}
