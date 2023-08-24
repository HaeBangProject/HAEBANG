package com.haebang.haebang.repository;

import com.haebang.haebang.entity.Apt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AptRepository extends JpaRepository<Apt, Long> {
    Optional<Apt> findByRoadAddress(String roadAddress);
    void deleteByRoadAddress(String roadAddress);

}
