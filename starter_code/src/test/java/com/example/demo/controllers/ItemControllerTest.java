package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepo = mock(ItemRepository.class);
    private List<Item> items;


    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);

        items = new ArrayList();

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

    }

    @Test
    public void getAllTest() {

        when(itemRepo.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> r = itemController.getItems();
        assertNotNull(r);
        assertEquals(HttpStatus.OK, r.getStatusCode());
    }

    @Test
    public void getOneItemByIdTest() {

        when(itemRepo.findById((long) 1)).thenReturn(java.util.Optional.ofNullable(items.get(1)));

        ResponseEntity<Item> r = itemController.getItemById(Long.valueOf(1));
        assertNotNull(r);
        assertEquals(HttpStatus.OK, r.getStatusCode());
    }

    @Test
    public void getOneItemByNameTest(){
        when(itemRepo.findByName("Jose")).thenReturn(items);

        ResponseEntity<List<Item>> r = itemController.getItemsByName("Jose");
        assertNotNull(r);
        assertEquals(HttpStatus.OK, r.getStatusCode());
    }

}
