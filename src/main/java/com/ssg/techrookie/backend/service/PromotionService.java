package com.ssg.techrookie.backend.service;

import com.ssg.techrookie.backend.web.dto.item.PromotionItemResponseDto;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionResponseDto;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionSaveRequestDto;

import java.util.List;

public interface PromotionService {
    Long addPromotion(PromotionSaveRequestDto requestDto);
    void deletePromotion(Long promotionId);
    void addPromotionItemsOnPromotion(Long promotionId, List<Long> itemList);
    PromotionItemResponseDto findPromotionByItem(Long itemId);
    PromotionResponseDto findById(Long promotionId);
}
