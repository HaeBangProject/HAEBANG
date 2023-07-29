package com.haebang.haebang.repository;

import com.haebang.haebang.entity.Apt;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByAptId(Long id);
    List<Item> findAllByMember(Member member);
    void deleteById(Long id);
}
