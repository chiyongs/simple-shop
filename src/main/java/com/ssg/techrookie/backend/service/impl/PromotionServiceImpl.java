package com.ssg.techrookie.backend.service.impl;

import com.ssg.techrookie.backend.domain.item.Item;
import com.ssg.techrookie.backend.domain.item.ItemRepository;
import com.ssg.techrookie.backend.domain.promotion.Promotion;
import com.ssg.techrookie.backend.domain.promotion.PromotionRepository;
import com.ssg.techrookie.backend.domain.promotionitem.PromotionItem;
import com.ssg.techrookie.backend.domain.promotionitem.PromotionItemRepository;
import com.ssg.techrookie.backend.exception.CustomException;
import com.ssg.techrookie.backend.exception.ErrorCode;
import com.ssg.techrookie.backend.service.PromotionService;
import com.ssg.techrookie.backend.web.dto.item.PromotionItemResponseDto;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionResponseDto;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final ItemRepository itemRepository;
    private final PromotionItemRepository promotionItemRepository;

    @Override
    @Transactional
    public Long addPromotion(PromotionSaveRequestDto requestDto) {
        return promotionRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    @Transactional
    public void deletePromotion(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROMOTION_NOT_FOUND));

        promotionRepository.delete(promotion);
    }

    @Override
    @Transactional
    public void addPromotionItemsOnPromotion(Long promotionId, List<Long> itemList) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROMOTION_NOT_FOUND));

        // Set을 이용해 itemId가 중복되지 않도록 설정
        Set<Long> itemsId = new HashSet<>(itemList);
        for(Long itemId : itemsId) {
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
            promotion.addPromotionItem(new PromotionItem(item));
        }
    }

    @Override
    public PromotionItemResponseDto findPromotionByItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        List<PromotionItem> findPromotionItems = promotionItemRepository.findByItemWhichPromotionCurrentlyInProgressOrderByPromotionPrice(item, LocalDate.now());
        // 상품이 프로모션에 적용되어있지 않거나 적용된 프로모션이 현재 전시중인 아닌 경우
        if(findPromotionItems.isEmpty()) {
            return new PromotionItemResponseDto(item);
        }

        return new PromotionItemResponseDto(findPromotionItems.get(0));
    }

    @Override
    public PromotionResponseDto findById(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROMOTION_NOT_FOUND));

        return new PromotionResponseDto(promotion);
    }
}
