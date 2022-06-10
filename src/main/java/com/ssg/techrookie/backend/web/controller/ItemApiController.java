package com.ssg.techrookie.backend.web.controller;

import com.ssg.techrookie.backend.service.ItemService;
import com.ssg.techrookie.backend.web.dto.ApiResponse;
import com.ssg.techrookie.backend.web.dto.item.ItemResponseDto;
import com.ssg.techrookie.backend.web.dto.item.ItemSaveRequestDto;
import com.ssg.techrookie.backend.web.dto.item.PromotionItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
@RestController
public class ItemApiController {

    private final ItemService itemService;

    /**
     * 상품 등록 메서드
     * @param requestDto (상품 정보를 담고 있는 dto)
     * @return 등록한 상품의 id
     */
    @PostMapping
    public ApiResponse<Long> itemAdd(@Validated @RequestBody ItemSaveRequestDto requestDto) {
        return ApiResponse.ok(itemService.addItem(requestDto));
    }

    /**
     * 상품 id에 해당하는 상품 삭제 메서드
     * @param itemId
     * @return 삭제한 상품의 id
     */
    @DeleteMapping
    public ApiResponse<Long> itemDelete(@Validated @RequestParam Long itemId) {
        itemService.deleteItem(itemId);
        return ApiResponse.ok(itemId);
    }

    /**
     * 사용자가 구매할 수 있는 상품 정보를 반환하는 메서드
     * @param userId
     * @return 구매가능한 상품 정보들을 담은 리스트
     */
    @GetMapping("/view")
    public ApiResponse<List<ItemResponseDto>> itemListAvailableForPurchaseByUser(@Validated @RequestParam Long userId) {
        return ApiResponse.ok(itemService.findAvailableForPurchaseByUser(userId));
    }

    /**
     * 상품 id에 해당하는 상품 정보 반환 메서드
     * @param itemId
     * @return 상품 정보를 담고 있는 dto
     */
    @GetMapping("/{itemId}")
    public ApiResponse<ItemResponseDto> itemDetail(@Validated @PathVariable Long itemId) {
        return ApiResponse.ok(itemService.findById(itemId));
    }

}
