package org.ggyool.eatgo.interfaces;

import org.ggyool.eatgo.application.UserService;
import org.ggyool.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        String email = resource.getEmail();
        String name = resource.getName();
        User user = userService.addUser(email, name);
        String url = "/users/" + user.getId();
        URI location = new URI(url);
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/users/{userId}")
    public String update(
            @PathVariable("userId") Long id,
            @RequestBody User resource
    ){
        String email = resource.getEmail();
        String name = resource.getName();
        Long level = resource.getLevel();
        userService.updateUser(id, email, name, level);
        return "{}";
    }

    @DeleteMapping("/users/{id}")
    public String delete(
            @PathVariable("id") Long id
    ){
        userService.deactiveUser(id);
        return "{}";
    }
}
