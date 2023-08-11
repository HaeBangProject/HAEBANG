package com.haebang.haebang.entity;

import java.io.Serializable;
import java.util.Objects;

public class BookmarkId implements Serializable {
    private Long member;
    private Long item;
    public BookmarkId(){}
    public BookmarkId(Long member, Long item){
        super();
        this.item = item;
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookmarkId that = (BookmarkId) o;
        return Objects.equals(this.member, that.member) &&
                Objects.equals(this.item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, item);
    }

}
