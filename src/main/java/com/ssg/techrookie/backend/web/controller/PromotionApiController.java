package com.ssg.techrookie.backend.web.controller;

import com.ssg.techrookie.backend.service.PromotionService;
import com.ssg.techrookie.backend.web.dto.ApiResponse;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/promotions")
@RestController
public class PromotionApiController {

    private final PromotionService promotionService;

    @PostMapping
    public ApiResponse<Long> promotionAdd(@Validated @RequestBody PromotionSaveRequestDto requestDto) {
        return ApiResponse.ok(promotionService.addPromotion(requestDto));
    }
}
