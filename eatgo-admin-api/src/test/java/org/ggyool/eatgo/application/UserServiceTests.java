package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.User;
import org.ggyool.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUP(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void getUsers(){
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder().email("test@example.com").name("test").level(1L).build());
        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();

        assertThat(users.get(0).getName()).isEqualTo("test");
    }

    @Test
    public void addUser(){
        String email = "admin@example.com";
        String name = "admin";
        User mockUser = User.builder().email(email).name(name).build();
        given(userRepository.save(any())).willReturn(mockUser);
        User user = userService.addUser(email, name);
        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    public void updateUser(){
        String email = "admin@example.com";
        String name = "modified admin";
        Long id = 1004L;
        Long level = 100L;

        User mockUser = User.builder()
                .email(email).name("before name").id(id).level(1L).build();
        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));
        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(eq(id));
        assertThat(user.getName()).isEqualTo("modified admin");
        assertThat(user.isAdmin()).isEqualTo(true);
    }

    @Test
    public void deactiveUser(){
        String email = "admin@example.com";
        String name = "admin";
        Long id = 1004L;
        Long level = 100L;

        User mockUser = User.builder()
                .email(email).name(name).id(id).level(level).build();
        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));


        User user = userService.deactiveUser(1004L);

        verify(userRepository).findById(1004L);
        assertThat(user.isAdmin()).isEqualTo(false);
        assertThat(user.isActive()).isEqualTo(false);
    }
}