package com.ssg.techrookie.backend.web.dto.promotion;

import com.ssg.techrookie.backend.domain.promotion.Promotion;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PromotionResponseDto {
    private Long id;
    private String promotionNm;
    private Integer discountAmount;
    private Double discountRate;
    private LocalDate promotionStartDate;
    private LocalDate promotionEndDate;

    public PromotionResponseDto(Promotion entity) {
        this.id = entity.getId();
        this.promotionNm = entity.getPromotionNm();
        this.discountAmount = entity.getDiscountAmount();
        this.discountRate = entity.getDiscountRate();
        this.promotionStartDate = entity.getPromotionStartDate();
        this.promotionEndDate = entity.getPromotionEndDate();
    }
}
