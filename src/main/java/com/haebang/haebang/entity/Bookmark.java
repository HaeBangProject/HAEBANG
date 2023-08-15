package com.haebang.haebang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@IdClass(BookmarkId.class)
public class Bookmark {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @Id
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}
