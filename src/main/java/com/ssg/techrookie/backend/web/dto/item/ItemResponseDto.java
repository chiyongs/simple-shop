package com.ssg.techrookie.backend.web.dto.item;

import com.ssg.techrookie.backend.domain.item.Item;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ItemResponseDto {

    private Long id;
    private String itemName;
    private Integer itemPrice;

    private LocalDate itemDisplayStartDate;
    private LocalDate itemDisplayEndDate;
    private String itemType;

    public ItemResponseDto(Item entity) {
        this.id = entity.getId();
        this.itemName = entity.getItemName();
        this.itemPrice = entity.getItemPrice();
        this.itemDisplayStartDate = entity.getItemDisplayStartDate();
        this.itemDisplayEndDate = entity.getItemDisplayEndDate();
        this.itemType = entity.getItemType().getType();
    }
}
