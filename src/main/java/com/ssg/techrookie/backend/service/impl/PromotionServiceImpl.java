package com.ssg.techrookie.backend.service.impl;

import com.ssg.techrookie.backend.domain.promotion.PromotionRepository;
import com.ssg.techrookie.backend.service.PromotionService;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;


    @Transactional
    @Override
    public Long addPromotion(PromotionSaveRequestDto requestDto) {
        return promotionRepository.save(requestDto.toEntity()).getId();
    }
}
