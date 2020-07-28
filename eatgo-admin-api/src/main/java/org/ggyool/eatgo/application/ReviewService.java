package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.Review;
import org.ggyool.eatgo.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    public Review addReview(Long restaurantid, Review review){
        return reviewRepository.save(
                Review.builder()
                .name(review.getName())
                .score(review.getScore())
                .description(review.getDescription())
                .restaurantId(restaurantid)
                .build()
        );
    }
}
