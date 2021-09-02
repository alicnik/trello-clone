package com.example.trelloclone.services;

import com.example.trelloclone.models.Label;
import com.example.trelloclone.repositories.LabelRepository;
import org.springframework.stereotype.Service;

@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label createLabel(Label label) {
        return labelRepository.save(label);
    }

    public Label updateLabel(Label label) throws Exception {
        boolean exists = labelRepository.existsById(label.getId());
        if (!exists) {
            throw new Exception("Label does not exist");
        }
        return labelRepository.save(label);

    }
}
