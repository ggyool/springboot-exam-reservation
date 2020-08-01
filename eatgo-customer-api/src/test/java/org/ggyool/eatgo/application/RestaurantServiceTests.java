package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RestaurantServiceTests {

    private RestaurantService restaurantService;
    // 자바 객체만 사용할 때 @Mock
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private ReviewRepository reviewRepository;

    // junit5의 Before
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository, reviewRepository);
    }


    public void mockRestaurantRepository(){
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("bob zip")
                .address("seoul")
                .menuItems(new ArrayList<>())
                .reviews(new ArrayList<>())
                .build();
        restaurants.add(restaurant);
        given(restaurantRepository.findAllByAddressContainingAndCategoryId("seoul", 1L)).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder().name("kimchi").build());
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder().name("ggyool").score(5).description("delicious!").build());
        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(reviews);
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));
        verify(reviewRepository).findAllByRestaurantId(eq(1004L));
        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(restaurant.getId()).isEqualTo(1004L);
        assertThat(menuItem.getName()).isEqualTo("kimchi");

        Review review = restaurant.getReviews().get(0);
        assertThat(review.getDescription()).isEqualTo("delicious!");
    }

    @Test
    public void getRestaurantsWithExisted(){
        String region = "seoul";
        List<Restaurant> restaurants = restaurantService.getRestaurants(region, 1L);
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId()).isEqualTo(1004L);
    }

    // junit5 에서는 expected x
    @Test
    public void getRestaurantsWithNotExisted(){
        assertThatThrownBy(()->{restaurantService.getRestaurant(404L);})
                .isInstanceOf(RestaurantNotFoundException.class);
    }

}