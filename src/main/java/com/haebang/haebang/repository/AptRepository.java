package com.haebang.haebang.repository;

import com.haebang.haebang.entity.Apt;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AptRepository extends JpaRepository<Apt, Long> {
    Optional<Apt> findByRoadAddress(String roadAddress);
    void deleteByRoadAddress(String roadAddress);




}
