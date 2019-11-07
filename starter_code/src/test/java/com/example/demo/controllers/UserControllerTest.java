package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    private User user;


    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);

        user = new User();
        user.setUsername("Jose");
        user.setPassword("thisIsHashed");
        user.setId(1);
        user.setCart(new Cart());

    }

    @Test
    public void create_user_happy_path()  {
        when(encoder.encode("nuevopassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("Jose");
        r.setPassword("nuevopassword");
        r.setConfirmPassword("nuevopassword");

        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("Jose", user.getUsername());
        assertEquals("thisIsHashed", user.getPassword());

    }

    @Test
    public void create_user_wrong_path()  {
        when(encoder.encode("short")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("Jose");
        r.setPassword("short");
        r.setConfirmPassword("short");

        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void findUserByIdTest() {

        when(userRepo.findById((long) 1)).thenReturn(java.util.Optional.of(user));

        ResponseEntity<User> r = userController.findById(Long.valueOf(1));
        assertNotNull(r);
        assertEquals(HttpStatus.OK, r.getStatusCode());
    }
    @Test
    public void findUserByNameTest() {

        when(userRepo.findByUsername("Jose")).thenReturn(user);

        ResponseEntity<User> r = userController.findByUserName("Jose");
        assertNotNull(r);
        assertEquals(HttpStatus.OK, r.getStatusCode());
    }
}
