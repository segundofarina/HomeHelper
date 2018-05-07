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
        return Math.floor((quality/aptitudes.size()) * 100) / 100;
    }

    public double getCleannessCalification(){

        double cleanness = 0;

        for(Aptitude aptitude : aptitudes){
            cleanness += aptitude.getCleannessCalification();
        }

        return Math.floor((cleanness/aptitudes.size()) * 100) / 100;
    }

    public double getPriceCalification(){

        double price = 0;

        for(Aptitude aptitude : aptitudes){
            price += aptitude.getPriceCalification();
        }

        return Math.floor((price/aptitudes.size()) * 100) / 100;
    }

    public double getPunctualityCalification(){

        double punctuality = 0;

        for(Aptitude aptitude : aptitudes){
            punctuality += aptitude.getPunctualityCalification();
        }
        return Math.floor((punctuality/aptitudes.size()) * 100) / 100;
    }

    public double getTreatmentCalification(){

        double treatment = 0;

        for(Aptitude aptitude : aptitudes){
            treatment += aptitude.getTreatmentCalification();
        }
        return Math.floor((treatment/aptitudes.size()) * 100) / 100;
    }

    public double getGeneralCalification() {

        double general = 0;

        for(Aptitude aptitude : aptitudes){
            general += aptitude.getGeneralCalification();
        }
        return Math.floor((general/aptitudes.size()) * 100) / 100;
    }


}