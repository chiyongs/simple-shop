package com.ssg.techrookie.backend.domain.promotionitem;

import com.ssg.techrookie.backend.domain.item.Item;
import com.ssg.techrookie.backend.domain.promotion.Promotion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PromotionItem {

    @Id @GeneratedValue
    @Column(name = "promotion_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer promotionPrice;

    public PromotionItem(Item item) {
        this.item = item;
    }

    //== 연관관계 메서드 ==//
    public boolean partOf(Promotion promotion) {
        this.promotion = promotion;
        this.promotionPrice = calculatePriceByPromotion();
        if(promotionPrice > 0) return true;
        return false;
    }

    //== 비즈니스 메서드 ==//
    /** 프로모션 적용 후 가격 계산 **/
    public int calculatePriceByPromotion() {
        if(promotion.isDiscountByAmount()) return item.getItemPrice() - promotion.getDiscountAmount();
        return (int) (item.getItemPrice() * (1 - promotion.getDiscountRate()));
    }


}
