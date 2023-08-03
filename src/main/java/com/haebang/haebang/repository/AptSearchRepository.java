package com.haebang.haebang.repository;

import com.haebang.haebang.document.AptDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AptSearchRepository extends ElasticsearchRepository<AptDocument, Long> {
    List<AptDocument> findAptDocumentsByRoadAddressOrDp(String roadAddress, String dp);
}
