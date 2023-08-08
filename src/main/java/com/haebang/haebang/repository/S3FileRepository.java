package com.haebang.haebang.repository;

import com.haebang.haebang.entity.S3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3FileRepository extends JpaRepository<S3File, Long> {
}
