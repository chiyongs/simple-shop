package com.ssg.techrookie.backend.domain.promotionitem;

import com.ssg.techrookie.backend.domain.item.Item;
import com.ssg.techrookie.backend.domain.item.ItemType;
import com.ssg.techrookie.backend.domain.promotion.Promotion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class PromotionItemRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PromotionItemRepository promotionItemRepository;

    private List<Item> items;
    private Promotion currentPromotion, pastPromotion;

    @DisplayName("프로모션 가격 결과가 0원 이하일 시 프로모션이 적용되지 않아야 함")
    @Test
    void calculatePriceByPromotion_lessOrEqual_Zero() {
        //given
        setUpItems();
        String promotionNm = "2022 쓱데이";
        int discountAmount = Integer.MAX_VALUE;
        LocalDate promotionStartDate = LocalDate.of(2022,1,1);
        LocalDate promotionEndDate = LocalDate.of(2023,1,31);

        currentPromotion = Promotion.builder()
                .promotionNm(promotionNm)
                .discountAmount(discountAmount)
                .promotionStartDate(promotionStartDate)
                .promotionEndDate(promotionEndDate)
                .build();


        //when
        List<PromotionItem> currentPromotionItems = items.stream().map(PromotionItem::new).collect(Collectors.toList());
        for(PromotionItem promotionItem : currentPromotionItems) {
            currentPromotion.addPromotionItem(promotionItem);
        }
        testEntityManager.persist(currentPromotion);
        List<PromotionItem> all = promotionItemRepository.findAll();

        //then
        assertThat(all.size()).isEqualTo(0);

    }

    @DisplayName("상품에 대한 진행중인 프로모션이 있는 경우 조회_성공")
    @Test
    void findByItemAndPromotionCurrentlyInProgress_성공() {
        //given
        setUpItems();
        setUpPastPromotion();
        setUpCurrentPromotion();

        //when
        List<PromotionItem> currentPromotionItems = items.stream().map(PromotionItem::new).collect(Collectors.toList());
        for(PromotionItem promotionItem : currentPromotionItems) {
            currentPromotion.addPromotionItem(promotionItem);
        }

        List<PromotionItem> pastPromotionItems = items.stream().map(PromotionItem::new).collect(Collectors.toList());
        for(PromotionItem promotionItem : pastPromotionItems) {
            pastPromotion.addPromotionItem(promotionItem);
        }

        testEntityManager.persist(currentPromotion);
        testEntityManager.persist(pastPromotion);

        //then
        for(Item item : items) {
            assertThat(promotionItemRepository.findByItemWhichPromotionCurrentlyInProgressOrderByPromotionPrice(item, LocalDate.now()).size())
                    .isEqualTo(1);
        }
        assertThat(promotionItemRepository.findByItemWhichPromotionCurrentlyInProgressOrderByPromotionPrice(items.get(0), LocalDate.now()).get(0).getPromotionPrice())
                .isEqualTo(19000);


    }

    private void setUpItems() {
        items = new ArrayList<>();

        String itemName = "노브랜드 버터링";
        int itemPrice = 20000;
        LocalDate displayStartDate = LocalDate.of(2022,1,1);
        LocalDate displayEndDate = LocalDate.of(2023,1,1);
        ItemType itemType = ItemType.일반;

        Item item1 = item(itemName, itemPrice, displayStartDate, displayEndDate, itemType);
        testEntityManager.persist(item1);

        itemName = "나이키 운동화";
        itemPrice = 40000;
        displayStartDate = LocalDate.of(2020,1,1);
        displayEndDate = LocalDate.of(2023,12,31);
        itemType = ItemType.기업회원상품;

        Item item2 = item(itemName, itemPrice, displayStartDate, displayEndDate, itemType);
        testEntityManager.persist(item2);

        itemName = "크리스마스 케이크";
        itemPrice = 30000;
        displayStartDate = LocalDate.of(2022,12,24);
        displayEndDate = LocalDate.of(2022,12,31);
        itemType = ItemType.일반;

        Item item3 = item(itemName, itemPrice, displayStartDate, displayEndDate, itemType);
        testEntityManager.persist(item3);
        items.add(item1);
        items.add(item2);
        items.add(item3);

    }

    private void setUpCurrentPromotion() {
        String promotionNm = "2022 쓱데이";
        int discountAmount = 1000;
        LocalDate promotionStartDate = LocalDate.of(2022,1,1);
        LocalDate promotionEndDate = LocalDate.of(2023,1,31);

        currentPromotion = Promotion.builder()
                .promotionNm(promotionNm)
                .discountAmount(discountAmount)
                .promotionStartDate(promotionStartDate)
                .promotionEndDate(promotionEndDate)
                .build();
    }
    private void setUpPastPromotion() {
        String promotionNm = "2021 쓱데이";
        int discountAmount = 2000;
        LocalDate promotionStartDate = LocalDate.of(2022,1,1);
        LocalDate promotionEndDate = LocalDate.of(2022,1,31);

        pastPromotion = Promotion.builder()
                .promotionNm(promotionNm)
                .discountAmount(discountAmount)
                .promotionStartDate(promotionStartDate)
                .promotionEndDate(promotionEndDate)
                .build();
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