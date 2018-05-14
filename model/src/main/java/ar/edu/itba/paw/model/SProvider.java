package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SProvider extends User{
    private String description;
    private List<Aptitude> aptitudes;
    private List<Neighborhood> neighborhoods;

    public SProvider(User user, String description, List<Aptitude> aptitudes, List<Neighborhood> neighborhoods) {
        super(user.getUsername(),user.getId(),user.getPassword(),user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhone(),user.getAddress(),user.getImage());

        this.description = description;
        this.aptitudes = aptitudes;
        this.neighborhoods = neighborhoods;
    }

    public String getDescription() {
        return description;
    }

    public List<Aptitude> getAptitudes() {
        return aptitudes;
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

    public List<Neighborhood> getNeighborhoods() {
        return this.neighborhoods;
    }



}
