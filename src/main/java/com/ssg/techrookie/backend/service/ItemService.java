package com.ssg.techrookie.backend.service;

import com.ssg.techrookie.backend.web.dto.item.ItemSaveRequestDto;

public interface ItemService {

    Long addItem(ItemSaveRequestDto requestDto);
    void deleteItem(Long itemId);
}
