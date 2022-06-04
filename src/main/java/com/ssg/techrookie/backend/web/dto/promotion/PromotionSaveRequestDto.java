package com.ssg.techrookie.backend.web.dto.promotion;

import com.ssg.techrookie.backend.domain.promotion.Promotion;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PromotionSaveRequestDto {

    @NotBlank
    private String promotionNm;
    private Integer discountAmount;
    private Double discountRate;
    private LocalDate promotionStartDate;
    private LocalDate promotionEndDate;

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
