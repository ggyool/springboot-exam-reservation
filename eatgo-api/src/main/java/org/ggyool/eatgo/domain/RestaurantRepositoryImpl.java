package org.ggyool.eatgo.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepositoryImpl() {
        restaurants.add(new Restaurant(1004L,"bob zip", "seoul"));
        restaurants.add(new Restaurant(2020L,"cyber food", "seoul"));
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurants;
    }

    @Override
    public Restaurant findById(Long id) {
        Restaurant restaurant = restaurants.stream()
                .filter(r->r.getId().equals(id))
                .findFirst()
                .orElse(null); // 찾지 못한 경우 null;
        return restaurant;
    }
}
