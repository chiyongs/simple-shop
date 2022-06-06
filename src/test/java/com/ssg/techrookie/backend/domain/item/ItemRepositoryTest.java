package com.ssg.techrookie.backend.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void item_save_성공() {
        //given
        String itemName = "testItem";
        int itemPrice = 10000;
        LocalDate displayStartDate = LocalDate.of(2022,1,1);
        LocalDate displayEndDate = LocalDate.of(2023,1,1);
        ItemType itemType = ItemType.일반;

        Item item = item(itemName, itemPrice, displayStartDate, displayEndDate, itemType);

        //when
        Item findItem = itemRepository.save(item);

        //then
        assertThat(findItem).isEqualTo(item);
        assertThat(findItem.getItemName()).isEqualTo(item.getItemName());
        assertThat(findItem.getItemPrice()).isEqualTo(item.getItemPrice());
        assertThat(findItem.getItemType()).isEqualTo(item.getItemType());
        assertThat(findItem.getItemDisplayStartDate()).isEqualTo(item.getItemDisplayStartDate());
        assertThat(findItem.getItemDisplayEndDate()).isEqualTo(item.getItemDisplayEndDate());
        assertThat(findItem.getItemType()).isEqualTo(item.getItemType());

    }




    @Test
    void 일반회원이_구매가능한_현재_전시중인_상품조회() {
        //given
        makeItemList();

        //when
        List<Item> all = itemRepository.findCurrentlyDisplayingItemsByItemType(LocalDate.now(), ItemType.일반);

        //then
        assertThat(all.size()).isEqualTo(1);
        assertTrue(all.stream().allMatch(item -> item.getItemType()==ItemType.일반));

        LocalDate now = LocalDate.now();
        assertTrue(all.stream().allMatch(item -> item.getItemDisplayStartDate().isBefore(now)));
        assertTrue(all.stream().allMatch(item -> item.getItemDisplayEndDate().isAfter(now)));

    }

    @Test
    void 기업회원이_구매가능한_현재_전시중인_상품조회() {
        //given
        makeItemList();

        //when
        List<Item> all = itemRepository.findAllItemsCurrentlyDisplaying(LocalDate.now());

        //then
        assertThat(all.size()).isEqualTo(2);
        assertTrue(all.stream().anyMatch(item -> item.getItemType() == ItemType.기업회원상품));
        assertTrue(all.stream().anyMatch(item -> item.getItemType() == ItemType.일반));

        LocalDate now = LocalDate.now();
        assertTrue(all.stream().allMatch(item -> item.getItemDisplayStartDate().isBefore(now)));
        assertTrue(all.stream().allMatch(item -> item.getItemDisplayEndDate().isAfter(now)));

    }

    private void makeItemList() {
        String itemName = "노브랜드 버터링";
        int itemPrice = 20000;
        LocalDate displayStartDate = LocalDate.of(2022,1,1);
        LocalDate displayEndDate = LocalDate.of(2023,1,1);
        ItemType itemType = ItemType.일반;

        testEntityManager.persist((item(itemName, itemPrice, displayStartDate, displayEndDate, itemType)));

        itemName = "나이키 운동화";
        itemPrice = 40000;
        displayStartDate = LocalDate.of(2020,1,1);
        displayEndDate = LocalDate.of(2023,12,31);
        itemType = ItemType.기업회원상품;

        testEntityManager.persist((item(itemName, itemPrice, displayStartDate, displayEndDate, itemType)));

        itemName = "크리스마스 케이크";
        itemPrice = 30000;
        displayStartDate = LocalDate.of(2022,12,24);
        displayEndDate = LocalDate.of(2022,12,31);
        itemType = ItemType.일반;

        testEntityManager.persist((item(itemName, itemPrice, displayStartDate, displayEndDate, itemType)));
    }

    private static Item item(String itemName, int itemPrice, LocalDate displayStartDate, LocalDate displayEndDate, ItemType itemType) {
        return Item.builder()
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemDisplayStartDate(displayStartDate)
                .itemDisplayEndDate(displayEndDate)
                .itemType(itemType)
                .build();
    }

}