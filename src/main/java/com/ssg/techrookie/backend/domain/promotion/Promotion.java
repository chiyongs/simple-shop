package com.ssg.techrookie.backend.domain.promotion;

import com.ssg.techrookie.backend.domain.promotionitem.PromotionItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionItem> promotionItems = new ArrayList<>();

    @Builder
    public Promotion(String promotionNm, Integer discountAmount, Double discountRate, LocalDate promotionStartDate, LocalDate promotionEndDate) {
        this.promotionNm = promotionNm;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.promotionStartDate = promotionStartDate;
        this.promotionEndDate = promotionEndDate;
    }

    //== 연관관계 메서드 ==//
    public void addPromotionItem(PromotionItem promotionItem) {
        promotionItems.add(promotionItem);
        promotionItem.partOf(this);
    }

    //== 조회 메서드 ==//
    public boolean isDiscountByAmount() {
        return discountAmount != null && discountRate == null;
    }

}
