package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    @Query(value = "SELECT p.plt_name FROM plant p WHERE p.user_id = ? ORDER BY p.created_at DESC LIMIT 1", nativeQuery = true)
    String findMostRecentPltNameByUserId(@Param("userId") String userId);
}


