package com.ssg.techrookie.backend.web.dto.item;

import com.ssg.techrookie.backend.domain.item.Item;
import com.ssg.techrookie.backend.domain.promotionitem.PromotionItem;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionResponseDto;

import lombok.Getter;


@Getter
public class PromotionItemResponseDto {

    private ItemResponseDto item;
    private PromotionResponseDto promotion;
    private Integer promotionPrice;

    public PromotionItemResponseDto(PromotionItem promotionItem) {
        this.item = new ItemResponseDto(promotionItem.getItem());
        this.promotion = new PromotionResponseDto(promotionItem.getPromotion());
        this.promotionPrice = promotionItem.getPromotionPrice();
    }

    public PromotionItemResponseDto(Item entity) {
        this.item = new ItemResponseDto(entity);
        this.promotionPrice = entity.getItemPrice();
    }
}
