package com.ssg.techrookie.backend.service;

import com.ssg.techrookie.backend.domain.item.Item;
import com.ssg.techrookie.backend.web.dto.item.ItemResponseDto;
import com.ssg.techrookie.backend.web.dto.item.ItemSaveRequestDto;
import com.ssg.techrookie.backend.web.dto.item.PromotionItemResponseDto;

import java.util.List;

public interface ItemService {

    Long addItem(ItemSaveRequestDto requestDto);
    void deleteItem(Long itemId);
    List<ItemResponseDto> findAvailableForPurchaseByUser(Long userId);
    PromotionItemResponseDto findItemWithPromotion(Long itemId);
}
