package org.ggyool.eatgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @Transient // db에 저장 하지 않도록
    private List<MenuItem> menuItems = new ArrayList<>();


    public void updateInformation(String name, String address){
        this.name = name;
        this.address = address;
    }

    public String getInformation() {
        return name + " in " + address;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
    public void setMenuItems(List<MenuItem> menuItems) {
        for(MenuItem menuItem : menuItems){
            addMenuItem(menuItem);
        }
    }

}
