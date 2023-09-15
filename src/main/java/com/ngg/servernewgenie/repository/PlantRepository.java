package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Integer> {
}
