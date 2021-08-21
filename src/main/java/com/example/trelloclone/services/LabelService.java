package com.example.trelloclone.services;

import com.example.trelloclone.repositories.LabelRepository;
import org.springframework.stereotype.Service;

@Service
public class LabelService {

    private LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }
}
