package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private ItemRepository itemRepo = mock(ItemRepository.class);

    private User user;
    private Item item;


    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepo);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepo);

        user = new User();
        user.setId(0);
        user.setPassword("thisIsHashed");
        user.setUsername("Jose");
        user.setCart(new Cart());

        item = new Item();
        item.setId(Long.valueOf(0));
        item.setDescription("Item 1 desc");
        item.setName("Item 1");
        item.setPrice(BigDecimal.valueOf(123));


    }

    @Test
    public void addToCartTest() {

        when(userRepo.findByUsername("Jose")).thenReturn(user);

        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("Jose");
        r.setItemId(1);
        r.setQuantity(1);

        Optional<Item> optionalItem = Optional.of(item);

        when(itemRepo.findById(r.getItemId())).thenReturn(optionalItem);

        final ResponseEntity<Cart> response = cartController.addTocart(r);

        assertNotNull(response);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        Cart cart = response.getBody();
        assertNotNull(cart);
        assertEquals(1, cart.getItems().size());
    }

    @Test
    public void addToCartWithUserNullTest() {

        when(userRepo.findByUsername("Jose")).thenReturn(null);

        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("Jose");
        r.setItemId(1);
        r.setQuantity(1);

        Optional<Item> optionalItem = Optional.of(item);

        when(itemRepo.findById(r.getItemId())).thenReturn(optionalItem);

        final ResponseEntity<Cart> response = cartController.addTocart(r);

        assertNotNull(response);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void addToCartWithoutItemTest() {

        when(userRepo.findByUsername("Jose")).thenReturn(user);

        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("Jose");
        r.setItemId(1);
        r.setQuantity(1);


        when(itemRepo.findById(r.getItemId())).thenReturn(Optional.empty());

        final ResponseEntity<Cart> response = cartController.addTocart(r);

        assertNotNull(response);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



    @Test
    public void removeFromCartTest() {
        when(userRepo.findByUsername("Jose")).thenReturn(user);

        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("Jose");
        r.setItemId(1);
        r.setQuantity(1);
        Optional<Item> optionalItem = Optional.of(item);

        when(itemRepo.findById(r.getItemId())).thenReturn(optionalItem);

        final ResponseEntity<Cart> response = cartController.removeFromcart(r);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Cart cart = response.getBody();
        assertNotNull(cart);
        assertEquals(0, cart.getItems().size());

    }
    @Test
    public void removeFromCartWithUserNullTest() {
        when(userRepo.findByUsername("Jose")).thenReturn(null);

        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("Jose");
        r.setItemId(1);
        r.setQuantity(1);
        Optional<Item> optionalItem = Optional.of(item);

        when(itemRepo.findById(r.getItemId())).thenReturn(optionalItem);

        final ResponseEntity<Cart> response = cartController.removeFromcart(r);

        assertNotNull(response);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void removeFromCartWithoutItemTest() {
        when(userRepo.findByUsername("Jose")).thenReturn(user);

        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("Jose");
        r.setItemId(1);
        r.setQuantity(1);

        when(itemRepo.findById(r.getItemId())).thenReturn(Optional.empty());

        final ResponseEntity<Cart> response = cartController.removeFromcart(r);

        assertNotNull(response);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
}
