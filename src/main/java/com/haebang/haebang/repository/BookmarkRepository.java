package com.haebang.haebang.repository;

import com.haebang.haebang.entity.Bookmark;
import com.haebang.haebang.model.BookmarkId;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {
    List<Bookmark> findBookmarkByMember(Member member);
    void deleteAllByItem(Item item);
}
