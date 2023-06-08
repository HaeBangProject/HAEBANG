package com.haebang.haebang.repository;

import com.haebang.haebang.entity.Apt;
import com.haebang.haebang.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByAptId(Long id);
}
