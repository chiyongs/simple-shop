package com.ssg.techrookie.backend.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.techrookie.backend.domain.user.User;
import com.ssg.techrookie.backend.domain.user.UserType;
import com.ssg.techrookie.backend.service.UserService;
import com.ssg.techrookie.backend.web.dto.user.UserJoinRequestDto;
import com.ssg.techrookie.backend.web.dto.user.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserApiController.class)
class UserApiControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void userDetail_성공() throws Exception {
        //given
        String userName = "testUser";
        UserType userType = UserType.일반;
        User user = User.builder()
                .userName(userName)
                .userType(userType)
                .build();
        UserResponseDto responseDto = new UserResponseDto(user);

        given(userService.findById(anyLong()))
                .willReturn(responseDto);

        //when & then
        mockMvc.perform(
                get("/api/v1/users/"+1L))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.userName").exists())
                .andExpect(jsonPath("$.data.userType").exists())
                .andExpect(jsonPath("$.data.userStat").exists());
    }

    @Test
    void userAdd_성공() throws Exception {
        //given
        String userName = "testUser";
        String userType = "일반";
        UserJoinRequestDto requestDto = UserJoinRequestDto.builder()
                .userName(userName)
                .userType(userType)
                .build();
        String content = new ObjectMapper().writeValueAsString(requestDto);

        given(userService.join(any(UserJoinRequestDto.class)))
                .willReturn(1L);

        //when & then
        mockMvc.perform(
                post("/api/v1/users")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(1L));

    }

    @Test
    void userAdd_잘못된userName_실패() throws Exception {
        //given
        String userName = " ";
        String userType = "일반";
        UserJoinRequestDto requestDto = UserJoinRequestDto.builder()
                .userName(userName)
                .userType(userType)
                .build();
        String content = new ObjectMapper().writeValueAsString(requestDto);

        given(userService.join(any(UserJoinRequestDto.class)))
                .willReturn(1L);

        //when & then
        mockMvc.perform(
                        post("/api/v1/users")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class));


    }

    @Test
    void userAdd_잘못된userType_실패() throws Exception {
        //given
        String userName = "testUser";
        String userType = "test";
        UserJoinRequestDto requestDto = UserJoinRequestDto.builder()
                .userName(userName)
                .userType(userType)
                .build();
        String content = new ObjectMapper().writeValueAsString(requestDto);

        given(userService.join(any(UserJoinRequestDto.class)))
                .willReturn(1L);

        //when & then
        mockMvc.perform(
                        post("/api/v1/users")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class));
    }

    @Test
    void userDelete_성공() throws Exception {
        //given

        //when & then
        mockMvc.perform(
                        delete("/api/v1/users")
                                .param("userId","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(1));
    }


}