package com.ssg.techrookie.backend.service.impl;

import com.ssg.techrookie.backend.domain.item.ItemRepository;
import com.ssg.techrookie.backend.domain.user.User;
import com.ssg.techrookie.backend.domain.user.UserRepository;
import com.ssg.techrookie.backend.domain.user.UserType;
import com.ssg.techrookie.backend.exception.CustomException;
import com.ssg.techrookie.backend.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@Import(ItemService.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;

    @Autowired @InjectMocks
    private ItemServiceImpl itemService;

    @DisplayName("회원이 구매가능한 현재 전시중인 상품조회_탈퇴한회원_실패")
    @Test
    void findAvailableForPurchaseByUser() {
        //given
        User user = User.builder()
                .build();
        user.withdraw();

        given(userRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(user));

        //when & then
        assertThrows(CustomException.class, () ->
                itemService.findAvailableForPurchaseByUser(1L));

    }


}