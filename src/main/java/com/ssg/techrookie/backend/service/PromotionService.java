package com.ssg.techrookie.backend.service;

import com.ssg.techrookie.backend.web.dto.promotion.PromotionSaveRequestDto;

public interface PromotionService {
    Long addPromotion(PromotionSaveRequestDto requestDto);
    void deletePromotion(Long promotionId);
}
