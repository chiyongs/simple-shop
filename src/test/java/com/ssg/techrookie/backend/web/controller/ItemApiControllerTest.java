package com.ssg.techrookie.backend.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssg.techrookie.backend.domain.item.Item;
import com.ssg.techrookie.backend.domain.item.ItemType;
import com.ssg.techrookie.backend.service.ItemService;
import com.ssg.techrookie.backend.web.dto.item.ItemSaveRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemApiController.class)
class ItemApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    void itemAdd_성공() throws Exception {
        //given
        String itemName = "testItem";
        int itemPrice = 10000;
        LocalDate displayStartDate = LocalDate.of(2022,1,1);
        LocalDate displayEndDate = LocalDate.of(2023,1,1);
        String itemType = "일반";

        String content = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(itemSaveRequestDto(itemName,itemPrice,displayStartDate,displayEndDate,itemType));

        given(itemService.addItem(any(ItemSaveRequestDto.class)))
                .willReturn(1L);

        //when & then
        mockMvc.perform(
                        post("/api/v1/items")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(1L));

    }

    @Test
    void itemAdd_실패_Blank_ItemName() throws Exception {
        //given
        String itemName = " ";
        int itemPrice = 10000;
        LocalDate displayStartDate = LocalDate.of(2022,1,1);
        LocalDate displayEndDate = LocalDate.of(2023,1,1);
        String itemType = "일반";

        String content = new ObjectMapper().
                registerModule(new JavaTimeModule())
                .writeValueAsString(itemSaveRequestDto(itemName,itemPrice,displayStartDate,displayEndDate,itemType));

        given(itemService.addItem(any(ItemSaveRequestDto.class)))
                .willReturn(1L);

        //when & then
        mockMvc.perform(
                        post("/api/v1/items")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class)
                );
    }

    @Test
    void itemAdd_실패_Not_Positive_ItemPrice() throws Exception {
        //given
        String itemName = "testItem";
        int itemPrice = -1000;
        LocalDate displayStartDate = LocalDate.of(2022,1,1);
        LocalDate displayEndDate = LocalDate.of(2023,1,1);
        String itemType = "일반";

        String content = new ObjectMapper().
                registerModule(new JavaTimeModule())
                .writeValueAsString(itemSaveRequestDto(itemName,itemPrice,displayStartDate,displayEndDate,itemType));

        given(itemService.addItem(any(ItemSaveRequestDto.class)))
                .willReturn(1L);

        //when & then
        mockMvc.perform(
                        post("/api/v1/items")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class)
                );
    }

    @Test
    void itemAdd_실패_Wrong_ItemType() throws Exception {
        //given
        String itemName = "testItem";
        int itemPrice = -1000;
        LocalDate displayStartDate = LocalDate.of(2022,1,1);
        LocalDate displayEndDate = LocalDate.of(2023,1,1);
        String itemType = "이상한타입";

        String content = new ObjectMapper().
                registerModule(new JavaTimeModule())
                .writeValueAsString(itemSaveRequestDto(itemName,itemPrice,displayStartDate,displayEndDate,itemType));

        given(itemService.addItem(any(ItemSaveRequestDto.class)))
                .willReturn(1L);

        //when & then
        mockMvc.perform(
                        post("/api/v1/items")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class)
                );
    }

    @Test
    void itemDelete_성공() throws Exception {
        //given

        //when & then
        mockMvc.perform(
                        delete("/api/v1/items")
                                .param("itemId","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(1));
    }

    private ItemSaveRequestDto itemSaveRequestDto(String itemName, int itemPrice, LocalDate displayStartDate, LocalDate displayEndDate, String itemType) {
        return ItemSaveRequestDto.builder()
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemType(itemType)
                .itemDisplayStartDate(displayStartDate)
                .itemDisplayEndDate(displayEndDate)
                .build();
    }

}