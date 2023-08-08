package com.haebang.haebang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.haebang.haebang.enums.FileCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class S3File {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String s3Url;
    private String dirName;
    private String fileName;
    @CreationTimestamp
    LocalDate createdDate;
    @Enumerated(EnumType.STRING)
    private FileCategory fileCategory;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")// casecadeType = ALL 코드 지워서 부모까지 지워지는거 막음
    Item item;
}
