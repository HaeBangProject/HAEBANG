package com.haebang.haebang.repository;

import com.haebang.haebang.entity.ChatRoomDTO;
import com.haebang.haebang.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatRoomDTO,String> {
    ChatRoomDTO findByRoomId(String id);

}
