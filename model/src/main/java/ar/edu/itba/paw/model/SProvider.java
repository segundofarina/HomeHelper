package ar.edu.itba.paw.model;

import java.util.List;
import java.util.Set;

public class SProvider extends User{
    private String description;
    private List<Aptitude> aptitudes;

    public SProvider(User user, String description, List<Aptitude> aptitudes) {
        super(user.getUsername(),user.getId(),user.getPassword(),user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhone());
        this.description = description;
        this.aptitudes = aptitudes;
    }

    public String getDescription() {
        return description;
    }

    public List<Aptitude> getAptitudes() {
        return aptitudes;
    }

    public double getQualityCalification(){

        double quality = 0;

        for(Aptitude aptitude : aptitudes){
            quality += aptitude.getQualityCalification();
        }

        return quality/aptitudes.size();
    }

    public double getCleannessCalification(){

        double cleanness = 0;

        for(Aptitude aptitude : aptitudes){
            cleanness += aptitude.getCleannessCalification();
        }

        return cleanness/aptitudes.size();
    }

    public double getPriceCalification(){

        double price = 0;

        for(Aptitude aptitude : aptitudes){
            price += aptitude.getPriceCalification();
        }

        return price/aptitudes.size();
    }

    public double getPunctualityCalification(){

        double punctuality = 0;

        for(Aptitude aptitude : aptitudes){
            punctuality += aptitude.getPunctualityCalification();
        }

        return punctuality/aptitudes.size();
    }

    public double getTreatmentCalification(){

        double treatment = 0;

        for(Aptitude aptitude : aptitudes){
            treatment += aptitude.getTreatmentCalification();
        }

        return treatment/aptitudes.size();
    }

    public double getGeneralCalification() {

        double general = 0;

        for(Aptitude aptitude : aptitudes){
            general += aptitude.getGeneralCalification();
        }

        return general/aptitudes.size();
    }


}
