package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.User;
import org.ggyool.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(String email, String name) {
        User user = User.builder().email(email).name(name).level(100L).build();
        return userRepository.save(user);
    }

    public User updateUser(Long id, String email, String name, Long level) {
        User user = userRepository.findById(id).orElse(null);
        user.update(email, name, level);
        return user;
    }

    public User deactiveUser(long id) {
        User user = userRepository.findById(id).orElse(null);
        user.deactivate();
        return user;
    }
}
