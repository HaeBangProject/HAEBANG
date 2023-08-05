package com.haebang.haebang.service;
import com.haebang.haebang.entity.Apt;
import com.haebang.haebang.repository.AptRepository;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class AptServiceTest {
    @Autowired
    AptRepository aptRepository;

    @AfterEach
    public void cleanup(){
        aptRepository.deleteByRoadAddress("Test_RoadAddress");
    }


    @Test
    void createItem(){
        //given
        Apt apt = new Apt();
        apt.setRoadAddress("Test_RoadAddress");
        apt.setDp("test아파트");
        apt.setCnt(10L);

        //when
        aptRepository.save(apt);
        Optional<Apt> test = aptRepository.findByRoadAddress("Test_RoadAddress");


        //then
        Assertions.assertThat(apt.getDp()).isEqualTo(test.get().getDp());


    }
}

