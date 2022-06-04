package com.ssg.techrookie.backend.domain.promotion;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Promotion {

    @Id @GeneratedValue
    @Column(name = "promotion_id")
    private Long id;

    private String promotionNm;

    private Integer discountAmount;
    private Double discountRate;

    private LocalDate promotionStartDate;
    private LocalDate promotionEndDate;

    @Builder
    public Promotion(String promotionNm, Integer discountAmount, Double discountRate, LocalDate promotionStartDate, LocalDate promotionEndDate) {
        this.promotionNm = promotionNm;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.promotionStartDate = promotionStartDate;
        this.promotionEndDate = promotionEndDate;
    }
}
