package com.ssg.techrookie.backend.domain.promotion;

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
class PromotionRepositoryTest {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void promotion_save_성공() {
        //given
        String promotionNm = "2022 쓱데이";
        int discountAmount = 1000;
        LocalDate promotionStartDate = LocalDate.of(2022,1,1);
        LocalDate promotionEndDate = LocalDate.of(2023,1,1);

        Promotion promotion = Promotion.builder()
                .promotionNm(promotionNm)
                .discountAmount(discountAmount)
                .promotionStartDate(promotionStartDate)
                .promotionEndDate(promotionEndDate)
                .build();

        //when
        testEntityManager.persist(promotion);

        //then
        Promotion findPromotion = promotionRepository.findById(promotion.getId()).get();

        assertThat(findPromotion).isEqualTo(promotion);
        assertThat(findPromotion.getPromotionNm()).isEqualTo(promotion.getPromotionNm());
        assertThat(findPromotion.getDiscountAmount()).isEqualTo(promotion.getDiscountAmount());
        assertThat(findPromotion.getPromotionStartDate()).isEqualTo(promotion.getPromotionStartDate());
        assertThat(findPromotion.getPromotionEndDate()).isEqualTo(promotion.getPromotionEndDate());
    }

}