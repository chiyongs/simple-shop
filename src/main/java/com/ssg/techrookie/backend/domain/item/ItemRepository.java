package com.ssg.techrookie.backend.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT i FROM Item i" +
            " WHERE i.itemDisplayStartDate <= :now" +
            " AND i.itemDisplayEndDate >= :now")
    List<Item> findItemsCurrentlyDisplaying(@Param("now") LocalDate now);

    @Query(value = "SELECT i FROM Item i" +
            " WHERE i.itemDisplayStartDate <= :now" +
            " AND i.itemDisplayEndDate >= :now" +
            " AND i.itemType = :itemType")
    List<Item> findCurrentlyDisplayingItemsByItemType(@Param("now") LocalDate now, @Param("itemType") ItemType itemType);
}
