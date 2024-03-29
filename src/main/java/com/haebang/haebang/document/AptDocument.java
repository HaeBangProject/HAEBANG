package com.haebang.haebang.document;

import com.haebang.haebang.entity.Apt;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "apt")
public class AptDocument {
    @Id
    private Long id;

    String roadAddress;

    String dp;// 한 건물당 하나의 도로명주소가짐 (아파트/건물이름)

    public static AptDocument from(Apt apt){
        return AptDocument.builder()
                .id(apt.getId())
                .roadAddress(apt.getRoadAddress())
                .dp(apt.getDp())
                .build();
    }
}
