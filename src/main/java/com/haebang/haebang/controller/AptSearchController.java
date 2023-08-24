package com.haebang.haebang.controller;

import com.haebang.haebang.repository.AptSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/search")
public class AptSearchController {
    private final AptSearchRepository aptSearchRepository;
    @GetMapping("apt")
    public ResponseEntity searchApt(@RequestParam(value = "search_condition") String searchCondition){
        return new ResponseEntity(aptSearchRepository.findAptDocumentsByRoadAddressOrDp(searchCondition, searchCondition), HttpStatus.OK);
    }

}
