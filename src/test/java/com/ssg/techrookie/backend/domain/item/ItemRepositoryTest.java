package com.ssg.techrookie.backend.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

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

        Item item = Item.builder()
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemDisplayStartDate(displayStartDate)
                .itemDisplayEndDate(displayEndDate)
                .itemType(itemType)
                .build();

        //when
        testEntityManager.persist(item);

        //then
        Item findItem = itemRepository.findById(item.getId()).get();
        assertThat(findItem).isEqualTo(item);
        assertThat(findItem.getItemName()).isEqualTo(item.getItemName());
        assertThat(findItem.getItemPrice()).isEqualTo(item.getItemPrice());
        assertThat(findItem.getItemType()).isEqualTo(item.getItemType());
        assertThat(findItem.getItemDisplayStartDate()).isEqualTo(item.getItemDisplayStartDate());
        assertThat(findItem.getItemDisplayEndDate()).isEqualTo(item.getItemDisplayEndDate());
        assertThat(findItem.getItemType()).isEqualTo(item.getItemType());
    }
}