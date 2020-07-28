package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.MenuItem;
import org.ggyool.eatgo.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MenuItemService {

    private MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems){
        for (MenuItem menuItem : menuItems) {
            update(restaurantId, menuItem);
        }
    }

    private void update(Long restaurantId, MenuItem menuItem) {
        if(menuItem.isDestroy()){
            menuItemRepository.deleteById(menuItem.getId());
            return;
        }
        menuItemRepository.save(MenuItem.builder()
                // 존재하는 id를 save하면 에러가 날 것이라 생각했는데 update 쿼리가 나간다.
                .id(menuItem.getId())
                .restaurantId(restaurantId)
                .name(menuItem.getName())
                .build()
        );
    }
}
