package com.ssg.techrookie.backend.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String itemName;
    private Integer itemPrice;
    private LocalDate itemDisplayStartDate;
    private LocalDate itemDisplayEndDate;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;
}
