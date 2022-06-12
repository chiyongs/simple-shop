package com.ssg.techrookie.backend.web.dto.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.ssg.techrookie.backend.domain.item.Item;
import com.ssg.techrookie.backend.domain.item.ItemType;
import com.ssg.techrookie.backend.web.validation.Enum;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemSaveRequestDto {

    @NotBlank
    private String itemName;
    @Positive
    private Integer itemPrice;
    @Enum(enumClass = ItemType.class, ignoreCase = true)
    private String itemType;

//    @Future
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate itemDisplayStartDate;

//    @Future
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate itemDisplayEndDate;

    public Item toEntity() {
        return Item.builder()
                .itemName(itemName)
                .itemType(ItemType.valueOf(itemType))
                .itemPrice(itemPrice)
                .itemDisplayStartDate(itemDisplayStartDate)
                .itemDisplayEndDate(itemDisplayEndDate)
                .build();
    }
}
