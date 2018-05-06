package ar.edu.itba.paw.model;

import java.util.Date;

public enum Status {
    Pending, Confirmed, Done;

    public static Status getStatus(int status) {
        if(status == 1){
            return Pending;
        }else if(status == 2){
            return Confirmed;
        }else if(status == 3){
            return Done;
        }else{
            return null;
        }
    }

    public static Status getStatus(String status) {
        if(status.equals("Pending")){
            return Pending;
        }else if(status.equals("Confirmed")){
            return Confirmed;
        }else if(status.equals("Done")){
            return Done;
        }else{
            return null;
        }
    }

}
