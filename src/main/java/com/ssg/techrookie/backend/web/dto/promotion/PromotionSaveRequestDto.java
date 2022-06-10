package com.ssg.techrookie.backend.web.dto.promotion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.ssg.techrookie.backend.domain.promotion.Promotion;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PromotionSaveRequestDto {

    @NotBlank
    private String promotionNm;
    private Integer discountAmount;
    private Double discountRate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate promotionStartDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate promotionEndDate;

    @NotEmpty
    private List<Long> itemsIdList = new ArrayList<>();

    public Promotion toEntity() {
        return Promotion.builder()
                .promotionNm(promotionNm)
                .discountAmount(discountAmount)
                .discountRate(discountRate)
                .promotionStartDate(promotionStartDate)
                .promotionEndDate(promotionEndDate)
                .build();
    }
}
