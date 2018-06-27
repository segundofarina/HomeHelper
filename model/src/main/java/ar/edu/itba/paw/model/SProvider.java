package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "userid")
@Table(name = "serviceProviders")
public class SProvider {

    @Column(name = "description",length = 100, nullable = false)
    private String description;

    @Id
    @Column(name = "userid")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private Set<Aptitude> aptitudes;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private Set<WorkingZone> workingZones;

    /*package*/ SProvider(){

    }

    public SProvider(User user, String description, Set<Aptitude> aptitudes, Set<WorkingZone> workingZones) {
        this.id=user.getId();
        this.user=user;
        this.description = description;
        this.aptitudes = aptitudes;
        this.workingZones = workingZones;
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

    public double getQualityCalification(){

        if(aptitudes.size() == 0 ){
            return 0;
        }

        double quality = 0;

        for(Aptitude aptitude : aptitudes){
            quality += aptitude.getQualityCalification();
        }
        return Math.floor((quality/aptitudes.size()) * 100) / 100;
    }

    public double getCleannessCalification(){

        if(aptitudes.size() == 0 ){
            return 0;
        }

        double cleanness = 0;

        for(Aptitude aptitude : aptitudes){
            cleanness += aptitude.getCleannessCalification();
        }

        return Math.floor((cleanness/aptitudes.size()) * 100) / 100;
    }

    public double getPriceCalification(){

        if(aptitudes.size() == 0 ){
            return 0;
        }

        double price = 0;

        for(Aptitude aptitude : aptitudes){
            price += aptitude.getPriceCalification();
        }

        return Math.floor((price/aptitudes.size()) * 100) / 100;
    }

    public double getPunctualityCalification(){

        if(aptitudes.size() == 0 ){
            return 0;
        }

        double punctuality = 0;

        for(Aptitude aptitude : aptitudes){
            punctuality += aptitude.getPunctualityCalification();
        }
        return Math.floor((punctuality/aptitudes.size()) * 100) / 100;
    }

    public double getTreatmentCalification(){

        if(aptitudes.size() == 0 ){
            return 0;
        }

        double treatment = 0;

        for(Aptitude aptitude : aptitudes){
            treatment += aptitude.getTreatmentCalification();
        }
        return Math.floor((treatment/aptitudes.size()) * 100) / 100;
    }

    public double getGeneralCalification() {

        if(aptitudes.size() == 0 ){
            return 0;
        }

        double general = 0;

        for(Aptitude aptitude : aptitudes){
            general += aptitude.getGeneralCalification();
        }
        return Math.floor((general/aptitudes.size()) * 100) / 100;
    }

    public boolean hasReviews(){

        for(Aptitude aptitude: aptitudes){
            if(aptitude.hasReviews()){
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public List<Neighborhood> getNeighborhoods(){
        List<Neighborhood> neighborhoods = new ArrayList<>();
        for(WorkingZone workingZone: workingZones){
            neighborhoods.add(workingZone.getNeighborhood());
        }
        return neighborhoods;
    }

    public Set<WorkingZone> getWorkingZones() {
        return this.workingZones;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setAptitudes(Set<Aptitude> aptitudes) {
        this.aptitudes = aptitudes;
    }

    public void setWorkingZones(Set<WorkingZone> workingZones) {
        this.workingZones = workingZones;
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
}
