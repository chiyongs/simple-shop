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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
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
        log.info("[addItem] {}", requestDto);
        return itemRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        log.info("[deleteItem] 해당 Item 삭제. itemId : {}", itemId);
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND, itemId));
        promotionItemRepository.deleteByItem(item);
        itemRepository.delete(item);
    }

    @Override
    public List<ItemResponseDto> findAvailableForPurchaseByUser(Long userId) {
        log.info("[findAvailableForPurchaseByUser] 사용자가 구매할 수 있는 상품 정보 조회. userId : {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId));

        if(user.getUserStat() == UserStat.탈퇴) {
            throw new CustomException(ErrorCode.NO_PERMISSION_TO_LEFT_USER);
        }
        // 일반회원인 경우, 일반 품목 상품만 검색
        if(user.getUserType() == UserType.일반) {
            log.info("[findAvailableForPurchaseByUser] 일반회원이므로 일반 품목 상품만 조회. userId : {}", userId);
            return itemRepository.findCurrentlyDisplayingItemsByItemType(LocalDate.now(), ItemType.일반)
                    .stream().map(ItemResponseDto::new).collect(Collectors.toList());
        }
        // 기업회원인 경우, 일반 품목과 기업 품목 상품 모두 검색
        log.info("[findAvailableForPurchaseByUser] 기업회원이므로 일반 품목과 기업 품목 상품 모두 조회. userId : {}", userId);
        return itemRepository.findItemsCurrentlyDisplaying(LocalDate.now())
                .stream().map(ItemResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public ItemResponseDto findById(Long itemId) {
        log.info("[findById] 해당 Item 조회. itemId : {}", itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND, itemId));

        return new ItemResponseDto(item);
    }

}
