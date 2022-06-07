package com.ssg.techrookie.backend.domain.promotionitem;

import com.ssg.techrookie.backend.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PromotionItemRepository extends JpaRepository<PromotionItem, Long> {

    void deleteByItem(Item item);

    @Query(value = "SELECT p FROM PromotionItem p" +
            " WHERE p.promotion.promotionStartDate <= :now" +
            " AND p.promotion.promotionEndDate >= :now")
    List<PromotionItem> findByPromotionCurrentlyInProgress(@Param("now") LocalDate now);

    @Query(value = "SELECT p FROM PromotionItem p" +
            " WHERE p.item = :item" +
            " AND p.promotion.promotionStartDate <= :now" +
            " AND p.promotion.promotionEndDate >= :now" +
            " ORDER BY p.promotionPrice")
    List<PromotionItem> findByItemWhichPromotionCurrentlyInProgressOrderByPromotionPrice(@Param("item") Item item, @Param("now") LocalDate now);
}
