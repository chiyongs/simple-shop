package com.ssg.techrookie.backend.service.impl;

import com.ssg.techrookie.backend.domain.promotion.Promotion;
import com.ssg.techrookie.backend.domain.promotion.PromotionRepository;
import com.ssg.techrookie.backend.exception.CustomException;
import com.ssg.techrookie.backend.exception.ErrorCode;
import com.ssg.techrookie.backend.service.PromotionService;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    @Transactional
    public Long addPromotion(PromotionSaveRequestDto requestDto) {
        return promotionRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    @Transactional
    public void deletePromotion(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROMOTION_NOT_FOUND));

        promotionRepository.delete(promotion);
    }
}
