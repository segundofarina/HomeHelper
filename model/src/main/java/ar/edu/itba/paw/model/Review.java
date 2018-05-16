package ar.edu.itba.paw.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Review {
    private User user;
    private HashMap<String,Integer> calification;
    private String comment;
    private Date date;

    public Review(int quality, int cleanness, int price, int punctuality, int treatment, String comment, Date date, User user) {

        calification = new HashMap<>();

        calification.put("Quality",quality);
        calification.put("Cleanness",cleanness);
        calification.put("Price",price);
        calification.put("Punctuality",punctuality);
        calification.put("Treatment",treatment);

        this.comment = comment;
        this.date = date;
        this.user = user;
    }

    public int getQualityCalification(){
        return calification.get("Quality");
    }

    public int getCleannessCalification(){
        return calification.get("Cleanness");
    }

    public int getPriceCalification(){
        return calification.get("Price");
    }

    public int getPunctualityCalification(){
        return calification.get("Punctuality");
    }

    public int getTreatmentCalification(){
        return calification.get("Treatment");
    }

    public int getGeneralCalification() {
        int generalCalification = 0;

        for(Map.Entry<String,Integer> entry : calification.entrySet()){
            generalCalification+= entry.getValue();
        }

        return generalCalification/calification.size();
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

}
