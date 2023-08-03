package com.haebang.haebang.controller;

import com.haebang.haebang.dto.MapDto;
import com.haebang.haebang.entity.Member;
import com.haebang.haebang.repository.AptRepository;
import com.haebang.haebang.repository.ItemRepository;
import com.haebang.haebang.repository.MemberRepository;
import com.haebang.haebang.service.MapService;
import com.haebang.haebang.service.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class MapControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @MockBean
    private MapService mapService;

//    @MockBean
//    private RedisService redisService;
//
//    @MockBean
//    private ItemRepository itemRepository;
//
//    @MockBean
//    private AptRepository aptRepository;
//
//    @MockBean
//    private MemberRepository memberRepository;

    @Test
    void testMap() throws Exception {
        List<MapDto> dummyList = new ArrayList<>();
        // mapService.search_map() 메서드가 호출될 때, dummyList를 반환하도록 설정
        when(mapService.search_map("2022", "5", 11350, "상계동")).thenReturn(dummyList);

        // GET /map 요청을 보내고, 응답이 OK(200)인지 확인
        mockMvc.perform(MockMvcRequestBuilders.get("/map"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("alert")) // 응답의 뷰 이름이 "alert"인지 검증
                .andExpect(MockMvcResultMatchers.model().attributeExists("list")); // "list"라는 모델 속성이 존재하는지 검증

        // mapService.search_map() 메서드가 1번 호출되었는지 검증
        verify(mapService, times(1)).search_map(any(), any(), any(), any());
    }
}