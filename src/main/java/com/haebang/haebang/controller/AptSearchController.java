package com.haebang.haebang.controller;

import com.haebang.haebang.component.ElasticsearchDataChecker;
import com.haebang.haebang.document.AptDocument;
import com.haebang.haebang.repository.AptSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/search")
public class AptSearchController {
    private final ElasticsearchDataChecker elasticsearchDataChecker;
    private final AptSearchRepository aptSearchRepository;
    @GetMapping("apt")
    public ResponseEntity searchApt(@RequestParam(value = "search_condition") String searchCondition){
        log.info("search apt 조건 = "+searchCondition);
        return new ResponseEntity(aptSearchRepository.findAptDocumentsByRoadAddressOrDp(searchCondition, searchCondition), HttpStatus.OK);
    }

    @GetMapping("test")
    public ResponseEntity test(@RequestParam(value = "query") String query) {
        log.info("api/search/test?query="+query);
        System.out.println(aptSearchRepository.findAptDocumentsByRoadAddressOrDp(query, query));
        return new ResponseEntity(aptSearchRepository.findAptDocumentsByRoadAddressOrDp(query, query), HttpStatus.OK);
    }
}
