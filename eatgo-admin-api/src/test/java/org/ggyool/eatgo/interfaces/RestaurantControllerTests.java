package org.ggyool.eatgo.interfaces;

import org.ggyool.eatgo.application.RestaurantService;
import org.ggyool.eatgo.domain.Restaurant;
import org.ggyool.eatgo.domain.RestaurantNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
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
                .categoryId(1L)
                .id(1004L)
                .name("bob zip")
                .address("seoul")
                .build();
        restaurants.add(restaurant);
        given(restaurantService.getRestaurants()).willReturn(restaurants);
        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")))
                .andExpect(content().string(
                        containsString("\"name\":\"bob zip\"")))
                .andExpect(content().string(
                        containsString("\"categoryId\":1")));
    }

    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("bob zip")
                .address("seoul")
                .build();

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);


        // content 보니까 getter 있는 내용 모두 생긴다. information의 경우 member변수는 아닌데 getter 만 있는데 나옴
        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")))
                .andExpect(content().string(
                        containsString("\"name\":\"bob zip\"")));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));
        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    public void createWithValidData() throws Exception {

        given(restaurantService.addRestaurant(any())).will(invocation -> {
           Restaurant restaurant = invocation.getArgument(0);
           return Restaurant.builder()
                   .id(1234L)
                   .name(restaurant.getName())
                   .address(restaurant.getAddress())
                   .build();
        });
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"br\", \"address\":\"seoul\", \"categoryId\":\"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1234"))
                .andExpect(content().string("{}"));
        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\", \"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"bob zip\", \"address\":\"busan\", \"categoryId\":\"1\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "bob zip", "busan", 1L);
    }
    @Test
    public void updateWithInvalidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\", \"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

}