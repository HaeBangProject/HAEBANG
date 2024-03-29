package com.haebang.haebang.component;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ElasticsearchDataChecker {

    private final RestHighLevelClient elasticsearchClient;

    public void checkAptDocumentData() throws IOException {
        String indexName = "apt"; // AptDocument에 대한 인덱스 이름

        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String sourceAsString = hit.getSourceAsString();
        }
    }
}