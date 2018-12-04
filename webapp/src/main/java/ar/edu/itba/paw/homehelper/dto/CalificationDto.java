package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.Review;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CalificationDto {

    //TODO change this it does not work
    private TreeMap<String,Double> califications;

    public CalificationDto() {
    }

    public CalificationDto(Aptitude aptitude, Locale locale, MessageSource messageSource) {
        califications = new TreeMap<>();
        int i = 1;
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale),aptitude.getQualityCalification());
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale),aptitude.getCleannessCalification());
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale),aptitude.getPriceCalification());
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale),aptitude.getPunctualityCalification());
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale),aptitude.getTreatmentCalification());
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale),aptitude.getGeneralCalification());
    }

    public CalificationDto(Review review, Locale locale, MessageSource messageSource) {
        califications = new TreeMap<>();
        int i=1;
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale), (double) review.getQualityCalification());
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale), (double) review.getCleannessCalification());
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale), (double) review.getPriceCalification());
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale), (double) review.getPunctualityCalification());
        califications.put(messageSource.getMessage("form.calification."+i++,null,locale), (double) review.getTreatmentCalification());

        int general = 0;
        for(Double calification: califications.values()){
            general+=calification;
        }
        califications.put("form.calification."+i,(double) general/califications.size());
    }

    /* Debugging */

    @Override
    public String toString() {
        String ans = "CalificationDto{";

        for(Map.Entry<String,Double> calification : califications.entrySet()){
            ans += calification.getKey()+":"+calification.getValue();
        }

        ans += "}";

        return ans;
    }

    public TreeMap<String, Double> getCalifications() {
        return califications;
    }
}
