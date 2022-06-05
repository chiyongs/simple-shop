package com.ssg.techrookie.backend.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssg.techrookie.backend.service.PromotionService;
import com.ssg.techrookie.backend.web.dto.promotion.PromotionSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PromotionApiController.class)
class PromotionApiControllerTest {

    @MockBean
    private PromotionService promotionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void promotionAdd_성공() throws Exception {
        //given
        String promotionNm = "2022 쓱데이";
        int discountAmount = 1000;
        LocalDate promotionStartDate = LocalDate.of(2022,1,1);
        LocalDate promotionEndDate = LocalDate.of(2023,1,1);

        PromotionSaveRequestDto requestDto = requestDto(promotionNm, discountAmount, promotionStartDate, promotionEndDate);

        String content = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(requestDto);

        given(promotionService.addPromotion(any(PromotionSaveRequestDto.class)))
                .willReturn(1L);

        //when & then
        mockMvc.perform(
                        post("/api/v1/promotions")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(1L))
                .andDo(print());
    }

    @Test
    void promotionDelete_성공() throws Exception {
        //given

        //when & then
        mockMvc.perform(
                        delete("/api/v1/promotions")
                                .param("promotionId","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(1));
    }

    private PromotionSaveRequestDto requestDto(String promotionNm, int discountAmount, LocalDate promotionStartDate, LocalDate promotionEndDate) {
        PromotionSaveRequestDto requestDto = PromotionSaveRequestDto.builder()
                .promotionNm(promotionNm)
                .discountAmount(discountAmount)
                .promotionStartDate(promotionStartDate)
                .promotionEndDate(promotionEndDate)
                .build();
        return requestDto;
    }

}