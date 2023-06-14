package com.haebang.haebang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haebang.haebang.dto.JoinDto;
import com.haebang.haebang.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    MemberService userService;// 흉내내고 싶은 것
    @Autowired
    ObjectMapper objectMapper;// java 객체 -> json으로 만들어줌

    @Test
    @DisplayName("회원가입 성공 테스트")
    void join() throws Exception {
        JoinDto joinReqDto = new JoinDto();
        joinReqDto.setUsername("테스트 유저 1");
        joinReqDto.setPassword("test password 1");
        mockMvc.perform( MockMvcRequestBuilders.post("/api/user/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(joinReqDto))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 테스트")
    void join_fail() throws Exception {
        JoinDto joinReqDto = new JoinDto();
        joinReqDto.setUsername("테스트 유저 1");
        joinReqDto.setPassword("test password 1");
        mockMvc.perform( MockMvcRequestBuilders.post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(joinReqDto))
                ).andDo(print())
                .andExpect(status().isConflict());
    }
}
