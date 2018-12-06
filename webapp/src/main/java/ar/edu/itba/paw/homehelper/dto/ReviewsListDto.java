package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.context.MessageSource;

import java.util.*;
import java.util.stream.Collectors;

public class ReviewsListDto {
    private List<AptitudeReviewDto> reviews;
    private int page;
    private int pageSize;
    private int maxPage;

    public ReviewsListDto() {
    }

    public ReviewsListDto(List<Review> reviews,int page, int pageSize, int maxPage) {
        this.reviews = new LinkedList<>();
        reviews.stream()
                .collect(Collectors.groupingBy(r -> r.getAptitude().getId()))
                .forEach((id,list)->
                        this.reviews.add(new AptitudeReviewDto(id,list))
                );
        this.page = page;
        this.pageSize = pageSize;
        this.maxPage= maxPage;


    }




    public List<AptitudeReviewDto> getReviews(){
        return reviews;
    }

    public void setReviews(List<AptitudeReviewDto> reviews) {
        this.reviews = reviews;
    }
}
