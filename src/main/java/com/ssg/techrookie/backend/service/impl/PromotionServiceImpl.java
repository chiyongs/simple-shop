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
        Promotion promotion = requestDto.toEntity();

        addNonDuplicatePromotionItem(promotion, requestDto.getItemsIdList());

        return promotionRepository.save(promotion).getId();
    }

    @Override
    @Transactional
    public void deletePromotion(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROMOTION_NOT_FOUND));

        promotionRepository.delete(promotion);
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

    /** 중복되는 상품을 프로모션에 적용시키지 않기 위해 중복 검사 후 프로모션을 적용하는 메서드 **/
    private void addNonDuplicatePromotionItem(Promotion promotion, List<Long> requestItemsIdList) {
        Set<Long> itemsId = new HashSet<>(requestItemsIdList);
        List<Item> findItems = itemRepository.findByIds(itemsId);

        if(findItems.size() != itemsId.size()) {
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }

        for(Item item : findItems) {
            promotion.addPromotionItem(new PromotionItem(item));
        }

    }
}
