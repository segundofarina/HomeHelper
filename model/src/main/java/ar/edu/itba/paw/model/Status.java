package ar.edu.itba.paw.model;

public enum Status {
    Pending(1), Confirmed(2), Done(3), Reject(4), Unverified(5);

    private int numVal;

    Status(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }

    @Override
    public String toString() {
        if (numVal == 1) {
            return "Pending";
        }
        if (numVal == 2) {
            return "Confirmed";
        }
        if (numVal == 3) {
            return "Done";
        }
        if (numVal == 4) {
            return "Reject";
        }
        if (numVal == 5){
            return "Unverified";
        }
        return "Undefined";
    }


    public static Status getStatus(int status) {
        if (status == 1) {
            return Pending;
        } else if (status == 2) {
            return Confirmed;
        } else if (status == 3) {
            return Done;
        } else if (status == 4) {
            return Reject;
        } else if (status == 5) {
            return Unverified;
        }else{
            return null;
        }
    }

    public static Status getStatus(String status) {
        if (status.equals("Pending")) {
            return Pending;
        } else if (status.equals("Confirmed")) {
            return Confirmed;
        } else if (status.equals("Done")) {
            return Done;
        } else if (status.equals("Reject")) {
            return Reject;
        } else if (status.equals("Unverified")) {
            return Unverified;
        }else {
            return null;
        }
    }

}
