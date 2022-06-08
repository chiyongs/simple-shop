package com.ssg.techrookie.backend.web.controller;

import com.ssg.techrookie.backend.service.PromotionService;
import com.ssg.techrookie.backend.web.dto.ApiResponse;
import com.ssg.techrookie.backend.web.dto.item.PromotionItemResponseDto;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionResponseDto;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/promotions")
@RestController
public class PromotionApiController {

    private final PromotionService promotionService;

    @PostMapping
    public ApiResponse<Long> promotionAdd(@Validated @RequestBody PromotionSaveRequestDto requestDto) {
        return ApiResponse.ok(promotionService.addPromotion(requestDto));
    }

    @DeleteMapping
    public ApiResponse<Long> promotionDelete(@Validated @RequestParam Long promotionId) {
        promotionService.deletePromotion(promotionId);
        return ApiResponse.ok(promotionId);
    }

    @PostMapping("/{promotionId}/items")
    public ApiResponse<Long> promotionItemsAddOnPromotion(@Validated @PathVariable Long promotionId, @Validated @RequestBody List<Long> itemList) {
        promotionService.addPromotionItemsOnPromotion(promotionId, itemList);
        return ApiResponse.ok(promotionId);
    }

    @GetMapping
    public ApiResponse<PromotionItemResponseDto> promotionDetailOnItem(@Validated @RequestParam Long itemId) {
        return ApiResponse.ok(promotionService.findPromotionByItem(itemId));
    }

    @GetMapping("/{promotionId}")
    public ApiResponse<PromotionResponseDto> promotionDetail(@Validated @PathVariable Long promotionId) {
        return ApiResponse.ok(promotionService.findById(promotionId));
    }
}
