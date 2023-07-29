package com.haebang.haebang.entity;

import java.io.Serializable;

public class BookmarkId implements Serializable {
    private Long member;
    private Long item;
    public BookmarkId(){}
    public BookmarkId(Long member, Long item){
        super();
        this.item = item;
        this.member = member;
    }

}
