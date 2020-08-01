package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.Restaurant;
import org.ggyool.eatgo.domain.RestaurantNotFoundException;
import org.ggyool.eatgo.domain.RestaurantRepository;
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
import static org.mockito.BDDMockito.given;

class RestaurantServiceTests {

    private RestaurantService restaurantService;
    // 자바 객체만 사용할 때 @Mock
    @Mock
    private RestaurantRepository restaurantRepository;

    // junit5의 Before
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();
        restaurantService = new RestaurantService(restaurantRepository);
    }


    public void mockRestaurantRepository(){
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("bob zip")
                .address("seoul")
                .menuItems(new ArrayList<>())
                .reviews(new ArrayList<>())
                .build();
        restaurants.add(restaurant);
        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }


    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        assertThat(restaurant.getId()).isEqualTo(1004L);
    }

    @Test
    public void getRestaurantsWithExisted(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId()).isEqualTo(1004L);
    }

    // junit5 에서는 expected x
    @Test
    public void getRestaurantsWithNotExisted(){
        assertThatThrownBy(()->{restaurantService.getRestaurant(404L);})
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    public void addRestaurant(){
        given(restaurantRepository.save(any())).will(invocation->{
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });
        Restaurant restaurant = Restaurant.builder()
                .id(1234L)
                .name("bob zip")
                .address("seoul")
                .build();

        Restaurant created = restaurantService.addRestaurant(restaurant);
        assertThat(created.getId()).isEqualTo(1234L);
    }

    @Test
    public void updateRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("bob zip")
                .address("seoul")
                .build();
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
        Restaurant updated = restaurantService.updateRestaurant(1004L, "bab zip", "busan", 1L);
        assertThat(restaurant.getName()).isEqualTo("bab zip");
        assertThat(restaurant.getAddress()).isEqualTo("busan");
        assertThat(restaurant).isEqualTo(updated);
    }




}