package com.ssg.techrookie.backend.web.controller;

import com.ssg.techrookie.backend.service.ItemService;
import com.ssg.techrookie.backend.web.dto.ApiResponse;
import com.ssg.techrookie.backend.web.dto.item.ItemSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
@RestController
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping
    public ApiResponse<Long> itemAdd(@Validated @RequestBody ItemSaveRequestDto requestDto) {
        return ApiResponse.ok(itemService.addItem(requestDto));
    }
}
