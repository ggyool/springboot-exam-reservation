package org.ggyool.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotNull
    private Long categoryId;
    @Transient
    private List<MenuItem> menuItems = new ArrayList<>();
    @Transient
    private List<Review> reviews = new ArrayList<>();

    public void updateInformation(String name, String address, Long categoryId){
        this.name = name;
        this.address = address;
        this.categoryId = categoryId;
    }

    public String getInformation() {
        return name + " in " + address;
    }

    private void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
    public void setMenuItems(List<MenuItem> menuItems) {
        for(MenuItem menuItem : menuItems){
            addMenuItem(menuItem);
        }
    }

    private void addReview(Review review) {
        reviews.add(review);
    }
    public void setReviews(List<Review> reviews) {
        for(Review review : reviews){
            addReview(review);
        }
    }


}
