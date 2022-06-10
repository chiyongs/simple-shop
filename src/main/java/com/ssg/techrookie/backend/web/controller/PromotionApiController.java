package com.ssg.techrookie.backend.web.controller;

import com.ssg.techrookie.backend.service.PromotionService;
import com.ssg.techrookie.backend.web.dto.ApiResponse;
import com.ssg.techrookie.backend.web.dto.item.PromotionItemResponseDto;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionResponseDto;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/promotions")
@RestController
public class PromotionApiController {

    private final PromotionService promotionService;

    /**
     * 프로모션 정보를 등록하는 메서드
     * @param requestDto (프로모션에 대한 정보 & 프로모션을 적용할 상품 목록)
     * @return 등록한 프로모션의 id
     */
    @PostMapping
    public ApiResponse<Long> promotionAdd(@Validated @RequestBody PromotionSaveRequestDto requestDto) {
        return ApiResponse.ok(promotionService.addPromotion(requestDto));
    }

    /**
     * 프로모션 id에 해당하는 프로모션 삭제 메서드
     * @param promotionId
     * @return 삭제된 프로모션의 id
     */
    @DeleteMapping
    public ApiResponse<Long> promotionDelete(@Validated @RequestParam Long promotionId) {
        promotionService.deletePromotion(promotionId);
        return ApiResponse.ok(promotionId);
    }

    /**
     * 상품에 존재하는 프로모션 정보를 반환하는 메서드
     * @param itemId
     * @return 상품의 정보와 프로모션 정보를 담고 있는 dto
     */
    @GetMapping
    public ApiResponse<PromotionItemResponseDto> promotionDetailOnItem(@Validated @RequestParam Long itemId) {
        return ApiResponse.ok(promotionService.findPromotionByItem(itemId));
    }

    /**
     * 프로모션 id에 해당하는 프로모션 정보 반환 메서드
     * @param promotionId
     * @return 프로모션 정보를 담고 있는 dto
     */
    @GetMapping("/{promotionId}")
    public ApiResponse<PromotionResponseDto> promotionDetail(@Validated @PathVariable Long promotionId) {
        return ApiResponse.ok(promotionService.findById(promotionId));
    }
}
