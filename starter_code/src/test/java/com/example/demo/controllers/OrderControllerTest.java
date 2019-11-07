package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepo = mock(UserRepository.class);
    private OrderRepository orderRepo = mock(OrderRepository.class);

    private User user;


    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepo);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepo);

        user = new User();
        user.setUsername("Jose");
        user.setPassword("thisIsHashed");
        user.setId(1);

        ArrayList<Item> items = new ArrayList();

        items.add(new Item() {{
            setName("Item 1");
            setPrice(BigDecimal.valueOf(123));
            setDescription("Desc item 1");
            setId((long) 1);
        }});
        items.add(new Item() {{
            setName("Item 2");
            setPrice(BigDecimal.valueOf(321));
            setDescription("Desc item 2");
            setId((long) 2);
        }});
        items.add(new Item() {{
            setName("Item 3");
            setPrice(BigDecimal.valueOf(111));
            setDescription("Desc item 3");
            setId((long) 3);
        }});
        Cart cart = new Cart();
        cart.setId((long) 1);
        cart.setItems(items);
        cart.setTotal(BigDecimal.valueOf(555));
        user.setCart(cart);
    }

    @Test
    public void submitTest() {

        when(userRepo.findByUsername("Jose")).thenReturn(user);

        final ResponseEntity<UserOrder> r = orderController.submit("Jose");
        assertNotNull(r);
        assertEquals(HttpStatus.OK, r.getStatusCode());

    }

    @Test
    public void submitWithUserNullTest() {

        when(userRepo.findByUsername("Jose")).thenReturn(null);

        final ResponseEntity<UserOrder> r = orderController.submit("Jose");
        assertNotNull(r);
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());

    }

    @Test
    public void getOrdersForUserTest() {
        when(userRepo.findByUsername("Jose")).thenReturn(user);

        final ResponseEntity<List<UserOrder>> r = orderController.getOrdersForUser("Jose");
        assertNotNull(r);
        assertEquals(HttpStatus.OK, r.getStatusCode());

    }

    @Test
    public void getOrdersForUserWithUserNullTest() {
        when(userRepo.findByUsername("Jose")).thenReturn(null);

        final ResponseEntity<List<UserOrder>> r = orderController.getOrdersForUser("Jose");
        assertNotNull(r);
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());

    }
}
