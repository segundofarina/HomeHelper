package ar.edu.itba.paw.model;

public enum Status {
    Pending(1), Confirmed(2), Done(3);

    private int numVal;

    Status(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }

    @Override
    public String toString() {
        if(numVal == 1) {
            return "Pending";
        }
        if(numVal == 2) {
            return "Confirmed";
        }
        if(numVal == 3) {
            return "Done";
        }
        return "Undefined";
    }


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
