package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Plant;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.repository.PlantRepository;
import com.ngg.servernewgenie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class PlantService {
    @Autowired
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;

    public PlantService(PlantRepository plantRepository, UserRepository userRepository) {
        this.plantRepository = plantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void savePlantNameByUserId(Long userId, String plantName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        Plant plant = new Plant();
        plant.setUser(user);
        plant.setPlt_name(plantName);
        plant.setCreated_at(LocalDateTime.now());

        plantRepository.save(plant);
    }
}

