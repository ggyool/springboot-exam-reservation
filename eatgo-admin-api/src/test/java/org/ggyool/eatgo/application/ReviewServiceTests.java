package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.Review;
import org.ggyool.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class ReviewServiceTests {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    public void getReviews(){
        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(Review.builder().description("delicious!").build());
        given(reviewRepository.findAll()).willReturn(mockReviews);

        List<Review> reviews = reviewService.getReviews();
        assertThat(reviews.get(0).getDescription()).isEqualTo("delicious!");

    }


}