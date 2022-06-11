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
            " JOIN FETCH p.promotion pro" +
            " WHERE p.item = :item" +
            " AND pro.promotionStartDate <= :now" +
            " AND pro.promotionEndDate >= :now" +
            " ORDER BY p.promotionPrice")
    List<PromotionItem> findByItemWhichPromotionCurrentlyInProgressOrderByPromotionPrice(@Param("item") Item item, @Param("now") LocalDate now);
}
