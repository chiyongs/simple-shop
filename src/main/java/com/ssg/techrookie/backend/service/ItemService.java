package com.ssg.techrookie.backend.service;

import com.ssg.techrookie.backend.web.dto.item.ItemResponseDto;
import com.ssg.techrookie.backend.web.dto.item.ItemSaveRequestDto;

import java.util.List;

public interface ItemService {
    Long addItem(ItemSaveRequestDto requestDto);
    void deleteItem(Long itemId);
    List<ItemResponseDto> findAvailableForPurchaseByUser(Long userId);

}
