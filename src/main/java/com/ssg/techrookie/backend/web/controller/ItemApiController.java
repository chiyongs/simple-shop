package com.ssg.techrookie.backend.web.controller;

import com.ssg.techrookie.backend.service.ItemService;
import com.ssg.techrookie.backend.web.dto.ApiResponse;
import com.ssg.techrookie.backend.web.dto.item.ItemSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
@RestController
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping
    public ApiResponse<Long> itemAdd(@Validated @RequestBody ItemSaveRequestDto requestDto) {
        return ApiResponse.ok(itemService.addItem(requestDto));
    }

    @DeleteMapping
    public ApiResponse<Long> itemDelete(@Validated @RequestParam Long itemId) {
        itemService.deleteItem(itemId);
        return ApiResponse.ok(itemId);
    }
}
