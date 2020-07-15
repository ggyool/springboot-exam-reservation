package org.ggyool.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

class RestaurantTests {
    @Test
    public void creation(){
        Restaurant restaurant = new Restaurant(1004L,"bob zip", "seoul");
        assertThat(restaurant.getId()).isEqualTo(1004);
        assertThat(restaurant.getName()).isEqualTo("bob zip");
        assertThat(restaurant.getAddress()).isEqualTo("seoul");
    }

    @Test
    public void information(){
        Restaurant restaurant = new Restaurant(1004L,"bob zip", "seoul");
        assertThat(restaurant.getInformation()).isEqualTo("bob zip in seoul");
    }


}