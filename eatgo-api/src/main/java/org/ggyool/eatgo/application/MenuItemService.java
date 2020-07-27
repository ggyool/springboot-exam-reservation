package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.MenuItem;
import org.ggyool.eatgo.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems){
        for (MenuItem menuItem : menuItems) {
            menuItemRepository.save(MenuItem.builder()
                    .restaurantId(restaurantId)
                    .name(menuItem.getName())
                    .build()
            );
        }
    }
}
