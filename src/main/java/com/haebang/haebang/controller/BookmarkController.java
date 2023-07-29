package com.haebang.haebang.controller;

import com.haebang.haebang.entity.Bookmark;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.entity.Member;
import com.haebang.haebang.repository.BookmarkRepository;
import com.haebang.haebang.repository.ItemRepository;
import com.haebang.haebang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class BookmarkController {

    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ItemRepository itemRepository;

    @GetMapping("bookmark")
    ResponseEntity getBookmarkList(Authentication authentication){
// 로그인 된 사람만, jwt 토큰 검사 필요
        Member member = memberRepository.findByUsername( authentication.getName()).orElseThrow();
        List<Bookmark> bookmarkList = bookmarkRepository.findBookmarkByMember(member);
        return new ResponseEntity(bookmarkList, HttpStatus.OK);
    }

    @PostMapping("bookmark/{item_id}")
    ResponseEntity doBookmark(@PathVariable("item_id") Long itemId, Authentication authentication){
        // 로그인 된 사람만, jwt 토큰 검사 필요
        log.info("북마크 post 요청");
        Member member = memberRepository.findByUsername( authentication.getName()).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();
        Bookmark bookmark = Bookmark.builder()
                .member(member)
                .item(item)
                .build();
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        log.info( "book mark + = "+savedBookmark );
        return new ResponseEntity( "북마크 완료", HttpStatus.OK);
    }

    @DeleteMapping("bookmark/{item_id}")
    ResponseEntity undoBookmark(@PathVariable("item_id") Long itemId, Authentication authentication){
        Member member = memberRepository.findByUsername( authentication.getName()).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();

        Bookmark bookmark = Bookmark.builder()
                .member(member)
                .item(item)
                .build();
        log.info("book mark deleted : "+bookmark);
        bookmarkRepository.delete(bookmark);

        return new ResponseEntity(HttpStatus.OK);
    }
}
