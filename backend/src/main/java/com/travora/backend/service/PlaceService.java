package com.travora.backend.service;

import com.travora.backend.repository.ActivityRepository;
import com.travora.backend.repository.DiningRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceService {

    private final DiningRepository diningRepository;
    private final ActivityRepository activityRepository;

    public PlaceService(DiningRepository diningRepository, ActivityRepository activityRepository) {
        this.diningRepository = diningRepository;
        this.activityRepository = activityRepository;
    }

    public Optional<byte[]> getPhotoByReference(String reference) {
        return diningRepository.findByPhotoReference(reference)
                .map(d -> d.getPhotoData())
                .filter(data -> data != null)
                .or(() -> activityRepository.findByPhotoReference(reference)
                        .map(a -> a.getPhotoData())
                        .filter(data -> data != null));
    }
}
