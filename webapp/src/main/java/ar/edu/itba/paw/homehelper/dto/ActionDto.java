package ar.edu.itba.paw.homehelper.dto;

public enum ActionDto {
    CONFIRM, COMPLETE, REJECT;

    public static ActionDto parse(String action) {
       if(action == null){
           return null;
       }else if(action.compareToIgnoreCase("confirm")==0){
           return CONFIRM;
       }else if(action.compareToIgnoreCase("complete")==0){
           return COMPLETE;
       }else if(action.compareToIgnoreCase("reject")==0){
           return REJECT;
       }else{
           return null;
       }
    }

}
