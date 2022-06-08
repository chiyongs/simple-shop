package com.ssg.techrookie.backend.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssg.techrookie.backend.domain.item.Item;
import com.ssg.techrookie.backend.domain.item.ItemType;
import com.ssg.techrookie.backend.domain.user.User;
import com.ssg.techrookie.backend.domain.user.UserType;
import com.ssg.techrookie.backend.exception.CustomException;
import com.ssg.techrookie.backend.exception.ErrorCode;
import com.ssg.techrookie.backend.service.ItemService;
import com.ssg.techrookie.backend.web.dto.item.ItemResponseDto;
import com.ssg.techrookie.backend.web.dto.item.ItemSaveRequestDto;
import com.ssg.techrookie.backend.web.dto.user.UserResponseDto;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void itemDetail_성공() throws Exception {
        //given
        String itemName = "노브랜드 버터링";
        int itemPrice = 20000;
        LocalDate displayStartDate = LocalDate.of(2022,1,1);
        LocalDate displayEndDate = LocalDate.of(2023,1,1);
        ItemType itemType = ItemType.일반;
        Item item = Item.builder()
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemDisplayStartDate(displayStartDate)
                .itemDisplayEndDate(displayEndDate)
                .itemType(itemType)
                .build();
        ItemResponseDto responseDto = new ItemResponseDto(item);

        given(itemService.findById(anyLong()))
                .willReturn(responseDto);

        //when & then
        mockMvc.perform(
                        get("/api/v1/items/"+1L))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.itemName").value(itemName))
                .andExpect(jsonPath("$.data.itemType").value(itemType.getType()))
                .andExpect(jsonPath("$.data.itemPrice").value(itemPrice));
    }

    @Test
    void itemListAvailableForPurchaseByUser_성공() throws Exception {
        //given
        List<ItemResponseDto> responseDtos = new ArrayList<>();
        for(int i=0;i<3;i++) {
            responseDtos.add(new ItemResponseDto(
                    item("test"+i, 10000*(i+1),
                            LocalDate.of(2022,1,1),
                            LocalDate.of(2023,12,31),
                            ItemType.일반)));
        }

        given(itemService.findAvailableForPurchaseByUser(anyLong()))
                .willReturn(responseDtos);

        mockMvc.perform(
                get("/api/v1/items/view")
                        .param("userId", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].itemName").exists())
                .andExpect(jsonPath("$.data[0].itemPrice").exists())
                .andExpect(jsonPath("$.data[0].itemDisplayStartDate").exists())
                .andExpect(jsonPath("$.data[0].itemDisplayEndDate").exists())
                .andExpect(jsonPath("$.data[0].itemType").exists());

    }

    @Test
    void itemListAvailableForPurchaseByUser_실패() throws Exception {
        //given
        List<ItemResponseDto> responseDtos = new ArrayList<>();
        for(int i=0;i<3;i++) {
            responseDtos.add(new ItemResponseDto(
                    item("test"+i, 10000*(i+1),
                            LocalDate.of(2022,1,1),
                            LocalDate.of(2023,12,31),
                            ItemType.일반)));
        }

        given(itemService.findAvailableForPurchaseByUser(anyLong()))
                .willThrow(new CustomException(ErrorCode.NO_PERMISSION_TO_LEFT_USER));

        mockMvc.perform(
                        get("/api/v1/items/view")
                                .param("userId", String.valueOf(1L)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> result.getResolvedException().getClass().isAssignableFrom(CustomException.class))
                .andExpect(jsonPath("$.code").value(ErrorCode.NO_PERMISSION_TO_LEFT_USER.name()))
                .andDo(print());


    }

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
                )
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_VALID_METHOD_ARGUMENT.name()));
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
                )
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_VALID_METHOD_ARGUMENT.name()));
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
                )
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_VALID_METHOD_ARGUMENT.name()));
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

    private static Item item(String itemName, int itemPrice, LocalDate displayStartDate, LocalDate displayEndDate, ItemType itemType) {
        return Item.builder()
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemDisplayStartDate(displayStartDate)
                .itemDisplayEndDate(displayEndDate)
                .itemType(itemType)
                .build();
    }

}