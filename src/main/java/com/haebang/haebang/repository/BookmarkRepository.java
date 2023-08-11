package com.haebang.haebang.repository;

import com.haebang.haebang.entity.Bookmark;
import com.haebang.haebang.entity.BookmarkId;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {
    List<Bookmark> findBookmarkByMember(Member member);
    void deleteAllByItem(Item item);
}
