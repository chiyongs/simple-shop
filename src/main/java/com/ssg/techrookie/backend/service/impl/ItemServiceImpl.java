package com.ssg.techrookie.backend.service.impl;

import com.ssg.techrookie.backend.domain.item.Item;
import com.ssg.techrookie.backend.domain.item.ItemRepository;
import com.ssg.techrookie.backend.domain.item.ItemType;
import com.ssg.techrookie.backend.domain.promotionitem.PromotionItemRepository;
import com.ssg.techrookie.backend.domain.user.User;
import com.ssg.techrookie.backend.domain.user.UserRepository;
import com.ssg.techrookie.backend.domain.user.UserStat;
import com.ssg.techrookie.backend.domain.user.UserType;
import com.ssg.techrookie.backend.exception.CustomException;
import com.ssg.techrookie.backend.exception.ErrorCode;
import com.ssg.techrookie.backend.service.ItemService;
import com.ssg.techrookie.backend.web.dto.item.ItemResponseDto;
import com.ssg.techrookie.backend.web.dto.item.ItemSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final PromotionItemRepository promotionItemRepository;

    @Override
    @Transactional
    public Long addItem(ItemSaveRequestDto requestDto) {
        return itemRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        promotionItemRepository.deleteByItem(item);
        itemRepository.delete(item);
    }

    @Override
    public List<ItemResponseDto> findAvailableForPurchaseByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(user.getUserStat() == UserStat.탈퇴) {
            throw new CustomException(ErrorCode.NO_PERMISSION_TO_LEFT_USER);
        }
        // 일반회원인 경우, 일반 품목 상품만 검색
        if(user.getUserType() == UserType.일반) {
            return itemRepository.findCurrentlyDisplayingItemsByItemType(LocalDate.now(), ItemType.일반)
                    .stream().map(ItemResponseDto::new).collect(Collectors.toList());
        }
        // 기업회원인 경우, 일반 품목과 기업 품목 상품 모두 검색
        return itemRepository.findItemsCurrentlyDisplaying(LocalDate.now())
                .stream().map(ItemResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public ItemResponseDto findById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        return new ItemResponseDto(item);
    }

}
