package com.ssg.techrookie.backend.service.impl;

import com.ssg.techrookie.backend.domain.item.ItemRepository;
import com.ssg.techrookie.backend.service.ItemService;
import com.ssg.techrookie.backend.web.dto.item.ItemSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public Long addItem(ItemSaveRequestDto requestDto) {
        return itemRepository.save(requestDto.toEntity()).getId();
    }
}
