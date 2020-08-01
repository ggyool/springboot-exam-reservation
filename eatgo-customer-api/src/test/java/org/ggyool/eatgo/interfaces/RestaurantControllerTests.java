package org.ggyool.eatgo.interfaces;

import org.ggyool.eatgo.application.RestaurantService;
import org.ggyool.eatgo.domain.MenuItem;
import org.ggyool.eatgo.domain.Restaurant;
import org.ggyool.eatgo.domain.RestaurantNotFoundException;
import org.ggyool.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RestaurantController.class)
class RestaurantControllerTests {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("bob zip")
                .address("seoul")
                .build();
        restaurants.add(restaurant);
        given(restaurantService.getRestaurants("seoul", 1L)).willReturn(restaurants);
        mvc.perform(get("/restaurants?region=seoul&categoryId=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")))
                .andExpect(content().string(
                        containsString("\"name\":\"bob zip\"")))
                .andExpect(content().string(
                        containsString("\"categoryId\":1")));;
    }

    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("bob zip")
                .address("seoul")
                .menuItems(new ArrayList<>(Arrays.asList(MenuItem.builder().name("kimchi").build())))
                .reviews(new ArrayList<>(Arrays.asList(Review.builder().name("ggyool").score(5).description("delicious!").build())))
                .build();

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);


        // content 보니까 getter 있는 내용 모두 생긴다. information의 경우 member변수는 아닌데 getter 만 있는데 나옴
        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")))
                .andExpect(content().string(
                        containsString("\"name\":\"bob zip\"")))
                .andExpect(content().string(
                        containsString("kimchi")
                ))
                .andExpect(content().string(
                        containsString("delicious")
                ));

    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));
        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

}