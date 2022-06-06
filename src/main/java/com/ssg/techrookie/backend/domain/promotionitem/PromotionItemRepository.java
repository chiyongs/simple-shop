package com.ssg.techrookie.backend.domain.promotionitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PromotionItemRepository extends JpaRepository<PromotionItem, Long> {
    @Query(value = "SELECT p FROM PromotionItem p" +
            " WHERE p.promotion.promotionStartDate <= :now" +
            " AND p.promotion.promotionEndDate >= :now")
    List<PromotionItem> findByPromotionCurrentlyInProgress(@Param("now") LocalDate now);
}
